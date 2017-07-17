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
package com.intuit.oauth2.data;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Response object to hold the attributes retrieved from Discovery Document API
 * 
 * @author dderose
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "issuer",
        "authorization_endpoint",
        "token_endpoint",
        "userinfo_endpoint",
        "revocation_endpoint",
        "jwks_uri"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryAPIResponse {
    
    @JsonProperty("issuer")
    private String issuer;
    
    @JsonProperty("authorization_endpoint")
    private String authorizationEndpoint;
    
    @JsonProperty("token_endpoint")
    private String tokenEndpoint;
    
    @JsonProperty("userinfo_endpoint")
    private String userinfoEndpoint;
   
    @JsonProperty("revocation_endpoint")
    private String revocationEndpoint;
    
    @JsonProperty("jwks_uri")
    private String jwksUri;

    @JsonProperty("issuer")
	public String getIssuer() {
		return issuer;
	}

	@JsonProperty("issuer")
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@JsonProperty("authorization_endpoint")
	public String getAuthorizationEndpoint() {
		return authorizationEndpoint;
	}
	@JsonProperty("authorization_endpoint")
	public void setAuthorizationEndpoint(String authorizationEndpoint) {
		this.authorizationEndpoint = authorizationEndpoint;
	}

	@JsonProperty("token_endpoint")
	public String getTokenEndpoint() {
		return tokenEndpoint;
	}

	@JsonProperty("token_endpoint")
	public void setTokenEndpoint(String tokenEndpoint) {
		this.tokenEndpoint = tokenEndpoint;
	}

	@JsonProperty("userinfo_endpoint")
	public String getUserinfoEndpoint() {
		return userinfoEndpoint;
	}

	@JsonProperty("userinfo_endpoint")
	public void setUserinfoEndpoint(String userinfoEndpoint) {
		this.userinfoEndpoint = userinfoEndpoint;
	}

	@JsonProperty("revocation_endpoint")
	public String getRevocationEndpoint() {
		return revocationEndpoint;
	}

	@JsonProperty("revocation_endpoint")
	public void setRevocationEndpoint(String revocationEndpoint) {
		this.revocationEndpoint = revocationEndpoint;
	}

	@JsonProperty("jwks_uri")
	public String getJwksUri() {
		return jwksUri;
	}

	@JsonProperty("jwks_uri")
	public void setJwksUri(String jwksUri) {
		this.jwksUri = jwksUri;
	}
   

}