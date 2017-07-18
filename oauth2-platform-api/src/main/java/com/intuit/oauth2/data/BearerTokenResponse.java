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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Response object to hold the attributes retrieved from BearerToken endpoint and 
 * Refresh token endpoint
 * 
 * @author dderose
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "expires_in",
        "id_token",
        "refresh_token",
        "x_refresh_token_expires_in",
        "access_token",
        "token_type"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class BearerTokenResponse {

    @JsonProperty("expires_in")
    private Long expiresIn;
    
    @JsonProperty("id_token")
    private String idToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("x_refresh_token_expires_in")
    private Long xRefreshTokenExpiresIn;
    
    @JsonProperty("access_token")
    private String accessToken;
   
    @JsonProperty("token_type")
    private String tokenType;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The expiresIn
     */
    @JsonProperty("expires_in")
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     *
     * @param expiresIn
     * The expires_in
     */
    @JsonProperty("expires_in")
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     *
     * @return
     * The idToken
     */
    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    /**
     *
     * @param idToken
     * The id_token
     */
    @JsonProperty("id_token")
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     *
     * @return
     * The refreshToken
     */
    @JsonProperty("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     *
     * @param refreshToken
     * The refresh_token
     */
    @JsonProperty("refresh_token")
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     *
     * @return
     * The xRefreshTokenExpiresIn
     */
    @JsonProperty("x_refresh_token_expires_in")
    public Long getXRefreshTokenExpiresIn() {
        return xRefreshTokenExpiresIn;
    }

    /**
     *
     * @param xRefreshTokenExpiresIn
     * The x_refresh_token_expires_in
     */
    @JsonProperty("x_refresh_token_expires_in")
    public void setXRefreshTokenExpiresIn(Long xRefreshTokenExpiresIn) {
        this.xRefreshTokenExpiresIn = xRefreshTokenExpiresIn;
    }

    /**
     *
     * @return
     * The accessToken
     */
    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The access_token
     */
    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The tokenType
     */
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    /**
     *
     * @param tokenType
     * The token_type
     */
    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}