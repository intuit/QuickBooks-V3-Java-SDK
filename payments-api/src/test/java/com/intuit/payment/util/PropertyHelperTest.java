/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author dderose
 *
 */
public class PropertyHelperTest {

	@Test
	public void testPropertyHelper() {
		PropertiesConfig propertyHelper = PropertiesConfig.getInstance();
		Assert.assertNotNull(propertyHelper);
	}
	
	@Test
	public void testPropertyVersion() {
		PropertiesConfig propertyHelper = PropertiesConfig.getInstance();
		String version = propertyHelper.getProperty("version");
		Assert.assertNotNull(version);
	}
	
	@Test
	public void testPropertyTLSVersion() {
		PropertiesConfig propertyHelper = PropertiesConfig.getInstance();
		String tlsVersion = propertyHelper.getProperty("TLS_VERSION");
		Assert.assertNotNull(tlsVersion);
		Assert.assertEquals("TLSv1.2", tlsVersion);
	}
	
	@Test
	public void testPropertyProdBaseUrl() {
		PropertiesConfig propertyHelper = PropertiesConfig.getInstance();
		String baseUrl = propertyHelper.getProperty("PAYMENTS_BASE_URL_PRODUCTION");
		Assert.assertNotNull(baseUrl);
		Assert.assertEquals("https://api.intuit.com/quickbooks/v4/payments/", baseUrl);
	}
	
	@Test
	public void testPropertySandboxBaseUrl() {
		PropertiesConfig propertyHelper = PropertiesConfig.getInstance();
		String baseUrl = propertyHelper.getProperty("PAYMENTS_BASE_URL_SANDBOX");
		Assert.assertNotNull(baseUrl);
		Assert.assertEquals("https://sandbox.api.intuit.com/quickbooks/v4/payments/", baseUrl);
	}
}
