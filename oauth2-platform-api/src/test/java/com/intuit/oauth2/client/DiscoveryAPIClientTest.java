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
package com.intuit.oauth2.client;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.data.DiscoveryAPIResponse;
import com.intuit.oauth2.exception.ConnectionException;


/**
 * @author dderose
 *
 */
public class DiscoveryAPIClientTest {
		
	@Test
	public void testCallDiscoverAPIForProduction() throws ConnectionException {
		
		DiscoveryAPIResponse discoveryAPIResponse = new DiscoveryAPIClient(null).callDiscoveryAPI(Environment.PRODUCTION);
		assertNotNull(discoveryAPIResponse);
		assertEquals("https://oauth.platform.intuit.com/op/v1", discoveryAPIResponse.getIssuer());
		
	}
	
	@Test
	public void testCallDiscoverAPIForSandbox() throws ConnectionException {
		
		DiscoveryAPIResponse discoveryAPIResponse = new DiscoveryAPIClient(null).callDiscoveryAPI(Environment.SANDBOX);
		assertNotNull(discoveryAPIResponse);
		assertEquals("https://sandbox-accounts.platform.intuit.com/v1/openid_connect/userinfo", discoveryAPIResponse.getUserinfoEndpoint());
		
	}
	
	
}
