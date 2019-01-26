/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.config;

import java.util.UUID;

import com.intuit.payment.util.PropertiesConfig;

/**
 * RequestContext holds the key attributes for making an API call
 * accessToken and environment are mandatory parameters
 * requestId is auto generated if not set explicitly
 * baseUrl is derieved automatically based on the environment value
 * 
 *  * @author dderose
 * 
 */
public class RequestContext {

	private String requestId;
	private String accessToken;
	private ProxyConfig proxyConfig;
	private String baseUrl;

	public enum Environment {
		PRODUCTION, SANDBOX
	};

	public RequestContext() {
	}

	private RequestContext(Builder builder) {
		this.requestId = builder.requestId;
		this.accessToken = builder.accessToken;
		this.baseUrl = builder.baseUrl;
		this.proxyConfig = builder.proxyConfig;
	}

	/**
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @return
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @return
	 */
	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	/**
	 * @return
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @param proxyConfig
	 */
	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	/**
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	

	/**
	 * Builder class for RequestContext
	 * 
	 * @author dderose
	 *
	 */
	public static class Builder {

		private String requestId;
		private String accessToken;
		private ProxyConfig proxyConfig;
		private String baseUrl;

		public Builder(String accessToken, Environment environment) {
			this.accessToken = accessToken;
			this.baseUrl = getBaseUrlFromProperties(environment);
			this.requestId = UUID.randomUUID().toString().replace("-", "");
		}

		public Builder requestId(String requestId) {
			this.requestId = requestId;
			return this;
		}

		public Builder proxyConfig(ProxyConfig proxyConfig) {
			this.proxyConfig = proxyConfig;
			return this;
		}

		public RequestContext build() {
			return new RequestContext(this);
		}

	}

	/**
	 * Retrieves base url from payment.properties
	 * 
	 * @param environment
	 * @return
	 */
	private static String getBaseUrlFromProperties(Environment environment) {
		return PropertiesConfig.getInstance().getProperty("PAYMENTS_BASE_URL_" + environment.toString());
	}

}
