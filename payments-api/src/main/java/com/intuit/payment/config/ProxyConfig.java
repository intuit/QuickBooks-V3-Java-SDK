/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.config;

/**
 * Config class to hold the proxy properties
 * 
 * @author dderose
 *
 */
public class ProxyConfig {

	private String host;
	private String port;
	private String username;
	private String password;

	private ProxyConfig(ProxyConfigBuilder builder) {
		this.host = builder.host;
		this.port = builder.port;
		this.username = builder.username;
		this.password = builder.password;

	}

	/**
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Builder class for ProxyConfig
	 * 
	 * @author dderose
	 *
	 */
	public static class ProxyConfigBuilder {

		private String host;
		private String port;
		private String username;
		private String password;

		public ProxyConfigBuilder(String host, String port) {
			this.host = host;
			this.port = port;
		}

		public ProxyConfigBuilder username(String username) {
			this.username = username;
			return this;
		}

		public ProxyConfigBuilder password(String password) {
			this.password = password;
			return this;
		}

		public ProxyConfig buildConfig() {
			return new ProxyConfig(this);
		}

	}

}
