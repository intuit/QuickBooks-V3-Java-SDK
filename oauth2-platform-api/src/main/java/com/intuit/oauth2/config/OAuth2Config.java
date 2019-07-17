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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.intuit.oauth2.client.DiscoveryAPIClient;
import com.intuit.oauth2.data.DiscoveryAPIResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.PropertiesConfig;

/**
 * Config class to hold the clientId and clientSecret
 * and the endpoint URLs retrieved from the Discovery document
 * 
 * @author dderose
 *
 */
public class OAuth2Config {

	//client id, secret
	private String clientId;
	private String clientSecret;
	
	//endpoint URLs
	private String intuitIdTokenIssuer;
	private String intuitAuthorizationEndpoint;
	private String intuitBearerTokenEndpoint;
	private String intuitRevokeTokenEndpoint;
	private String intuitJwksURI;
	private String userProfileEndpoint;
	
	//proxy config
	private ProxyConfig proxyConfig;
	
	private static final Logger logger = LoggerImpl.getInstance();
	
	
	private OAuth2Config(OAuth2ConfigBuilder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.intuitIdTokenIssuer = builder.intuitIdTokenIssuer;
        this.intuitAuthorizationEndpoint = builder.intuitAuthorizationEndpoint;
        this.intuitBearerTokenEndpoint = builder.intuitBearerTokenEndpoint;
        this.intuitRevokeTokenEndpoint = builder.intuitRevokeTokenEndpoint;
        this.intuitJwksURI = builder.intuitJwksURI;
        this.userProfileEndpoint = builder.userProfileEndpoint;
        this.proxyConfig = builder.proxyConfig;
	}
	
	
    public String getIntuitIdTokenIssuer() {
        return intuitIdTokenIssuer;
    }

    public String getIntuitAuthorizationEndpoint() {
        return intuitAuthorizationEndpoint;
    }
    
    public String getIntuitBearerTokenEndpoint() {
        return intuitBearerTokenEndpoint;
    }
    
    public String getIntuitRevokeTokenEndpoint() {
		return intuitRevokeTokenEndpoint;
	} 

    public String getIntuitJwksURI() {
        return intuitJwksURI;
    }
    
    public String getUserProfileEndpoint() {
		return userProfileEndpoint;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public ProxyConfig getProxyConfig() {
		return proxyConfig;
	}

	public static class OAuth2ConfigBuilder {
		
		private String clientId;
		private String clientSecret;
		
		private String intuitIdTokenIssuer;
		private String intuitAuthorizationEndpoint;
		private String intuitBearerTokenEndpoint;
		private String intuitRevokeTokenEndpoint;
		private String intuitJwksURI;
		private String userProfileEndpoint;
		
		private ProxyConfig proxyConfig;

		public OAuth2ConfigBuilder(String clientId, String clientSecret) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
		}
		
		public OAuth2ConfigBuilder callDiscoveryAPI(Environment environment) {
		
			try {
				DiscoveryAPIResponse discoveryAPIResponse = new DiscoveryAPIClient(proxyConfig).callDiscoveryAPI(environment);
			
				if (discoveryAPIResponse != null) {
					this.intuitIdTokenIssuer = discoveryAPIResponse.getIssuer();
					this.intuitAuthorizationEndpoint = discoveryAPIResponse.getAuthorizationEndpoint();
					this.intuitBearerTokenEndpoint = discoveryAPIResponse.getTokenEndpoint();
					this.intuitRevokeTokenEndpoint = discoveryAPIResponse.getRevocationEndpoint();
					this.intuitJwksURI = discoveryAPIResponse.getJwksUri();
					this.userProfileEndpoint = discoveryAPIResponse.getUserinfoEndpoint();
				}
			} catch (ConnectionException e) {
				logger.error("Exception while preparing url for redirect ", e);
			}
			return this;
		}
		
		public OAuth2ConfigBuilder proxyConfig(ProxyConfig proxyConfig) {		
			this.proxyConfig = proxyConfig;
			return this;
		}

		public OAuth2Config buildConfig() {
			return new OAuth2Config(this);
		}
		
	}
	
	/**
	 * Returns the scope value based on the Enum supplied
	 * 
	 * @param scope
	 * @return
	 */
	public String getScopeValue(Scope scope)  {
		logger.debug("Enter OAuth2config::getDefaultScope");
		return PropertiesConfig.getInstance().getProperty(scope.value());
	}
	
	/**
	 * Generates CSRF token
	 * 
	 * @return
	 */
	public String generateCSRFToken()  {
		logger.debug("Enter OAuth2config::generateCSRFToken");
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Prepares URL to call the OAuth2 authorization endpoint using Scope, CSRF and redirectURL that is supplied
	 * 
	 * @param scope
	 * @param redirectUri
	 * @param csrfToken
	 * @return
	 * @throws InvalidRequestException
	 */
	public String prepareUrl(List<Scope> scopes, String redirectUri, String csrfToken) throws InvalidRequestException  {
		
		logger.debug("Enter OAuth2config::prepareUrl");
		if(scopes == null || scopes.isEmpty() || redirectUri.isEmpty() || csrfToken.isEmpty()) {
			logger.error("Invalid request for prepareUrl ");
			throw new InvalidRequestException("Invalid request for prepareUrl");
		}
		try {
			return intuitAuthorizationEndpoint 
					+ "?client_id=" + clientId 
					+ "&response_type=code&scope=" + URLEncoder.encode(buildScopeString(scopes), "UTF-8") 
					+ "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") 
					+ "&state=" + csrfToken;
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception while preparing url for redirect ", e);
			throw new InvalidRequestException(e.getMessage(), e);
		}
		
	}
	
	private String buildScopeString(List<Scope> scopes) {
		StringBuilder sb = new StringBuilder();
		for (Scope scope: scopes) {
			sb.append(getScopeValue(scope) + " ");
		}
		return StringUtils.stripEnd(sb.toString(), " ");
	}
	
	/**
	 * Prepares URL to call the OAuth2 authorization endpoint using Scope and redirectURL that is supplied.
	 * A CSRF token is generated and sent in the request.
	 * 
	 * @param scope
	 * @param redirectUri
	 * @return
	 * @throws InvalidRequestException
	 */
	public String prepareUrl(List<Scope> scopes, String redirectUri) throws InvalidRequestException  {
		
		logger.debug("Enter OAuth2config::prepareUrl");
		if(scopes == null || scopes.isEmpty() || redirectUri.isEmpty()) {
			logger.error("Invalid request for prepareUrl ");
			throw new InvalidRequestException("Invalid request for prepareUrl");
		}
		
		try {
			return intuitAuthorizationEndpoint 
					+ "?client_id=" + clientId
					+ "&response_type=code&scope=" + URLEncoder.encode(buildScopeString(scopes), "UTF-8") 
					+ "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") 
					+ "&state=" + generateCSRFToken();
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception while preparing url for redirect ", e);
			throw new InvalidRequestException(e.getMessage(), e);
		}
		
	}
	   
}
