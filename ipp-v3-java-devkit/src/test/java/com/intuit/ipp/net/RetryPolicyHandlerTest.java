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
package com.intuit.ipp.net;

import com.intuit.ipp.data.Status;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import java.net.ConnectException;

import org.apache.hc.core5.http.message.StatusLine;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.util.Logger;

public class RetryPolicyHandlerTest {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	private int retryCount = 3;;

	private int retryInterval = 30;

	private int increment = 5;

	private int minBackoff = 5;

	private int maxBackoff = 100;

	private int deltaBackoff = 10;

	HttpClient client;

	HttpGet httpget;

	IntuitRetryPolicyHandler handler;

	/**
	 * initialize a new instance of RetryPolicyHandlerTest class
	 */
	public RetryPolicyHandlerTest() {
	}

	/**
	 * initiates before each test method execution. intiates HttpClient and Method.
	 */
	@BeforeClass
	protected void setUp() throws Exception {
		LOG.debug("in setup");
		httpget = new HttpGet("http://localhost:2020");
	}

	/**
	 * logic that deals with fixed retry mechanism
	 * 
	 * @throws Exception
	 */
	@Test(expectedExceptions = ConnectException.class)
	public void testFixedRetry() throws Exception {
		handler = new IntuitRetryPolicyHandler(retryCount, retryInterval);
		client = HttpClientBuilder.create().setRetryStrategy(handler).build();
		String status = client.execute(httpget, response -> new StatusLine(response).toString());
		LOG.debug(status);
	}

	/**
	 * logic that deals with incremenatal retry mechanism
	 * 
	 * @throws Exception
	 */
	@Test(expectedExceptions = ConnectException.class)
	public void testIncrementalRetry() throws Exception {
		LOG.debug("in incremental");
		handler = new IntuitRetryPolicyHandler(retryCount, retryInterval, increment);
		client = HttpClientBuilder.create().setRetryStrategy(handler).build();
		String status = client.execute(httpget, response -> new StatusLine(response).toString());
		LOG.debug(status);
	}

	/**
	 * logic that deals with exponenetial retry mechanism
	 * 
	 * @throws Exception
	 */

	@Test(expectedExceptions = ConnectException.class)
	public void testExponentialRetry() throws Exception {
		LOG.debug("in exponential");
		handler = new IntuitRetryPolicyHandler(retryCount, minBackoff, maxBackoff, deltaBackoff);
		client = HttpClientBuilder.create().setRetryStrategy(handler).build();
		String status = client.execute(httpget, response -> new StatusLine(response).toString());
		LOG.debug(status);
	}

	/**
	 * Excutes after each Test completes
	 */

	@AfterClass
	public void tearDown() {
		LOG.debug("in teardown");
		client = null;
		httpget = null;
	}
}