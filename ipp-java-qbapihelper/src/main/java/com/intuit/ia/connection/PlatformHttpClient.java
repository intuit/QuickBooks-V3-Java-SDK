/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ia.connection;
/**
 * 
 * This class implements the disconnect and getAppMenu API by invoking appropriate end point urls configured
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.slf4j.Logger;

import com.intuit.ia.exception.ConnectionException;
import com.intuit.ia.utils.IAHelperConfig;
import com.intuit.ia.utils.LoggerImpl;
import com.intuit.ia.utils.PlatformConstants;
import com.intuit.ia.utils.WebUtils;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;

class PlatformHttpClient {
	
	
	private Map<String, String> requestHeaders = new Hashtable<String, String>();
	private HttpURLConnection connection;
	private int timeout = 1200;
	private OAuthConsumer oauthConsumer;
	private String urlString;
	Logger logger = LoggerImpl.getInstance();
	public PlatformHttpClient(String consumerKey,String consumerSecret,String accessToken,String accessTokenSecret) {
		
		this.oauthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		oauthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
		oauthConsumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
	}

	private void openConnection() throws Exception {
		requestHeaders.put("Content-type", "application/xml");
		requestHeaders.put("Content-Length", "0");
		doGet();
	}

	public PlatformResponse disconnect() throws ConnectionException {

		try {
			urlString = IAHelperConfig.getInstance().getProperty(PlatformConstants.DISCON_URL_KEY);
			
			openConnection();
			if (connection != null) {

				Document respDoc = getResponseXmlDocument();
				
				PlatformResponse response = new PlatformResponse();
				response.setErrorCode(WebUtils.getElementText(respDoc.getRootElement(), "ErrorCode"));
				response.setErrorMessage(WebUtils.getElementText(respDoc.getRootElement(), "ErrorMessage"));
				response.setServerTime(WebUtils.getElementText(respDoc.getRootElement(), "ServerTime"));
				
				if (!response.getErrorCode().equals("0")) {
					throw new ConnectionException(response.getErrorMessage(), response.getErrorCode());
				}
				return response;
			}

		} catch (ConnectionException conEx) {
			throw conEx;
		}

		catch (Exception e) {
			throw new ConnectionException("Failed to disconnect: "
					+ e.getCause() + " " + e.getMessage());
		}
		return null;
	}

	public List<String> getAppMenu() throws Exception {
		List<String> respList = new ArrayList<String>();

		try {
			urlString = IAHelperConfig.getInstance().getProperty(PlatformConstants.APP_URL_KEY);
			logger.debug("The url string is :" + urlString);
			openConnection();
			StringBuffer sb = new StringBuffer();
			
			if (connection != null) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
					respList.add(line);
				}
				rd.close();
			}
		} catch (Exception e) {
			throw new Exception("Failed to get App Menu: " + e.getCause() + " "
					+ e.getMessage());
		}
		
		return respList;
	}

	private void doGet() throws Exception {
		
		String timeOutString;
		URL url = new URL(urlString);
		Proxy httpProxy = null;
		String host;
		String port;
		try {
			host = IAHelperConfig.getInstance().getProperty(PlatformConstants.HTTP_PROXY_HOST_KEY);
			port = IAHelperConfig.getInstance().getProperty(PlatformConstants.HTTP_PROXY_PORT_KEY);
			if (host != null && port != null) {
				httpProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						host, Integer.parseInt(port)));
				this.connection = (HttpURLConnection) url
						.openConnection(httpProxy);
			} else {
				this.connection = (HttpURLConnection) url.openConnection();
			}

		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Not an HTTP URL");
		}
		try{
		// Set the request method
		this.connection.setRequestMethod(PlatformConstants.HTTP_VERB_GET);
		timeOutString = IAHelperConfig.getInstance().getProperty("time_out");
		if(timeOutString != null)
			timeout = Integer.parseInt(timeOutString);
		this.connection.setConnectTimeout(timeout);
		logger.debug("Proceeding for signing");
		// For OAuth authentication.
		if (oauthConsumer != null) {
			oauthConsumer.sign(this.connection);
		}
		}
		catch(Exception e)
		{
			
			logger.debug("Exception occured in preparing the request :" + e.getMessage());
		}
	}

	public Document getResponseXmlDocument() throws IOException {

		InputStream inputStream = null;
		Document responseDocument;
		try {

			inputStream = connection.getInputStream();
			responseDocument = WebUtils.createXmlDocument(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			this.connection.disconnect();
			this.connection = null;
		}
		return responseDocument;
	}
	
	public PlatformResponse reConnect() throws ConnectionException {

		try {
			
			urlString = IAHelperConfig.getInstance().getProperty(PlatformConstants.RECONNECT_URL_KEY);
			openConnection();
			if (connection != null) {

				Document respDoc = getResponseXmlDocument();

				PlatformResponse response = new PlatformResponse();
				response.setErrorCode(WebUtils.getElementText(respDoc.getRootElement(), "ErrorCode"));
				response.setErrorMessage(WebUtils.getElementText(respDoc.getRootElement(), "ErrorMessage"));
				response.setOauthToken(WebUtils.getElementText(respDoc.getRootElement(), "OAuthToken"));
				response.setOauthTokenSecret(WebUtils.getElementText(respDoc.getRootElement(), "OAuthTokenSecret"));
				response.setServerTime(WebUtils.getElementText(respDoc.getRootElement(), "ServerTime"));

				if (!response.getErrorCode().equals("0")) {
					throw new ConnectionException(response.getErrorMessage(), response.getErrorCode());
				}
				return response;
			}

		} catch (ConnectionException conEx) {
			throw conEx;
		}

		catch (Exception e) {
			throw new ConnectionException("Failed to reconnect: "
					+ e.getCause() + " " + e.getMessage());
		}
		return null;
	}
	public User getCurrentUser() throws Exception {
		User user = null;

		try {
			urlString = IAHelperConfig.getInstance().getProperty(PlatformConstants.CURRENT_USER_URL);
			logger.debug("The url string is :" + urlString);
			openConnection();
			
			if (connection != null) {
				Document respDoc = getResponseXmlDocument();
				String errorCode = WebUtils.getElementText(respDoc.getRootElement(), "ErrorCode");
				String errorMsg = WebUtils.getElementText(respDoc.getRootElement(), "ErrorMessage");
				if (!errorCode.equals("0")) {
					throw new ConnectionException(errorMsg, errorCode);
				}else
				{
					user= new User();
					Element e = WebUtils.getElementByTagName(respDoc.getRootElement(), "User");
					user.setEmailAddress(WebUtils.getElementText(e, "EmailAddress"));
					user.setFirstName(WebUtils.getElementText(respDoc.getRootElement(), "FirstName"));
					user.setIsVerified(WebUtils.getElementText(respDoc.getRootElement(), "IsVerified"));
					user.setLastName(WebUtils.getElementText(respDoc.getRootElement(), "LastName"));
				}
			}
		} catch (Exception e) {
			throw new Exception("Failed to get CurrentUser: " + e.getCause() + " "
					+ e.getMessage());
		}
		return user;
	}
}
