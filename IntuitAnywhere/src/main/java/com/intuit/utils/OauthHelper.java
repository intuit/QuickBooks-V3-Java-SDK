/*
 * Copyright (c) 2011 Intuit, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * Contributors:
 *
 *    Intuit Partner Platform - initial contribution 
 *
 */

package com.intuit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;

import org.apache.log4j.Logger;

/*
 * This is a utility class for OAuth routines.
 */

public class OauthHelper {

	public static final Logger LOG = Logger.getLogger(OauthHelper.class);

	public static String REQUEST_TOKEN_URL;
	public static String ACCESS_TOKEN_URL;
	public static String AUTHORIZE_URL;

	public OauthHelper() {

		REQUEST_TOKEN_URL = WebUtils.OAUTH_URL + "/oauth/v1/get_request_token";
		ACCESS_TOKEN_URL = WebUtils.OAUTH_URL + "/oauth/v1/get_access_token";
		AUTHORIZE_URL = WebUtils.APPCENTER_URL + "/Connect/Begin";
	}

	public void getDynamicConsumer() {

		try {

			final String apptoken = WebUtils.APP_TOKEN;

			final URL url = new URL(WebUtils.OAUTH_URL
					+ "/oauth/v1/create_consumer?appToken=" + apptoken);
			final HttpURLConnection httpconnection = (HttpURLConnection) url
					.openConnection();
			httpconnection.connect();

			StringBuffer responseBody = null;
			int read = 0;
			final byte buffer[] = new byte[8192];
			String consumerret = "";
			String consumerkeytoken = "";
			String consumerkeysecret = "";

			try {
				final InputStream responseBodyStream = httpconnection
						.getInputStream();
				responseBody = new StringBuffer();

				while ((read = responseBodyStream.read(buffer)) != -1) {
					responseBody.append(new String(buffer, 0, read));
				}
				responseBodyStream.close();
				consumerret = responseBody.toString();

				final String[] consumerkey = consumerret.split("&");

				for (int i = 0; i < consumerkey.length; i++) {
					final String[] currentElements = consumerkey[i].split("=");

					if (currentElements[0].equalsIgnoreCase("oauth_token")) {
						consumerkeytoken = currentElements[1];
					} else if (currentElements[0]
							.equalsIgnoreCase("oauth_token_secret")) {
						consumerkeysecret = currentElements[1];
					}
				}

			} catch (Exception ex1) {

				final int httpRespCode = httpconnection.getResponseCode();
				try {
					final InputStream es = httpconnection.getErrorStream();
					final StringBuffer errorBody = new StringBuffer();
					while ((read = es.read(buffer)) != -1) {
						errorBody.append(new String(buffer, 0, read));
					}
				} catch (Exception ex2) {
					LOG.error(ex2.getLocalizedMessage());
				}
			}

		} catch (Exception ex3) {
			LOG.error(ex3.getLocalizedMessage());
		}
	}

	public Map<String, String> getRequestTokenSignPost() {

		String authURL = null;

		OAuthProvider provider = createProvider();

		String consumerkey = WebUtils.OAUTH_CONSUMER_KEY;
		String consumersecret = WebUtils.OAUTH_CONSUMER_SECRET;

		LOG.info("Inside getRequestToken, Consumer Key and Secret: "
				+ consumerkey + " " + consumersecret);
		String callback_url = WebUtils.OAUTH_CALLBACK_URL;
		LOG.info("callback URL: " + callback_url);

		OAuthConsumer ouathconsumer = new DefaultOAuthConsumer(consumerkey,
				consumersecret);

		try {
			HttpParameters additionalParams = new HttpParameters();
			additionalParams.put("oauth_callback",
					URLEncoder.encode(callback_url, "UTF-8"));
			ouathconsumer.setAdditionalParameters(additionalParams);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getLocalizedMessage());
		}

		String requestret = "";
		String requestToken = "";
		String requestTokenSecret = "";

		try {
			String signedRequestTokenUrl = ouathconsumer
					.sign(REQUEST_TOKEN_URL);
			LOG.info("signedRequestTokenUrl: " + signedRequestTokenUrl);

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

			try {
				authURL = provider.retrieveRequestToken(ouathconsumer,
						callback_url);
			} catch (OAuthNotAuthorizedException e) {
				LOG.error(e.getLocalizedMessage());
			}
			ouathconsumer.setTokenWithSecret(ouathconsumer.getToken(),
					ouathconsumer.getTokenSecret());

			requesttokenmap.put("requestToken", requestToken);
			requesttokenmap.put("requestTokenSecret", requestTokenSecret);
			requesttokenmap.put("authURL", authURL);
			return requesttokenmap;

		} catch (OAuthMessageSignerException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (OAuthExpectationFailedException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (OAuthCommunicationException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (MalformedURLException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage());
		}
		LOG.info("Error: Failed to get request token.");
		return null;

	}

	public static OAuthProvider createProvider() {
		OAuthProvider provider = new DefaultOAuthProvider(
				OauthHelper.REQUEST_TOKEN_URL, OauthHelper.ACCESS_TOKEN_URL,
				OauthHelper.AUTHORIZE_URL);

		return provider;
	}

	public String getAuthorizeURL(String requestToken, String requestTokenSecret) {

		String authorizeURL = "";
		try {
			authorizeURL = AUTHORIZE_URL + "?oauth_token=" + requestToken;
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
		LOG.info("Authorize URL: " + authorizeURL);
		return authorizeURL;
	}

	public Map<String, String> getAccessToken(String verifierCode,
			String requestToken, String requestTokenSecret) {
		String consumerkey = WebUtils.OAUTH_CONSUMER_KEY;
		String consumersecret = WebUtils.OAUTH_CONSUMER_SECRET;
		String accessToken = "";
		String accessTokenSecret = "";

		try {
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumerkey,
					consumersecret);
			consumer.setTokenWithSecret(requestToken, requestTokenSecret);

			HttpParameters additionalParams = new HttpParameters();
			additionalParams.put("oauth_callback", "oob");
			additionalParams.put("oauth_verifier", verifierCode);
			consumer.setAdditionalParameters(additionalParams);

			String signedURL = consumer.sign(ACCESS_TOKEN_URL);
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
					LOG.info("OAuth accessToken: " + accessToken);
					LOG.info("OAuth accessTokenSecret: " + accessTokenSecret);
					Map<String, String> accesstokenmap = new HashMap<String, String>();
					accesstokenmap.put("accessToken", accessToken);
					accesstokenmap.put("accessTokenSecret", accessTokenSecret);
					return accesstokenmap;
				}
			}

		} catch (OAuthMessageSignerException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (OAuthExpectationFailedException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (OAuthCommunicationException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}
		return null;
	}

}
