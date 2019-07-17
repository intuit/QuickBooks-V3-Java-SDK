/*******************************************************************************
  * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;

import com.intuit.payment.config.ProxyConfig;
import com.intuit.payment.exception.BadRequestException;
import com.intuit.payment.util.LoggerImpl;
import com.intuit.payment.util.PropertiesConfig;

/**
 * Client class to make http request calls
 * 
 * @author dderose
 *
 */
public class HttpRequestClient {

	private final CloseableHttpClient client;

	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SOCKET_TIMEOUT = 30000;

	private static final Logger logger = LoggerImpl.getInstance();

	/**
	 * Build the HttpClient
	 *
	 */
	public HttpRequestClient(ProxyConfig proxyConfig) {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();

		// add default headers
		List<BasicHeader> headers = new ArrayList<BasicHeader>();
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
		headers.add(new BasicHeader(HttpHeaders.USER_AGENT,
				"V3JavaSDK-payments-" + PropertiesConfig.getInstance().getProperty("version")));

		// build the client
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", prepareClientSSL()).register("http", new PlainConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClientBuilder hcBuilder = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config)
				.setDefaultHeaders(headers).setMaxConnPerRoute(10)
				.setDefaultCredentialsProvider(setProxyAuthentication(proxyConfig));

		// getting proxy from Config file.
		HttpHost proxy = getProxy(proxyConfig);

		if (proxy != null) {
			hcBuilder.setProxy(proxy);
		}
		client = hcBuilder.build();
	}

	/**
	 * Method to make the HTTP request call using the request attributes
	 * supplied
	 * 
	 * @param request
	 * @return
	 * @throws BadRequestException
	 */
	public Response makeRequest(Request serviceRequest) throws BadRequestException {

		logger.debug("Enter HttpRequestClient::makeRequest");

		// prepare request
		RequestBuilder builder = RequestBuilder.create(serviceRequest.getMethod().toString())
				.setUri(serviceRequest.getUrl()).setVersion(HttpVersion.HTTP_1_1).setCharset(StandardCharsets.UTF_8);

		builder.addHeader("Request-Id", serviceRequest.getContext().getRequestId());

		// add auth header
		if (!serviceRequest.isIgnoreAuthHeader()) {
			builder.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + serviceRequest.getContext().getAccessToken());
		}

		MethodType method = serviceRequest.getMethod();
		if (method == MethodType.POST && serviceRequest.getPostJson() != null) {
			// add post json
			HttpEntity entity = new StringEntity(serviceRequest.getPostJson(), "UTF-8");
			builder.setEntity(entity);
		}

		logger.debug("Request URI : " + builder.getUri());
		logger.debug("Http Method : " + builder.getMethod());
		
		HttpResponse httpResponse = null;
		try {
			// make the call
			httpResponse = client.execute(builder.build());
			
			// prepare response
			return new Response(httpResponse.getStatusLine().getStatusCode(), getResult(httpResponse), getIntuitTid(httpResponse));

		} catch (IOException e) {
			logger.error("Exception while making httpRequest", e);
			throw new BadRequestException(e.getMessage());
		} finally {
			//close
			HttpClientUtils.closeQuietly(httpResponse);
		}
	}

	/**
	 * Method to set proxy authentication
	 * 
	 * @return
	 */
	public CredentialsProvider setProxyAuthentication(ProxyConfig proxyConfig) {

		if (proxyConfig == null) {
			return null;
		}
		String username = proxyConfig.getUsername();
		String password = proxyConfig.getPassword();

		if (!username.isEmpty() && !password.isEmpty()) {
			String host = proxyConfig.getHost();
			String port = proxyConfig.getPort();
			if (!host.isEmpty() && !port.isEmpty()) {
				CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				String domain = proxyConfig.getDomain();
				if (!domain.isEmpty()) {
					credentialsProvider.setCredentials(new AuthScope(host, Integer.parseInt(port)), new NTCredentials(username, password, host, domain));
				} else {
					credentialsProvider.setCredentials(new AuthScope(host, Integer.parseInt(port)), new UsernamePasswordCredentials(username, password));
				}
				return credentialsProvider;
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	public SSLConnectionSocketFactory prepareClientSSL() {
		try {
			KeyStore trustStore = null;
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
					.build();

			String tlsVersion = PropertiesConfig.getInstance().getProperty("TLS_VERSION");
			logger.info("tlsVersion used: " + tlsVersion);
			SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext,
					new String[] { tlsVersion }, null, new NoopHostnameVerifier());
			return sslConnectionFactory;
		} catch (Exception ex) {
			logger.error("couldn't create SSLConnectionSocketFactory!! {}", ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Method to get proxy
	 * 
	 * @return returns HttpHost
	 */
	public HttpHost getProxy(ProxyConfig proxyConfig) {
		if (proxyConfig == null) {
			return null;
		}
		String host = proxyConfig.getHost();
		String port = proxyConfig.getPort();
		HttpHost proxy = null;
		if (!host.isEmpty() && !port.isEmpty()) {
			proxy = new HttpHost(host, Integer.parseInt(port));
		}
		return proxy;
	}

	/**
	 * Parse the response and return the string from httpresponse body
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	public static String getResult(HttpResponse response) throws IOException {
		StringBuffer result = new StringBuffer();
		if (response.getEntity() != null && response.getEntity().getContent() != null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		}
		logger.info(result.toString());
		return result.toString();
	}

	/**
	 * Parses the response headers and returns value for intuit_tid parameter
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	public static String getIntuitTid(HttpResponse response) throws IOException {
		return response.getFirstHeader("intuit_tid").getValue();
	}

}
