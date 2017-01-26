/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.interceptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.exception.ConfigurationException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.IntuitRetryPolicyHandler;
import com.intuit.ipp.net.MethodType;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.PropertyHelper;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to establish a HTTP connection
 *
 */
public class HTTPClientConnectionInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable PORT_443
	 */
	private static final int PORT_443 = 443;
	
	/**
	 * variable LENGTH_256
	 */
	private static final int LENGTH_256 = 256;
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {
		
		LOG.debug("Enter HTTPClientConnectionInterceptor...");
		
		DefaultHttpClient client = new DefaultHttpClient();

		RequestElements intuitRequest = intuitMessage.getRequestElements();
		
		// Set the retry handler
		IntuitRetryPolicyHandler handler = getRetryHandler();
		client.setHttpRequestRetryHandler(handler);

		// getting proxy from Config file.
		HttpHost proxy = getProxy();

		if (proxy != null) {
			setProxyAuthentication(client);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			Scheme sch = getSSLScheme();
			if (sch != null) {
				client.getConnectionManager().getSchemeRegistry().register(sch);
			}
		}

		// set the timeout parameters
		setTimeout(client, intuitRequest.getContext());
		
		HttpRequestBase httpRequest = null;
		URI uri = null;
		
		try {
			uri = new URI(intuitRequest.getRequestParameters().get(RequestElements.REQ_PARAM_RESOURCE_URL));
		} catch (URISyntaxException e) {
			throw new FMSException("URISyntaxException", e);
		}
		
		String methodtype = intuitRequest.getRequestParameters().get(RequestElements.REQ_PARAM_METHOD_TYPE);
		
		if (methodtype.equals(MethodType.GET.toString())) {
			httpRequest = new HttpGet(uri);
		} else if (methodtype.equals(MethodType.POST.toString())) {
			httpRequest = new HttpPost(uri);
			byte[] compressedData = intuitRequest.getCompressedData();
			if (compressedData != null) {
				// use compressed data to create httpEntity
				InputStream is = new ByteArrayInputStream(compressedData);
				HttpEntity entity = new InputStreamEntity(is, compressedData.length);
				((HttpPost) httpRequest).setEntity(entity);
			} else if(null != intuitRequest.getPostString()) {
				// use postString to create httpEntity
				HttpEntity entity;
				try {
					entity = new StringEntity(intuitRequest.getPostString());
				} catch (UnsupportedEncodingException e) {
					throw new FMSException("UnsupportedEncodingException", e);
				}
				((HttpPost) httpRequest).setEntity(entity);
			}
		}
		
		// populate the headers to HttpRequestBase
		populateRequestHeaders(httpRequest, intuitRequest.getRequestHeaders());
		
		// authorize the request
		authorizeRequest(intuitRequest.getContext(), httpRequest);
		
		LOG.debug("Request URI : " + uri);
		LOG.debug("Http Method : " + methodtype);
		LOG.debug("Request Body : " + intuitRequest.getSerializedData());
		
		HttpResponse httpResponse = null;
		try {
			// prepare HttpHost object
			HttpHost target = new HttpHost(uri.getHost(), -1, uri.getScheme());
			httpResponse = client.execute(target, httpRequest);
			LOG.debug("Connection status : " + httpResponse.getStatusLine());
			// set the response elements before close the connection
			setResponseElements(intuitMessage, httpResponse);
		} catch (ClientProtocolException e) {
			throw new ConfigurationException("Error in Http Protocol definition", e);
		} catch (IOException e) {
			throw new FMSException(e);
		} finally {
			if (client != null) {
				try {
					client.getConnectionManager().shutdown();
				} catch (Exception e) {
					LOG.warn("Unable to close HttpClient connection.", e);
				}
			}
		}
		
		LOG.debug("Exit HTTPClientConnectionInterceptor.");
	}
	
	/**
	 * Method to populate the HTTP request headers by reading it from the requestHeaders Map
	 * 
	 * @param httpRequest the http request
	 * @param requestHeaders the request headers
	 */
	private void populateRequestHeaders(HttpRequestBase httpRequest, Map<String, String> requestHeaders) {
		
		Set<String> keySet = requestHeaders.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			String value = requestHeaders.get(key);
			httpRequest.addHeader(key, value);
		}
		
		PropertyHelper propertyHelper = PropertyHelper.getInstance();
		String requestSource = propertyHelper.getRequestSource() + propertyHelper.getVersion();
		if(propertyHelper.getRequestSourceHeader() != null){
			httpRequest.addHeader(propertyHelper.getRequestSourceHeader(), requestSource);
		}
		
	}
	
	/**
	 * Method to authorize the given HttpRequest
	 * 
	 * @param context the context
	 * @param httpRequest the http request
	 * @throws FMSException the FMSException
	 */
	private void authorizeRequest(Context context, HttpRequestBase httpRequest) throws FMSException {
		context.getAuthorizer().authorize(httpRequest);
	}
	
	/**
	 * Method to get the retry handler which is used to retry to establish the HTTP connection
	 * 
	 * @return returns the IntuitRetryPolicyHandler
	 * @throws FMSException the FMSException
	 */
	private IntuitRetryPolicyHandler getRetryHandler() throws FMSException {
		IntuitRetryPolicyHandler handler = null;
		String policy = Config.getProperty(Config.RETRY_MODE);
		if (policy.equalsIgnoreCase("fixed")) {
			String retryCountStr = Config.getProperty(Config.RETRY_FIXED_COUNT);
			String retryIntervalStr = Config.getProperty(Config.RETRY_FIXED_INTERVAL);
			try {
				handler = new IntuitRetryPolicyHandler(Integer.parseInt(retryCountStr),
						Integer.parseInt(retryIntervalStr));
			} catch (NumberFormatException e) {
				throw new ConfigurationException(e);
			}
			
		} else if (policy.equalsIgnoreCase("incremental")) {
			String retryCountStr = Config.getProperty(Config.RETRY_INCREMENTAL_COUNT);
			String retryIntervalStr = Config.getProperty(Config.RETRY_INCREMENTAL_INTERVAL);
			String retryIncrementStr = Config.getProperty(Config.RETRY_INCREMENTAL_INCREMENT);
			try {
				handler = new IntuitRetryPolicyHandler(Integer.parseInt(retryCountStr),
						Integer.parseInt(retryIntervalStr), Integer.parseInt(retryIncrementStr));
			} catch (NumberFormatException e) {
				throw new ConfigurationException(e);
			} 
			
		} else if (policy.equalsIgnoreCase("exponential")) {
			String retryCountStr = Config.getProperty(Config.RETRY_EXPONENTIAL_COUNT);
			String minBackoffStr = Config.getProperty(Config.RETRY_EXPONENTIAL_MIN_BACKOFF);
			String maxBackoffStr = Config.getProperty(Config.RETRY_EXPONENTIAL_MAX_BACKOFF);
			String deltaBackoffStr = Config.getProperty(Config.RETRY_EXPONENTIAL_DELTA_BACKOFF);

			try {
				handler = new IntuitRetryPolicyHandler(Integer.parseInt(retryCountStr),
						Integer.parseInt(minBackoffStr), Integer.parseInt(maxBackoffStr),
						Integer.parseInt(deltaBackoffStr));
			} catch (NumberFormatException e) {
				throw new ConfigurationException(e);
			}
		}

		return handler;
	}

	/**
	 * Method to get the SSL scheme
	 * 
	 * @return returns Scheme
	 * @throws FMSException the FMSException
	 */
	public Scheme getSSLScheme() throws FMSException {
		String path = Config.getProperty(Config.PROXY_KEYSTORE_PATH);
		String pass = Config.getProperty(Config.PROXY_KEYSTORE_PASSWORD);
		Scheme scheme = null;
		FileInputStream instream = null;
		KeyStore trustStore = null;
		SSLSocketFactory factory = null;

		if (path != null && pass != null) {
			try {
				trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				instream = new FileInputStream(new File(path));
				trustStore.load(instream, pass.toCharArray());
				factory = new SSLSocketFactory(trustStore);
				scheme = new Scheme("https", PORT_443, factory);
			} catch (KeyStoreException e) {
				LOG.error("KeyStoreException", e);
				throw new FMSException(e);
			} catch (FileNotFoundException e) {
				LOG.error("FileNotFoundException", e);
				throw new FMSException(e);
			} catch (NoSuchAlgorithmException e) {
				LOG.error("NoSuchAlgorithmException", e);
				throw new FMSException(e);
			} catch (CertificateException e) {
				LOG.error("CertificateException", e);
				throw new FMSException(e);
			} catch (IOException e) {
				LOG.error("IOException", e);
				throw new FMSException(e);
			} catch (KeyManagementException e) {
				LOG.error("KeyManagementException", e);
				throw new FMSException(e);
			} catch (UnrecoverableKeyException e) {
				LOG.error("UnrecoverableKeyException", e);
				throw new FMSException(e);
			} finally {
				try {
					instream.close();
				} catch (IOException e) {
					LOG.error("IOException", e);
					throw new FMSException(e);
				}
			}
		}
		return scheme;
	}

	/**
	 * Method to get proxy
	 * 
	 * @return returns HttpHost
	 */
	public HttpHost getProxy() {
		String host = Config.getProperty(Config.PROXY_HOST);
		String port = Config.getProperty(Config.PROXY_PORT);
		HttpHost proxy = null;
		if (StringUtils.hasText(host) && StringUtils.hasText(port)) {
			proxy = new HttpHost(host, Integer.parseInt(port));
		}
		return proxy;
	}
	
	/**
	 * Method to set proxy authentication
	 * 
	 * @param httpclient the http client
	 */
	public void setProxyAuthentication(DefaultHttpClient httpclient) {
		String username = Config.getProperty(Config.PROXY_USERNAME);
		String password = Config.getProperty(Config.PROXY_PASSWORD);
		
		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
			String host = Config.getProperty(Config.PROXY_HOST);
			String port = Config.getProperty(Config.PROXY_PORT);
			if (StringUtils.hasText(host) && StringUtils.hasText(port)) {
				httpclient.getCredentialsProvider().setCredentials(new AuthScope(host, Integer.parseInt(port)), new UsernamePasswordCredentials(username, password));
			}
		}
	}
	
	/**
	 * Method to set the response elements by reading the values from HttpResponse
	 * 
	 * @param intuitMessage the intuit message object
	 * @param httpResponse the http response object
	 * @throws FMSException the FMSException
	 */
	private void setResponseElements(IntuitMessage intuitMessage, HttpResponse httpResponse) throws FMSException {
		ResponseElements responseElements = intuitMessage.getResponseElements();
		if(httpResponse.getLastHeader(RequestElements.HEADER_PARAM_CONTENT_ENCODING) != null)
		{
		responseElements.setEncodingHeader(httpResponse.getLastHeader(RequestElements.HEADER_PARAM_CONTENT_ENCODING).getValue());
		}
		else 
		{
			responseElements.setEncodingHeader(null);
		}
		if(httpResponse.getLastHeader(RequestElements.HEADER_PARAM_CONTENT_TYPE) !=null)
		{
		responseElements.setContentTypeHeader(httpResponse.getLastHeader(RequestElements.HEADER_PARAM_CONTENT_TYPE).getValue());
		}
		else
		{
			responseElements.setContentTypeHeader(null);
		}
		responseElements.setStatusLine(httpResponse.getStatusLine());
		responseElements.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		try {
			responseElements.setResponseContent(getCopyOfResponseContent(httpResponse.getEntity().getContent()));
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException while get the content from HttpRespose.", e);
			throw new FMSException(e);
		} catch (Exception e) {
			LOG.error("IOException in HTTPClientConnectionInterceptor while reading the entity from HttpResponse.", e);
			throw new FMSException(e);
		}
	}
	
	/**
	 * Method to create the copy of the input stream of response body. This is required while decompress the original content
	 * 
	 * @param is the input stream of the response body
	 * @return InputStream the copy of response body
	 * @throws FMSException the FMSException
	 */
	private InputStream getCopyOfResponseContent(InputStream is) throws FMSException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream copyIs = null; 
		try {
			byte[] bbuf = new byte[LENGTH_256];
			while (true) {
				int r = is.read(bbuf);
				if (r < 0) {
					break;
				}
				baos.write(bbuf, 0, r);
			}
			copyIs = new ByteArrayInputStream(baos.toByteArray());
			return copyIs;
		} catch (IOException ioe) {
			LOG.error("IOException while decompress the data using GZIP compression.", ioe);
			throw new CompressionException(ioe);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					LOG.error("Unable to close ByteArrayOutputStream.");
				}
			}
		}
	}
	
	/**
	 * Method to set the connection and request timeouts by reading from the configuration file or Context object
	 * 
	 * @param client the DefaultHttpClient
	 */
	private void setTimeout(DefaultHttpClient client, Context context) {
        // special logic for Amex. if the customerRequestTimeout is set, use it. otherwise use the timeout in
        //the config file.
        if ( context.getCustomerRequestTimeout() != null) {
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, context.getCustomerRequestTimeout());
        } else {
            String reqTimeout = Config.getProperty(Config.TIMEOUT_REQUEST);
            if (StringUtils.hasText(reqTimeout)) {
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(reqTimeout.trim()));
            }
        }

		String connTimeout = Config.getProperty(Config.TIMEOUT_CONNECTION);
		if (StringUtils.hasText(connTimeout)) {
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(connTimeout.trim()));
		}

	}
}
