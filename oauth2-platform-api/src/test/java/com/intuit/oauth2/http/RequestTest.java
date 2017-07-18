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
package com.intuit.oauth2.http;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.exception.PlatformException;

public class RequestTest {
	
	OAuth2Config oauth2Config;
	private static final String clientId = "Q05MIy6oCjARUBablYbLLth6D7xh3dpMwWxOLnHYKM4WIqWBDP"; 
	private static final String clientSecret = "8dFUOx16fwJ4ZLNN7onJQ0vFG8ybkHf05uaz6T4b";
	
	
	@BeforeClass
	public void setUp() {
		
		 oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(clientId, clientSecret) //set client id, secret
				.callDiscoveryAPI(Environment.SANDBOX) // call discovery API to populate urls
				.buildConfig();
	}
	
	@Test
    public void testConstructorWithDomain() {
        Request request = new Request.RequestBuilder(MethodType.GET, oauth2Config.getIntuitBearerTokenEndpoint()).build();
        assertNotNull(request);
        assertEquals(MethodType.GET, request.getMethod());
        assertEquals(oauth2Config.getIntuitBearerTokenEndpoint(), request.getUrl());
    }

	@Test
    public void testConstructURL() throws PlatformException, MalformedURLException {
		Request request = new Request.RequestBuilder(MethodType.GET, oauth2Config.getIntuitBearerTokenEndpoint()).build();
        URL url = request.constructURL();
        URL expected = new URL(oauth2Config.getIntuitBearerTokenEndpoint());
        assertEquals(expected, url);
    }
}
