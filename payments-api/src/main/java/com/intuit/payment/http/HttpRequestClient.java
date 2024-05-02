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

import com.intuit.payment.config.ProxyConfig;
import com.intuit.payment.exception.BadRequestException;
import com.intuit.payment.util.LoggerImpl;
import com.intuit.payment.util.PropertiesConfig;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContexts;
import org.slf4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
		ConnectionConfig config = ConnectionConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
				.setSocketTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS).build();

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
		cm.setDefaultConnectionConfig(config);
		cm.setDefaultMaxPerRoute(10);
		HttpClientBuilder hcBuilder = HttpClients.custom().setConnectionManager(cm).setDefaultHeaders(headers)
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
		ClassicRequestBuilder builder = ClassicRequestBuilder.create(serviceRequest.getMethod().toString())
				.setUri(serviceRequest.getUrl()).setVersion(HttpVersion.HTTP_1_1).setCharset(StandardCharsets.UTF_8);

		builder.addHeader("Request-Id", serviceRequest.getContext().getRequestId());

		// add auth header
		if (!serviceRequest.isIgnoreAuthHeader()) {
			builder.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + serviceRequest.getContext().getAccessToken());
		}

		MethodType method = serviceRequest.getMethod();
		if (method == MethodType.POST && serviceRequest.getPostJson() != null) {
			// add post json
			HttpEntity entity = new StringEntity(serviceRequest.getPostJson(), StandardCharsets.UTF_8);
			builder.setEntity(entity);
		}

		logger.debug("Request URI : " + builder.getUri());
		logger.debug("Http Method : " + builder.getMethod());
		
		ClassicHttpResponse httpResponse = null;
		try {
			// make the call
			return client.execute(builder.build(), HttpRequestClient::handleHttpResponse);
		} catch (IOException e) {
			logger.error("Exception while making httpRequest", e);
			throw new BadRequestException(e.getMessage());
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
				BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(new AuthScope(host, Integer.parseInt(port)), new UsernamePasswordCredentials(username, password.toCharArray()));
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
	 * Parses the httpresponse and returns an Intuit response
	 *
	 * @param response
	 * @return Response
	 * @throws IOException
	 */
	public static Response handleHttpResponse(ClassicHttpResponse response) throws IOException {
		return new Response(response.getCode(), getResult(response), getIntuitTid(response));
	}

	/**
	 * Parse the response and return the string from httpresponse body
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	public static String getResult(ClassicHttpResponse response) throws IOException {
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
