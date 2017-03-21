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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

import org.slf4j.Logger;

import com.intuit.ia.exception.OAuthException;
import com.intuit.ia.utils.IAHelperConfig;
import com.intuit.ia.utils.LoggerImpl;
import com.intuit.ia.utils.PlatformConstants;

class OAuthHelper {

	private IAHelperConfig config;

	public OAuthHelper() {
		config = IAHelperConfig.getInstance();
	}

	protected Logger logger = LoggerImpl.getInstance();

	public Map<String, String> getRequestToken(String consumerkey,
			String consumersecret) throws OAuthException {

		logger.debug("Inside getRequestToken, Consumer Key and Secret: "
				+ consumerkey + " " + consumersecret);
		String callback_url = config
				.getProperty(PlatformConstants.OAUTH_CALLBACK_URL);
		logger.debug("callback URL: " + callback_url);
		OAuthConsumer ouathconsumer = new DefaultOAuthConsumer(consumerkey,
				consumersecret);
		try {
			HttpParameters additionalParams = new HttpParameters();
			additionalParams.put("oauth_callback",
					URLEncoder.encode(callback_url, "UTF-8"));
			ouathconsumer.setAdditionalParameters(additionalParams);
		} catch (UnsupportedEncodingException e) {
			logger.debug("Failed to get request token :" + e.getMessage());
			throw new OAuthException(e.getMessage(), e);
		}

		String requestret = "";
		String requestToken = "";
		String requestTokenSecret = "";

		try {
			String signedRequestTokenUrl = ouathconsumer.sign(config
					.getProperty(PlatformConstants.REQUEST_TOKEN_URL));
			logger.debug("signedRequestTokenUrl: " + signedRequestTokenUrl);

			URL url;

			url = new URL(signedRequestTokenUrl);

			HttpURLConnection httpconnection = (HttpURLConnection) url
					.openConnection();

			httpconnection.setRequestMethod("GET");
			httpconnection
					.setRequestProperty("Content-type", "application/xml");
			httpconnection.setRequestProperty("Content-Length", "0");
			if (httpconnection != null) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						httpconnection.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);

				}
				rd.close();
				requestret = sb.toString();
			}
			String[] requestTokenSections = requestret.split("&");

			for (int i = 0; i < requestTokenSections.length; i++) {
				String[] currentElements = requestTokenSections[i].split("=");

				if (currentElements[0].equalsIgnoreCase("oauth_token")) {
					requestToken = currentElements[1];
				} else if (currentElements[0]
						.equalsIgnoreCase("oauth_token_secret")) {
					requestTokenSecret = currentElements[1];
				}
			}

			Map<String, String> requesttokenmap = new HashMap<String, String>();
			requesttokenmap.put("requestToken", requestToken);
			requesttokenmap.put("requestTokenSecret", requestTokenSecret);
			return requesttokenmap;

		} catch (OAuthMessageSignerException e) {
			// LOG.error(e.getLocalizedMessage());
		} catch (OAuthExpectationFailedException e) {
			// LOG.error(e.getLocalizedMessage());
		} catch (OAuthCommunicationException e) {
			// LOG.error(e.getLocalizedMessage());
		} catch (MalformedURLException e) {
			// LOG.error(e.getLocalizedMessage());
		} catch (IOException e) {
			// LOG.error(e.getLocalizedMessage());
		}
		logger.debug("Error: Failed to get request token.");
		return null;

	}

	public String getAuthorizeUrl(String requestToken) throws OAuthException {

		String authorizeURL = "";

		try {
			authorizeURL = config.getProperty(PlatformConstants.AUTHORIZE_URL)
					+ "?oauth_token=" + requestToken;
		} catch (Exception e) {
			// LOG.error(e.getLocalizedMessage());

		}

		logger.debug("Authorize URL: " + authorizeURL);
		return authorizeURL;

	}

	public Map<String, String> getAccessToken(String verifierCode,
			String requestToken, String requestTokenSecret, String consumerkey,
			String consumersecret) throws OAuthException {
		String accessToken = "";
		String accessTokenSecret = "";
		String accesTokenUrl = "";
		Map<String, String> accesstokenmap = new HashMap<String, String>();
		try {
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumerkey,
					consumersecret);
			consumer.setTokenWithSecret(requestToken, requestTokenSecret);
			HttpParameters additionalParams = new HttpParameters();
			additionalParams.put("oauth_callback", "oob");
			additionalParams.put("oauth_verifier", verifierCode);
			consumer.setAdditionalParameters(additionalParams);
			accesTokenUrl = IAHelperConfig.getInstance().getProperty(
					PlatformConstants.ACCESS_TOKEN_URL);
			logger.debug("Access token url : " + accesTokenUrl);
			String signedURL = consumer.sign(accesTokenUrl);
			URL url = new URL(signedURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-type", "application/xml");
			urlConnection.setRequestProperty("Content-Length", "0");

			String accesstokenresponse = "";
			if (urlConnection != null) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				accesstokenresponse = sb.toString();
			}
			if (accesstokenresponse != null) {
				String[] responseElements = accesstokenresponse.split("&");
				if (responseElements.length > 1) {
					accessToken = responseElements[1].split("=")[1];
					accessTokenSecret = responseElements[0].split("=")[1];
					accesstokenmap.put("accessToken", accessToken);
					accesstokenmap.put("accessTokenSecret", accessTokenSecret);
					return accesstokenmap;
				}
			}
		} catch (Exception e) {
			logger.debug("Failed to get access token :" + e.getMessage());
			throw new OAuthException(e.getMessage(), e);
		}
		return accesstokenmap;
	}

}
