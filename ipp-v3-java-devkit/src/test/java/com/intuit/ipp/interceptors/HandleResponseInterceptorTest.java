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
package com.intuit.ipp.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ipp.services.IPPHelper;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.AuthenticationException;
import com.intuit.ipp.exception.AuthorizationException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.InvalidRequestException;
import com.intuit.ipp.exception.InvalidTokenException;
import com.intuit.ipp.exception.ServiceException;
import com.intuit.ipp.exception.ValidationException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.EntityCreator;


public class HandleResponseInterceptorTest {

	private IntuitMessage intuitMessage = null;
	
	private  Context context = null;
	
	private String actualURL;
	
	private DataService service = null;
	
	@BeforeClass
	public void setup() throws FMSException {
		intuitMessage = new IntuitMessage();
		IntuitResponse intuitResponse = new IntuitResponse();
		intuitResponse.setFault(getFault());
		intuitMessage.getResponseElements().setResponse(intuitResponse);
	
		IPPHelper ippHelper = IPPHelper.getInstance();
		actualURL = Config.getProperty(Config.BASE_URL_QBO);
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		context = new Context(oauth, ServiceType.QBO, ippHelper.getQboRealmID());
		service = new DataService(context);
		EntityCreator.addCustomer(oauth, context);
	}
	
	private Fault getFault() {
		Fault fault = new Fault();
		List<Error> errors = new ArrayList<Error>();
		Error error = new Error();
		error.setCode("100");
		error.setMessage("Exception");
		errors.add(error);
		fault.setError(errors);
		return fault;
	}
	
	@Test  (enabled = false)
	public void testHandleResponseInterceptor_Validation() {
		boolean isValid = false;
		IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
		intuitResponse.getFault().setType("Validation");
		HandleResponseInterceptor interceptor = new HandleResponseInterceptor();
		try {
			interceptor.execute(intuitMessage);
		} catch (FMSException e) {
			if (e instanceof ValidationException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test (enabled = false)
	public void testHandleResponseInterceptor_Service() {
		boolean isValid = false;
		IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
		intuitResponse.getFault().setType("Service");
		HandleResponseInterceptor interceptor = new HandleResponseInterceptor();
		try {
			interceptor.execute(intuitMessage);
		} catch (FMSException e) {
			if (e instanceof ServiceException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test  (enabled = false)
	public void testHandleResponseInterceptor_Authentication() {
		boolean isValid = false;
		IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
		intuitResponse.getFault().setType("AuthenticationFault");
		HandleResponseInterceptor interceptor = new HandleResponseInterceptor();
		try {
			interceptor.execute(intuitMessage);
		} catch (FMSException e) {
			if (e instanceof AuthenticationException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test  (enabled = false)
	public void testHandleResponseInterceptor_Authorization() {
		boolean isValid = false;
		IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
		intuitResponse.getFault().setType("Authorization");
		HandleResponseInterceptor interceptor = new HandleResponseInterceptor();
		try {
			interceptor.execute(intuitMessage);
		} catch (FMSException e) {
			if (e instanceof AuthorizationException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test (enabled = false)
	public void testHandleResponseInterceptor_401() {
		boolean isValid = false;
		
		Customer customerIn = new Customer();
		customerIn.setId("NG:2285964");

		Config.setProperty(Config.BASE_URL_QBO, "https://services.intuit.com/v3/company1");
		try {
			service.findById(customerIn);
		} catch (FMSException e) {
			if (e instanceof InvalidTokenException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test (enabled = false)
	public void testHandleResponseInterceptor_404() {
		boolean isValid = false;
		
		Customer customerIn = new Customer();
		customerIn.setId("NG:12111");

		Config.setProperty(Config.BASE_URL_QBO, "https://services.intuit.com/company");
		try {
			service.findById(customerIn);
		} catch (FMSException e) {
			if (e instanceof InvalidRequestException) {
				isValid = true;
			}
		}
		
		Assert.assertTrue(isValid);
	}
	
	@Test  (enabled = false)
	public void testHandleResponseInterceptor_reset() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());

		Config.setProperty(Config.BASE_URL_QBO, actualURL);
		Customer customerOut =service.findById(customerIn);
		Assert.assertNotNull(customerOut);
	}
	
	@AfterClass
	public void restoreURL(){
		Config.setProperty(Config.BASE_URL_QBO, actualURL);
	}
	
}
