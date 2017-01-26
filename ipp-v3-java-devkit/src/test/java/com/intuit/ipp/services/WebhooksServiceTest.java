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
package com.intuit.ipp.services;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.data.WebhooksEvent;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Config;

public class WebhooksServiceTest {

	private WebhooksService webhooksService = null;
	
	private static final String verifierToken = "b5771f19b12ddb4bcdb9f08bdc50bc63878a";
	private static final String intuitSignature = "JC2GUwciPmRBwEO7WvrRmKqu9tGOKL8FFallocTFi8Q=";
	private static final String payload = "{\"eventNotifications\":[{\"realmId\":\"1185883450\",\"dataChangeEvent\":{\"entities\":[{\"name\":\"Customer\",\"id\":\"1\",\"operation\":\"Create\",\"lastUpdated\":\"2015-10-05T14:42:19-0700\"}]}}]}";
	
	@BeforeClass
	public void setup() throws FMSException {
		// set custom config
		Config.setProperty(Config.WEBHOOKS_VERIFIER_TOKEN, verifierToken);
		
		// create webhooks service
		webhooksService = new WebhooksService();
	}

	@Test
	public void testVerifyPayload() throws FMSException {
		
		boolean result = webhooksService.verifyPayload("1234", payload);
		Assert.assertFalse(result);
		result = webhooksService.verifyPayload(intuitSignature, payload);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testGetWebhooksEvent() throws FMSException {
		
		WebhooksEvent webhooksEvent = webhooksService.getWebhooksEvent(payload);
		Assert.assertNotNull(webhooksEvent);
		Assert.assertEquals(webhooksEvent.getEventNotifications().size(), 1);
	}
	

}
