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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.MethodType;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.PropertyHelper;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to establish a HTTP connection using java.net.HttpUrlConnection
 * @author rahula238
 *
 */
public class HTTPURLConnectionInterceptor implements Interceptor {
	public static final String HTTP_URL_CONNECTION="HTTP_URL_CONNECTION";
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable LENGTH_256
	 */
	private static final int LENGTH_256 = 256;
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {	
		LOG.debug("Enter HTTPURLConnectionInterceptor...");
		RequestElements intuitRequest = intuitMessage.getRequestElements();
		
		URI uri = null;
		HttpURLConnection httpUrlConnection = null;
		String methodtype = intuitRequest.getRequestParameters().get(RequestElements.REQ_PARAM_METHOD_TYPE);
		
		//setup proxy if configured
		Proxy proxy = setupProxy();
		
		try {
			uri = new URI(intuitRequest.getRequestParameters().get(RequestElements.REQ_PARAM_RESOURCE_URL));
			URL url = uri.toURL();	
			
			httpUrlConnection = proxy==null? (HttpURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection(proxy);
			httpUrlConnection.setRequestMethod(methodtype);
			
			//set timeouts
			setTimeout(httpUrlConnection);				
			
			// populate the headers
			populateRequestHeaders(httpUrlConnection, intuitRequest.getRequestHeaders());
					
			// authorize the request
			authorizeRequest(intuitRequest.getContext(), httpUrlConnection);			
			
			if(intuitRequest.getCompressedData()!=null){
				httpUrlConnection.setDoOutput(true);
			}
			
			LOG.debug("Request URI : " + uri);
			LOG.debug("Request Method : " + methodtype);
			LOG.debug("Request Headers : " + httpUrlConnection.getRequestProperties());
			LOG.debug("Request Body : " + intuitRequest.getSerializedData());
			
			//connect
			httpUrlConnection.connect();
			
			// Send the payload (if any).
			if (methodtype.equals(MethodType.POST.toString())) {
				byte[] compressedData = intuitRequest.getCompressedData();
				if (compressedData != null) {					
					DataOutputStream  output = new DataOutputStream(httpUrlConnection.getOutputStream());					
					output.write(compressedData);
					output.flush();
					output.close();
				}
			}
			
			setResponseElements(intuitMessage, httpUrlConnection);
			
		} catch (URISyntaxException e) {
			throw new FMSException("URISyntaxException", e);
		} catch (IOException e) {
			throw new FMSException("IO Exception", e);
		} catch(Throwable e){
			throw new FMSException("Unexpected expection", e);
		}
		finally{
			// Make sure to always disconnect.
			httpUrlConnection.disconnect();
		}		
		
		LOG.debug("Exit HTTPURLConnectionInterceptor.");
	}
	
	/**
	 * Method to populate the HTTP request headers by reading it from the requestHeaders Map 
	 */
	private void populateRequestHeaders(HttpURLConnection httpUrlConnection, Map<String, String> requestHeaders) {
		
		Set<String> keySet = requestHeaders.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			String value = requestHeaders.get(key);
			httpUrlConnection.setRequestProperty(key, value);
		}
		
		PropertyHelper propertyHelper = PropertyHelper.getInstance();
		String requestSource = propertyHelper.getRequestSource() + propertyHelper.getVersion();
		if(propertyHelper.getRequestSourceHeader() != null){
			httpUrlConnection.setRequestProperty(propertyHelper.getRequestSourceHeader(), requestSource);
		}
		

	}
	
	private void authorizeRequest(Context context, HttpURLConnection httpUrlConnection) throws FMSException {
		context.getAuthorizer().authorize(httpUrlConnection);
	}
		
	private Proxy setupProxy() {
		String host = Config.getProperty(Config.PROXY_HOST);
		String port = Config.getProperty(Config.PROXY_PORT);			
		
		if (StringUtils.hasText(host) && StringUtils.hasText(port)) {
			//need to set proxy
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, Integer.parseInt(port)));
							
			final String username = Config.getProperty(Config.PROXY_USERNAME);
			final String password = Config.getProperty(Config.PROXY_PASSWORD);
			
			if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
				//need to set proxy auth
				Authenticator authenticator = new Authenticator() {

			        public PasswordAuthentication getPasswordAuthentication() {
			            return (new PasswordAuthentication(username, password.toCharArray()));
			        }
			    };
			    Authenticator.setDefault(authenticator);				
			}					
			return proxy;
		}
		return null;		
	}
	
	/**
	 * Method to set the response elements by reading the values from the response
	 */
	private void setResponseElements(IntuitMessage intuitMessage, HttpURLConnection httpUrlConnection) throws FMSException {
		LOG.debug("Response headers:"+httpUrlConnection.getHeaderFields());
		ResponseElements responseElements = intuitMessage.getResponseElements();
		responseElements.setEncodingHeader(httpUrlConnection.getContentEncoding());
		responseElements.setContentTypeHeader(httpUrlConnection.getContentType());		
		try {
			responseElements.setStatusCode(httpUrlConnection.getResponseCode());
			
			InputStream responseStream;
			try {
				responseStream = httpUrlConnection.getInputStream();
			}
			catch (IOException ioe) {
				// Any error response from the server. Read the error stream instead of the responseStream. It contains the response from the server for the error code.
				responseStream = httpUrlConnection.getErrorStream();
			}
		
			responseElements.setResponseContent(getCopyOfResponseContent(responseStream));
		} catch (IllegalStateException e) {
			LOG.error("IllegalStateException while get the content from HttpRespose.", e);
			throw new FMSException(e);
		} catch (Exception e) {
			LOG.error("IOException in HTTPURLConnectionInterceptor while reading the entity from HttpResponse.", e);
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
	 * Method to set the connection and request timeouts by reading from the configuration file	 
	 */
	private void setTimeout(HttpURLConnection httpUrlConnection) {
		String connTimeout = Config.getProperty(Config.TIMEOUT_CONNECTION);
		if (StringUtils.hasText(connTimeout)) {
			httpUrlConnection.setReadTimeout(new Integer(connTimeout.trim()));			
		}
		String reqTimeout = Config.getProperty(Config.TIMEOUT_REQUEST);
		if (StringUtils.hasText(reqTimeout)) {
			httpUrlConnection.setConnectTimeout(new Integer(reqTimeout.trim()));			
		}
	}
}