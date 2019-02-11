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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.oauth2.exception.InvalidRequestException;

/**
 * @author dderose
 *
 */
public class OAuth2ConfigTest {
	
	private OAuth2Config oauth2Config;
	private static final String clientId = "clientId"; 
	private static final String clientSecret = "clientSecret"; 
	
	@BeforeClass
	public void setUp() {
		oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(clientId, clientSecret).buildConfig();	
	}
	
	
	@Test
	public void testGenerateCSRFToken() {
		String csrf = oauth2Config.generateCSRFToken();
		assertNotNull(csrf);
	}
	
	@Test
	public void testPrepareUrl() throws InvalidRequestException {
		List<Scope> scopes = new ArrayList<Scope>();
		scopes.add(Scope.OpenId);
		String url = oauth2Config.prepareUrl(scopes, "https://4f4390eb.ngrok.io/oauth2redirect", oauth2Config.generateCSRFToken());
		assertNotNull(url);
		
		Map<String, String> params = prepareQueryParamMap(url);
		assertNotNull(params.containsKey("scope"));
		assertEquals("openid", params.get("scope"));
		
		//
		scopes.add(Scope.Email);
		scopes.add(Scope.Phone);
		url = oauth2Config.prepareUrl(scopes, "https://4f4390eb.ngrok.io/oauth2redirect");
		assertNotNull(url);
		params = prepareQueryParamMap(url);
		assertNotNull(params.containsKey("scope"));
		assertEquals("openid+email+phone", params.get("scope"));
	}
	
	@Test(expectedExceptions = InvalidRequestException.class)
	public void testPrepareUrlException() throws InvalidRequestException {
		List<Scope> scopes = new ArrayList<Scope>();
		scopes.add(Scope.OpenId);
		oauth2Config.prepareUrl(null, "https://4f4390eb.ngrok.io/oauth2redirect", oauth2Config.generateCSRFToken());
	}


	private Map<String, String> prepareQueryParamMap(String url) {
		Map<String, String> params = new HashMap<String, String>();
		String[] strParams = url.split("&");
		for (String param : strParams)  
		{  
		    String name = param.split("=")[0];  
		    String value = param.split("=")[1];  
		    params.put(name, value);  
		}
		return params;
	}
	
	@Test
	public void testGetDefaultScope() {
	
		String scope = oauth2Config.getScopeValue(Scope.Accounting);
		assertEquals("com.intuit.quickbooks.accounting", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Payments);
		assertEquals("com.intuit.quickbooks.payment", scope);
		
		scope = oauth2Config.getScopeValue(Scope.AccountingPayments);
		assertEquals("com.intuit.quickbooks.accounting com.intuit.quickbooks.payment", scope);
		
		scope = oauth2Config.getScopeValue(Scope.All);
		assertEquals("openid profile email phone address com.intuit.quickbooks.accounting com.intuit.quickbooks.payment", scope);
		
		scope = oauth2Config.getScopeValue(Scope.OpenIdAll);
		assertEquals("openid profile phone address email", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Email);
		assertEquals("email", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Address);
		assertEquals("address", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Phone);
		assertEquals("phone", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Profile);
		assertEquals("profile", scope);
		
		scope = oauth2Config.getScopeValue(Scope.OpenId);
		assertEquals("openid", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Payroll);
		assertEquals("com.intuit.quickbooks.payroll", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Timetracking);
		assertEquals("com.intuit.quickbooks.payroll.timetracking", scope);
		
		scope = oauth2Config.getScopeValue(Scope.Benefits);
		assertEquals("com.intuit.quickbooks.payroll.benefits", scope);

		scope = oauth2Config.getScopeValue(Scope.IntuitName);
		assertEquals("intuit_name", scope);
		
		
	}

}
