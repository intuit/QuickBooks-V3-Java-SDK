/*******************************************************************************
 * Copyright (c) 2018 Intuit
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

import org.json.JSONObject;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.data.OAuthMigrationRequest;
import com.intuit.oauth2.data.OAuthMigrationResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.http.HttpRequestClient;
import com.intuit.oauth2.http.MethodType;
import com.intuit.oauth2.http.Request;
import com.intuit.oauth2.http.Response;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.MapperImpl;
import com.intuit.oauth2.utils.PropertiesConfig;

/**
 * Client class for OAuthMigration API
 * 
 * @author dderose
 *
 */
public class OAuthMigrationClient {
	

    private OAuthMigrationRequest oAuthMigrationRequest;

    private static final Logger logger = LoggerImpl.getInstance();
    private static final ObjectMapper mapper  = MapperImpl.getInstance();
    
    public OAuthMigrationClient(OAuthMigrationRequest request) {
        this.oAuthMigrationRequest = request;
    }
    
    /**
    * Hiding the default constructor as OAuth2PlatformClient is always required to function properly
    */
   protected OAuthMigrationClient() {

   }
	
	/**
	 * Calls the migrate API based on the the request provided and
	 * returns an object with oauth2 tokens
	 * 
	 * @param environment
	 * @return
	 * @throws ConnectionException 
	 */
	public OAuthMigrationResponse migrate() throws ConnectionException {

		logger.debug("Enter OAuthMigrationClient::migrate");
		
		try {
			
			
			HttpRequestClient client = new HttpRequestClient(oAuthMigrationRequest.getOauth2config().getProxyConfig());
			
			//prepare post json
			String requestjson =  new JSONObject().put("scope", getScopeValue(oAuthMigrationRequest.getScope()))
    				.put("redirect_uri", getRedirectUrl() )
    				.put("client_id", oAuthMigrationRequest.getOauth2config().getClientId())
    				.put("client_secret",oAuthMigrationRequest.getOauth2config().getClientSecret())
    				.toString();
			
			Request request = new Request.RequestBuilder(MethodType.GET, getMigrationAPIUrl(oAuthMigrationRequest.getEnvironment()))
											.requiresAuthentication(true)
											.postJson(requestjson)
											.build(); 
			//make the API call
			Response response = client.makeJsonRequest(request, oAuthMigrationRequest);

			logger.debug("Response Code : " + response.getStatusCode());
			if (response.getStatusCode() == 200) {
				ObjectReader reader = mapper.readerFor(OAuthMigrationResponse.class);
				OAuthMigrationResponse oAuthMigrationResponse = reader.readValue(response.getContent());
				return oAuthMigrationResponse;

			} else {
				logger.debug("failed calling migrate API");
				throw new ConnectionException("failed calling migrate API", response.getStatusCode() + "");
			}
		} catch (Exception ex) {
			logger.error("Exception while calling migrate", ex);
			throw new ConnectionException(ex.getMessage(), ex);
		}
	}
	
	
	private static String getMigrationAPIUrl(Environment environment) {
		return PropertiesConfig.getInstance().getProperty("OAUTH_MIGRATION_URL_" + environment.value()); 
	}
	
	public String getScopeValue(Scope scope)  {
		return PropertiesConfig.getInstance().getProperty(scope.value());
	}
	
	public String getRedirectUrl()  {
		String url = PropertiesConfig.getInstance().getProperty("REDIRECT_URL"); 
		return (oAuthMigrationRequest.getRedirectUri() == null || oAuthMigrationRequest.getRedirectUri().isEmpty()) ? url: oAuthMigrationRequest.getRedirectUri();
	}
	

}
