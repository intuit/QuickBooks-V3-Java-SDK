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
package com.intuit.ipp.security;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.interceptors.HTTPURLConnectionInterceptor;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;

public class OAuthAuthorizerTest {

	private org.slf4j.Logger log = Logger.getLogger();
	private final String URL_STRING = "http://code.intuit.com";

	public String extractHeaderParams(Header[] header,
			HttpRequestBase requestBase, String headerName) {

		String[] headerArray = null;
		String[] headerItemArray = null;
		String value = null;
		if (header != null)
			for (Header head : header) {
				log.debug(head.toString());
				if (head.getName().equals("Authorization")) {
					headerArray = head.getValue().split("=,");
					headerArray = headerArray[0].split(",");

					for (int counter = 0; counter < headerArray.length; counter++) {
						headerItemArray = headerArray[counter].split("=");
						if (headerItemArray[0].equals(headerName)) {
							value = headerItemArray[1].replace("\"", "");
							return value;
						}
					}
				}

			}
		return value;
	}

	@Test
	public void oAuthAuthorizerWithAccessTokenSecret() {

		String accessToken = "TestAccessToken";
		String accessTokenSecret = "TestAccessTokenSecret";
		String consumerKey = "TestConsumerKey";
		String consumerSecret = "TestConsumerSecret";
		Header[] header = null;
		HttpRequestBase requestBase = new HttpGet();
		try {
			requestBase.setURI(new URI(URL_STRING));
			OAuthAuthorizer oauthAuthorizer = new OAuthAuthorizer(consumerKey,
					consumerSecret, accessToken, accessTokenSecret);
			oauthAuthorizer.authorize(requestBase);

			header = requestBase.getAllHeaders();
			Assert.assertEquals(
					accessToken,
					extractHeaderParams(header, requestBase,
							"OAuth oauth_token"),
					"The authorization header is not proper,oauth_token not present");
			Assert.assertEquals(
					consumerKey,
					extractHeaderParams(header, requestBase,
							" oauth_consumer_key"),
					"The authorization header is not proper,oauth_consumer_key not present");

		} catch (Exception e) {

			log.debug(e.getMessage());
		}
	}

	@Test
	public void oAuthAuthorizerExceptionTest() {

	}
	
	@Test
	public void oAuthAuthorizerWithAccessTokenSecretHttpUrlConnection() {

		String accessToken = "TestAccessToken";
		String accessTokenSecret = "TestAccessTokenSecret";
		String consumerKey = "TestConsumerKey";
		String consumerSecret = "TestConsumerSecret";

		Config.setProperty(Config.HTTP_TRANSPORT, HTTPURLConnectionInterceptor.HTTP_URL_CONNECTION);
		
		try {
			URL url = new URL(URL_STRING);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			OAuthAuthorizer oauthAuthorizer = new OAuthAuthorizer(consumerKey,
					consumerSecret, accessToken, accessTokenSecret);

			oauthAuthorizer.authorize(conn);

			//unable to get Authorization header due to security restriction			
			//http://stackoverflow.com/questions/2864062/getrequestpropertyauthorization-always-returns-null

			//no exception is success

		} catch (Exception e) {			
			log.debug(e.getMessage());
			Assert.fail("Exception should not happen");
		}
	}

}
