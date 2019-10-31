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

import com.intuit.ipp.security.IAuthorizer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ipp.services.IPPHelper;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ContextTest {
	
	@BeforeClass
	public static void setup(){
		Config.setProperty(Config.HTTP_TRANSPORT, "");
	}

	private Context getContextForTest() {
		Context context;
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		try {
			context = new Context(oauth, ippHelper.getQboAppToken(), ServiceType.QBO, ippHelper.getQboRealmID());
		} catch(FMSException e) {
			context = null;
		}
		return context;
	}

	@Test
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


	@Test
	public void testServiceType_nullConstructor() {
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		ServiceType serviceType = null;
		try {
			new Context(oauth, serviceType, ippHelper.getQboRealmID());
		} catch (FMSException e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void testCustomerRequestTimeout() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		Integer timeout = 2;
		context.setCustomerRequestTimeout(timeout);
		Assert.assertEquals(context.getCustomerRequestTimeout(), timeout);
	}

	@Test
	public void testIncludeParam() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		List<String> testList = new ArrayList<>();
		testList.add("testString");
		context.setIncludeParam(testList);
		Assert.assertEquals(context.getIncludeParam(), testList);
	}

	@Test
	public void testTicket() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String ticket = "testTicket";
		context.setTicket(ticket);
		Assert.assertEquals(context.getTicket(), ticket);
	}

	@Test
	public void testAppToken() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String token = "testToken";
		context.setAppToken(token);
		Assert.assertEquals(context.getAppToken(), token);
	}

	@Test
	public void testAppDBID() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String ticket = "testDBID";
		context.setAppDBID(ticket);
		Assert.assertEquals(context.getAppDBID(), ticket);
	}

	@Test
	public void testRealmID() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String realmId = "testID";
		context.setRealmID(realmId);
		Assert.assertEquals(context.getRealmID(), realmId);
	}

	@Test
	public void testTrackingID() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		UUID trackingID = UUID.randomUUID();
		context.setTrackingID(trackingID);
		Assert.assertEquals(context.getTrackingID(), trackingID);
	}

	@Test
	public void testRequestID() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String requestID = "testRequestID";
		context.setRequestID(requestID);
		Assert.assertEquals(context.getRequestID(), requestID);
	}

	@Test
	public void testRequestIDNull() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		String requestID = null;
		context.setRequestID(requestID);
		Assert.assertNotNull(context.getRequestID());
	}

	@Test
	public void testIntuitServiceType() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		ServiceType serviceType = ServiceType.IPS;
		context.setIntuitServiceType(serviceType);
		Assert.assertEquals(context.getIntuitServiceType(), serviceType);
	}

	@Test
	public void testAuthorizer() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret() + "1", ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		context.setAuthorizer(oauth);
		Assert.assertEquals(context.getAuthorizer(), oauth);
	}

	@Test
	public void testInvalidate() {
		Context context = getContextForTest();
		Assert.assertNotNull(context);
		context.invalidate();
		Assert.assertNull(context.getAppToken());
		Assert.assertNull(context.getAppDBID());
		Assert.assertNull(context.getAuthorizer());
		Assert.assertNull(context.getIntuitServiceType());
		Assert.assertNull(context.getRealmID());
	}
}
