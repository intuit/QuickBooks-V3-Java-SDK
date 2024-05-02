/*******************************************************************************
 * Copyright (c) 2017 Intuit
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
package com.intuit.oauth2.http;

import com.intuit.oauth2.config.ProxyConfig;
import com.intuit.oauth2.data.OAuthMigrationRequest;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.PropertiesConfig;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContexts;
import org.slf4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Client class to make http request calls
 *
 * @author dderose
 */
public class HttpRequestClient {

	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SOCKET_TIMEOUT = 30000;
	private static final Logger logger = LoggerImpl.getInstance();
	private final CloseableHttpClient client;

	/**
	 * Build the HttpClient
	 */
	public HttpRequestClient(ProxyConfig proxyConfig) {
		ConnectionConfig config = ConnectionConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).setSocketTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS).build();

		//add default headers
		List<BasicHeader> headers = new ArrayList<BasicHeader>();
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
		headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "V3JavaSDK-OAuth2-" + PropertiesConfig.getInstance().getProperty("version")));

		//build the client
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", prepareClientSSL()).register("http", new PlainConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setDefaultConnectionConfig(config);
		cm.setDefaultMaxPerRoute(10);
		HttpClientBuilder hcBuilder = HttpClients.custom().setConnectionManager(cm).setDefaultHeaders(headers).setDefaultCredentialsProvider(setProxyAuthentication(proxyConfig));

		// getting proxy from Config file.
		HttpHost proxy = getProxy(proxyConfig);

		if (proxy != null) {
			hcBuilder.setProxy(proxy);
		}
		client = hcBuilder.build();
	}

	public SSLConnectionSocketFactory prepareClientSSL() {
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy()).build();

			String tlsVersion = PropertiesConfig.getInstance().getProperty("TLS_VERSION");
			return new SSLConnectionSocketFactory(sslContext, new String[]{tlsVersion}, null, new NoopHostnameVerifier());
		} catch (Exception ex) {
			logger.error("couldn't create httpClient!! {}", ex.getMessage(), ex);
			return null;
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
	 * Method to make the HTTP request call using the request attributes supplied
	 *
	 * @param request
	 * @return
	 * @throws InvalidRequestException
	 */
	public Response makeRequest(Request request) throws InvalidRequestException {

		logger.debug("Enter HttpRequestClient::makeRequest");

		//prepare request
		ClassicRequestBuilder builder = ClassicRequestBuilder.create(request.getMethod().value()).setUri(request.constructURL().toString()).setVersion(HttpVersion.HTTP_1_1).setCharset(StandardCharsets.UTF_8);

		//add auth header
		if (request.isRequiresAuthentication()) {
			builder.addHeader(HttpHeaders.AUTHORIZATION, request.getAuthString());
		}

		MethodType method = request.getMethod();
		if (method == MethodType.POST) {
			//add post header
			builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

			//add post params
			for (NameValuePair nvp : request.getPostParams()) {
				builder.addParameter(nvp);
			}
		}

		logger.debug("Request URI : " + builder.getUri());

		try {
			//make the call
			return client.execute(builder.build(), HttpRequestClient::handleHttpResponse);
		} catch (IOException e) {
			logger.error("Exception while making httpRequest", e);
			throw new InvalidRequestException(e.getMessage());
		}

	}

	/**
	 * Method to make the HTTP POST request using the request attributes supplied
	 *
	 * @param request
	 * @return
	 * @throws InvalidRequestException
	 */
	public Response makeJsonRequest(Request request, OAuthMigrationRequest migrationRequest) throws InvalidRequestException {

		logger.debug("Enter HttpRequestClient::makeJsonRequest");
		//create oauth consumer using tokens
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(migrationRequest.getConsumerKey(), migrationRequest.getConsumerSecret());
		consumer.setTokenWithSecret(migrationRequest.getAccessToken(), migrationRequest.getAccessSecret());

		HttpPost post = new HttpPost(request.constructURL().toString());

		//sign
		try {
			consumer.sign(post);
		} catch (OAuthMessageSignerException e) {
			logger.error("Exception while making httpRequest", e);
			throw new InvalidRequestException(e.getMessage());
		} catch (OAuthExpectationFailedException e) {
			logger.error("Exception while making httpRequest", e);
			throw new InvalidRequestException(e.getMessage());
		} catch (OAuthCommunicationException e) {
			logger.error("Exception while making httpRequest", e);
			throw new InvalidRequestException(e.getMessage());
		}

		//add headers
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-Type", "application/json");
		
		logger.debug("Request URI : " + request.constructURL().toString());
		logger.debug("request payload : " + request.getPostJson());

		// add post data
		HttpEntity entity = new StringEntity(request.getPostJson(), StandardCharsets.UTF_8);
		post.setEntity(entity);

		CloseableHttpResponse httpResponse = null;
		try {
			//make the call
			return client.execute(post, HttpRequestClient::handleHttpResponse);
		} catch (IOException e) {
			logger.error("Exception while making httpRequest", e);
			throw new InvalidRequestException(e.getMessage());
		}

	}

	/**
	 * Parses the http response and returns an Intuit response
	 *
	 * @param response
	 * @return Response
	 * @throws IOException
	 */
	public static Response handleHttpResponse(ClassicHttpResponse response) throws IOException {
		int httpStatusCode = response.getCode();
		logger.info("httpStatusCode : " + httpStatusCode);
		String intuit_tid = getIntuitTid(response);
		logger.debug("intuit_tid : " + intuit_tid);

		//prepare response
		return new Response(response.getEntity() == null ? null : response.getEntity().getContent(), httpStatusCode, intuit_tid);
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
