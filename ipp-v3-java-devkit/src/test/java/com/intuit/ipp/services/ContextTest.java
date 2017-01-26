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
import com.intuit.ipp.services.IPPHelper;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;

public class ContextTest {
	
	@BeforeClass
	public static void setup(){
		Config.setProperty(Config.HTTP_TRANSPORT, "");
	}
	

	@Test(enabled = false)
	public void testServiceType_null() {
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		ServiceType serviceType = null;
		try {
			new Context(oauth, ippHelper.getQboAppToken(), serviceType, ippHelper.getQboRealmID());
		} catch (FMSException e) {
			Assert.assertNotNull(e);
		}
	}
	
	
}
