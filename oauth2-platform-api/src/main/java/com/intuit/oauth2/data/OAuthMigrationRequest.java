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
package com.intuit.oauth2.data;

import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;

public class OAuthMigrationRequest {
	
	//Environment
	private Environment environment;
	
	//OAuth2 client id, secret
	private OAuth2Config oauth2config;
	
	//OAuth1 consumer data
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessSecret;
	
	//Scope
	private Scope scope;
	
	//Redirect URL
	private String redirectUri;
	
	private OAuthMigrationRequest(OAuthMigrationRequestBuilder builder) {
       
        this.environment = builder.environment;
        this.oauth2config = builder.oauth2config;
        this.consumerKey = builder.consumerKey;
        this.consumerSecret = builder.consumerSecret;
        this.accessToken = builder.accessToken;
        this.accessSecret = builder.accessSecret;
        this.scope = builder.scope;
        this.redirectUri = builder.redirectUri;
        
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public OAuth2Config getOauth2config() {
		return oauth2config;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public Scope getScope() {
		return scope;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public static class OAuthMigrationRequestBuilder {
		
		//Environment
		private Environment environment;
		
		//OAuth2 client id, secret
		private OAuth2Config oauth2config;
		
		//OAuth1 consumer data
		private String consumerKey;
		private String consumerSecret;
		private String accessToken;
		private String accessSecret;
		
		//Scope
		private Scope scope;
		
		//Redirect URL
		private String redirectUri;
		
		public OAuthMigrationRequestBuilder(Environment environment, Scope scope) {
			this.environment = environment;
			this.scope = scope;
		}
		
		public OAuthMigrationRequestBuilder consumerKey(String consumerKey) {		
			this.consumerKey = consumerKey;
			return this;
		}
		
		public OAuthMigrationRequestBuilder consumerSecret(String consumerSecret) {		
			this.consumerSecret = consumerSecret;
			return this;
		}
		
		public OAuthMigrationRequestBuilder accessToken(String accessToken) {		
			this.accessToken = accessToken;
			return this;
		}
		
		public OAuthMigrationRequestBuilder accessSecret(String accessSecret) {		
			this.accessSecret = accessSecret;
			return this;
		}
		
		public OAuthMigrationRequestBuilder oAuth2Config(OAuth2Config oAuth2Config) {		
			this.oauth2config = oAuth2Config;
			return this;
		}
	
		public OAuthMigrationRequestBuilder redirectUri(String redirectUri) {		
			this.redirectUri = redirectUri;
			return this;
		}
		
		public OAuthMigrationRequest build() {
			return new OAuthMigrationRequest(this);
		}
		
	}
	

}
