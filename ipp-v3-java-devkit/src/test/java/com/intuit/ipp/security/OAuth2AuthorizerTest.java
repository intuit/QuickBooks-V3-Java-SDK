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

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.Header;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.interceptors.HTTPURLConnectionInterceptor;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;

public class OAuth2AuthorizerTest {

	private org.slf4j.Logger log = Logger.getLogger();
	private final String URL_STRING = "http://code.intuit.com";

	public String extractHeaderParams(Header[] header,
			HttpUriRequestBase requestBase) {

		String value = null;
		if (header != null)
			for (Header head : header) {
				log.debug(head.toString());
				if (head.getName().equals("Authorization")) {
					return head.getValue().toString();
					/*headerArray = head.getValue().split("=,");
					headerArray = headerArray[0].split(",");

					for (int counter = 0; counter < headerArray.length; counter++) {
						headerItemArray = headerArray[counter].split("=");
						if (headerItemArray[0].equals(headerName)) {
							value = headerItemArray[1].replace("\"", "");
							return value;
						}
					}*/
				}

			}
		return value;
	}

	@Test
	public void oAuthAuthorizerWithAccessTokenSecret() {

		String accessToken = "TestAccessToken";
		Header[] header = null;
		HttpUriRequestBase requestBase = new HttpGet(URL_STRING);
		try {
			OAuth2Authorizer oauthAuthorizer = new OAuth2Authorizer(accessToken);
			oauthAuthorizer.authorize(requestBase);

			header = requestBase.getHeaders();
			Assert.assertEquals("Bearer "+accessToken,extractHeaderParams(header, requestBase));
			

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

		Config.setProperty(Config.HTTP_TRANSPORT, HTTPURLConnectionInterceptor.HTTP_URL_CONNECTION);
		
		try {
			URL url = new URL(URL_STRING);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			OAuth2Authorizer oauthAuthorizer = new OAuth2Authorizer(accessToken);

			oauthAuthorizer.authorize(conn);


		} catch (Exception e) {			
			log.debug(e.getMessage());
			Assert.fail("Exception should not happen");
		}
	}

}
