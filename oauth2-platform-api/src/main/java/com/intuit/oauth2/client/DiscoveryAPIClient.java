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
package com.intuit.oauth2.client;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.ProxyConfig;
import com.intuit.oauth2.data.DiscoveryAPIResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.http.HttpRequestClient;
import com.intuit.oauth2.http.MethodType;
import com.intuit.oauth2.http.Request;
import com.intuit.oauth2.http.Response;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.MapperImpl;
import com.intuit.oauth2.utils.PropertiesConfig;

/**
 * Client class for Discovery API with methods to invoke discoverAPI and
 * retrieve the endpoint urls 
 * 
 * @author dderose
 *
 */
public class DiscoveryAPIClient {
	
	private ObjectMapper mapper  = MapperImpl.getInstance();
	private static final Logger logger = LoggerImpl.getInstance();
	
	private ProxyConfig proxyConfig;
	
	//use this constructor to apply proxy configs
	public DiscoveryAPIClient(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}
	
	//for non proxy call, use this constructor
	public DiscoveryAPIClient() {
		this.proxyConfig = null;
	}
	
	/**
	 * Calls the Discovery Document API based on the the Environment provided and
	 * returns an object with url’s for all the endpoints
	 * 
	 * @param environment
	 * @return
	 * @throws ConnectionException 
	 */
	public DiscoveryAPIResponse callDiscoveryAPI(Environment environment) throws ConnectionException {

		logger.debug("Enter DiscoveryAPIClient::callDiscoveryAPI");
		
		try {

			HttpRequestClient client = new HttpRequestClient(proxyConfig);
			Request request = new Request.RequestBuilder(MethodType.GET, getDiscoveryAPIHost(environment))
											.requiresAuthentication(false)
											.build(); 
			Response response = client.makeRequest(request);

			logger.debug("Response Code : " + response.getStatusCode());
			if (response.getStatusCode() == 200) {
				ObjectReader reader = mapper.readerFor(DiscoveryAPIResponse.class);
				DiscoveryAPIResponse discoveryAPIResponse = reader.readValue(response.getContent());
				return discoveryAPIResponse;

			} else {
				logger.debug("failed calling discovery document API");
				throw new ConnectionException("failed calling discovery document API", response.getStatusCode() + "");
			}
		} catch (Exception ex) {
			logger.error("Exception while calling discovery document", ex);
			throw new ConnectionException(ex.getMessage(), ex);
		}
	}
	
	private static String getDiscoveryAPIHost(Environment environment) {
		return PropertiesConfig.getInstance().getProperty("DISCOVERY_API_HOST_" + environment.value()); 
	}
	

}
