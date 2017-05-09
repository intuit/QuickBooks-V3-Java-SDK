/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ia.connection;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ia.exception.ConnectionException;
import com.intuit.ia.utils.IAHelperConfig;

public class IATest {

	@Test
	public void testOAuthIAAPIs() {

		IAPlatformClient clientObj = new IAPlatformClient();
		invokeAPIs(clientObj);
	}

	private void invokeAPIs(IAPlatformClient clientObj) {
		List<String> appMenuList = null;
		User user = null;
		boolean isException = false;
		IAHelperConfig conObj = IAHelperConfig.getInstance();
		String consumerKey = conObj.getProperty("oauth_consumer_key");
		String consumerSecret = conObj.getProperty("oauth_consumer_secret");
		String accessToken = conObj.getProperty("access_token");
		String accessTokenSecret = conObj.getProperty("access_token_secret");

		try {
			appMenuList = clientObj.getAppMenu(consumerKey, consumerSecret,
					accessToken, accessTokenSecret);
			Assert.assertNotNull(appMenuList);
			Assert.assertNotSame(appMenuList.size(), 0);
			System.out.println(appMenuList.toString());
		} catch (Exception e1) {
			isException = true;
		}
		try {
			user = clientObj.getcurrentUser(consumerKey, consumerSecret,
					accessToken, accessTokenSecret);
			Assert.assertNotNull(user.getEmailAddress());
			System.out.println(user.getEmailAddress());
		} catch (Exception e1) {
			isException = true;
		}
		try {

			clientObj.reConnect(consumerKey, consumerSecret, accessToken,
					accessTokenSecret);

		} catch (ConnectionException e) {
			// isException = true;
			System.out.println(e.toString());
		}
		/*try {
			clientObj.disconnect(consumerKey, consumerSecret, accessToken,
					accessTokenSecret);
		} catch (ConnectionException e) {
			isException = true;
			System.out.println(e.toString());
		}
		Assert.assertFalse(isException, "Failed to perform the operation");*/

	}

}
