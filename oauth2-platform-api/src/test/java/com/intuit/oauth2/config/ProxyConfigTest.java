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
package com.intuit.oauth2.config;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Config class to hold the proxy properties
 *
 * @author dderose
 */
public class ProxyConfigTest {

	private ProxyConfig.ProxyConfigBuilder initProxyConfigBuilder()
	{
		ProxyConfig.ProxyConfigBuilder proxyConfigBuilder = new ProxyConfig.ProxyConfigBuilder("oauth.platform.intuit.com","8080");
		proxyConfigBuilder.username("myusername");
		proxyConfigBuilder.password("mypassword");
		proxyConfigBuilder.domain("mydomain");
		return proxyConfigBuilder;
	}


	@Test
	public void testProxyConfigBuilder()
	{
		ProxyConfig.ProxyConfigBuilder proxyConfigBuilder = initProxyConfigBuilder();
		ProxyConfig proxyConfig = proxyConfigBuilder.buildConfig();
		assertNotNull(proxyConfigBuilder);
		assertNotNull(proxyConfig);
		assertEquals("oauth.platform.intuit.com", proxyConfig.getHost());
		assertEquals("8080", proxyConfig.getPort());
		assertEquals("myusername", proxyConfig.getUsername());
		assertEquals("mypassword", proxyConfig.getPassword());
		assertEquals("mydomain", proxyConfig.getDomain());
	}


}
