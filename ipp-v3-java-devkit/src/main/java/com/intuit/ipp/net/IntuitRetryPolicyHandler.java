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

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.security.sasl.SaslException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpContext;

import com.intuit.ipp.exception.ConfigurationException;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;

/**
 * Class to handle retry policy
 *
 */
public class IntuitRetryPolicyHandler implements HttpRequestRetryHandler {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable NUM_0_8
	 */
	private static final double NUM_0_8 = 0.8;
	
	/**
	 * variable NUM_1_2
	 */
	private static final double NUM_1_2 = 1.2;

	/**
	 * variable retryCount
	 */
	private int retryCount;

	/**
	 * variable retryInterval
	 */
	private int retryInterval;

	/**
	 * variable initialInterval
	 * the initial interval that will be applied for the first time
	 */
	private int initialInterval;

	/**
	 * variable increment
	 * The incremental time value that will be used for calculating the progressive delay between retries.
	 */
	private int increment;

	/**
	 * variable minBackoff
	 * the minimum Backoff time
	 */
	private int minBackoff;

	/**
	 * variable 
	 */
	// the maximum Backoff time
	private int maxBackoff;

	/**
	 * variable deltaBackoff
	 * the maximum DeltaBackOff time
	 */
	private int deltaBackoff;

	/**
	 * variable mechanism
	 * string to represent which type of retry mechanism
	 */
	private String mechanism;

	/**
	 * Initialize a new instance of the RetryPolicyHandler class
	 * 
	 * @param retryCount the retry count
	 * @param retryInterval the retry interval
	 * @throws ConfigurationException the configuration exception
	 */
	public IntuitRetryPolicyHandler(int retryCount, int retryInterval) throws ConfigurationException {
		RetryHelper.argumentNotNegativeValue(retryCount, Config.RETRY_FIXED_COUNT);
		RetryHelper.argumentNotNegativeValue(retryInterval, Config.RETRY_FIXED_INTERVAL);

		this.retryCount = retryCount;
		this.retryInterval = retryInterval;
		mechanism = "fixedretry";
	}

	/**
	 * Initialize a new instance of the RetryPolicyHandler class
	 * 
	 * @param retryCount the retry count
	 * @param initialInterval the initial interval
	 * @param increment the increment
	 * @throws ConfigurationException the configuration exception
	 */
	public IntuitRetryPolicyHandler(int retryCount, int initialInterval, int increment) throws ConfigurationException {
		RetryHelper.argumentNotNegativeValue(retryCount, Config.RETRY_INCREMENTAL_COUNT);
		RetryHelper.argumentNotNegativeValue(initialInterval, Config.RETRY_INCREMENTAL_INTERVAL);
		RetryHelper.argumentNotNegativeValue(increment, Config.RETRY_INCREMENTAL_INCREMENT);

		this.retryCount = retryCount;
		this.initialInterval = initialInterval;
		this.increment = increment;
		mechanism = "incrementalretry";
	}

	/**
	 * Initialize a new instance of the RetryPolicyHandler class
	 * 
	 * @param retryCount the retry cont
	 * @param minBackoff the min backoff
	 * @param maxBackoff the max backoff
	 * @param deltaBackoff the delta backoff
	 * @throws ConfigurationException the configuration exception
	 */
	public IntuitRetryPolicyHandler(int retryCount, int minBackoff, int maxBackoff, int deltaBackoff) throws ConfigurationException {
		RetryHelper.argumentNotNegativeValue(retryCount, Config.RETRY_EXPONENTIAL_COUNT);
		RetryHelper.argumentNotNegativeValue(minBackoff, Config.RETRY_EXPONENTIAL_MIN_BACKOFF);
		RetryHelper.argumentNotNegativeValue(maxBackoff, Config.RETRY_EXPONENTIAL_MAX_BACKOFF);
		RetryHelper.argumentNotNegativeValue(deltaBackoff, Config.RETRY_EXPONENTIAL_DELTA_BACKOFF);

		this.retryCount = retryCount;
		this.minBackoff = minBackoff;
		this.maxBackoff = maxBackoff;
		this.deltaBackoff = deltaBackoff;
		mechanism = "exponentialretry";
	}

	/**
	 * method to validate the retry policies
	 * 
	 * @return boolean
	 */
	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
		LOG.debug("In retry request");
		if (exception == null) {
			throw new IllegalArgumentException("Exception parameter may not be null");
		} else  if (context == null) {
			throw new IllegalArgumentException("HTTP context may not be null");
		}

		if (executionCount > this.retryCount) {
			return checkPolicy(executionCount);
		} else if (exception instanceof NoHttpResponseException) {
			// Retry if the server dropped connection on us
			return checkPolicy(executionCount);
		} else if (exception instanceof InterruptedIOException) {
			// Timeout
			return false;
		} else if (exception instanceof UnknownHostException) {
			// Unknown host
			return false;
		} else if (exception instanceof ConnectException) {
			// Connection refused
			return false;
		} else if (exception instanceof SSLException) {
			// SSL handshake exception
			return false;
		} else if (exception instanceof ProtocolException) {
			// protocol exception
			return false;
		} else if (exception instanceof SaslException) {
			// Sasl exception
			return false;
		}

		HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
		boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
		if (idempotent) {
			// Retry if the request is considered idempotent
			return checkPolicy(executionCount);
		}

		Boolean b = (Boolean) context.getAttribute(HttpCoreContext.HTTP_REQ_SENT);
		boolean sent = (b != null && b.booleanValue());

		// if (!sent || this.requestSentRetryEnabled) {
		// Retry if the request has not been sent fully or
		// if it's OK to retry methods that have been sent
		if (!sent) {
			return checkPolicy(executionCount);
		}
		// otherwise do not retry
		return false;
	}

	/**
	 * Method to check the retry request policy
	 * 
	 * @param executionCount the execution count
	 * @return boolean
	 */
	private boolean checkPolicy(int executionCount) {
		if (mechanism.equalsIgnoreCase("fixedretry")) {
			if (this.retryCount == 0) {
				return false;
			} else if (executionCount < this.retryCount) {
				try {
					Thread.sleep((long) this.retryInterval);
					LOG.debug("The retryInterval " + this.retryInterval);
					return true;
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			return false;
		}

		// functionality for incremental retry policy
		if (mechanism.equalsIgnoreCase("incrementalretry")) {
			if (executionCount < this.retryCount) {
				try {
					Thread.sleep((long) this.initialInterval + (this.increment * executionCount));
					return true;
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			return false;
		}
		// functionality for exponential retry
		if (mechanism.equalsIgnoreCase("exponentialretry")) {
			if (executionCount < this.retryCount) {
				try {
					int delta = (int) ((Math.pow(2.0, executionCount) - 1.0)
							* (this.deltaBackoff * NUM_0_8) + (Math.random()
							* (this.deltaBackoff * NUM_1_2)
							- (this.deltaBackoff * NUM_0_8) + 1));
					int interval = (int) Math.min((this.minBackoff + delta), this.maxBackoff);
					Thread.sleep(interval);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
				return true;
			}
			return false;
		}
		return true;
	}
	
}
