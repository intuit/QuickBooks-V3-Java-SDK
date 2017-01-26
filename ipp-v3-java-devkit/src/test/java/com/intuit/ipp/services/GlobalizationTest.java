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

import java.util.Calendar;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;

public class GlobalizationTest {

	private  Context context = null;
	
	private DataService service = null;
	
	private String randomVal = UUID.randomUUID().toString();
	
	@BeforeClass
	public void setup() {
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		try {
			context = new Context(oauth, ippHelper.getQboAppToken(), ServiceType.QBO, ippHelper.getQboRealmID());
		} catch (FMSException e) {
			e.printStackTrace();
		}
		service = new DataService(context);
	}
	
	@Test  (enabled = false)
	public void testAdd() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setDisplayName("表示" + randomVal.substring(0, 5));
		customerIn.setTitle("タイトル" + randomVal.substring(6, 10));
		customerIn.setGivenName("ファミリー" + randomVal.substring(11, 15));
		customerIn.setFamilyName("世界您好" + randomVal.substring(16, 20));
		
		Customer customerOut = service.add(customerIn);

		Assert.assertNotNull(customerOut);
		Assert.assertNotNull(customerOut.getDisplayName());
	}
	
	@Test(enabled = false)
	public void testUpdate() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + randomVal);
		customerIn.setCompanyName("Company Name Test" + randomVal);
		Customer customerAdded = service.add(customerIn);
		
		customerIn.setId(customerAdded.getId());
		customerIn.setSyncToken(customerAdded.getSyncToken());
		customerIn.setSparse(true);
		//customerIn.setDisplayName(new String("表示4528b".getBytes("UTF-8")));
		customerIn.setDisplayName("表示4528b" + Calendar.getInstance().getTimeInMillis());
		
		Customer customerOut = service.update(customerIn);
		//Assert.assertEquals(customerOut.getDisplayName(), customerIn.getDisplayName());
		Assert.assertNotNull(customerOut);
		Assert.assertNotNull(customerOut.getDisplayName());
	}
	
}
