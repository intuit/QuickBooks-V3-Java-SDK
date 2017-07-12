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
 * Response object to hold the attributes retrieved from UserInfo endpoint 
 * 
 * @author dderose
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "sub",
        "email",
        "emailVerified",
        "givenName",
        "familyName",
        "phoneNumber",
        "phoneNumberVerified",
        "address"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse {
    
    @JsonProperty("sub")
    private String sub;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("emailVerified")
    private boolean emailVerified;
    
    @JsonProperty("givenName")
    private String givenName;
   
    @JsonProperty("familyName")
    private String familyName;
    
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    
    @JsonProperty("phoneNumberVerified")
    private boolean phoneNumberVerified;
    
    @JsonProperty("address")
    private Address address;

    @JsonProperty("sub")
	public String getSub() {
		return sub;
	}

    @JsonProperty("sub")
	public void setSub(String sub) {
		this.sub = sub;
	}

    @JsonProperty("email")
	public String getEmail() {
		return email;
	}

    @JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

    @JsonProperty("emailVerified")
	public boolean isEmailVerified() {
		return emailVerified;
	}

    @JsonProperty("emailVerified")
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

    @JsonProperty("givenName")
	public String getGivenName() {
		return givenName;
	}

    @JsonProperty("givenName")
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

    @JsonProperty("familyName")
	public String getFamilyName() {
		return familyName;
	}

    @JsonProperty("familyName")
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

    @JsonProperty("phoneNumber")
	public String getPhoneNumber() {
		return phoneNumber;
	}

    @JsonProperty("phoneNumber")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    @JsonProperty("phoneNumberVerified")
	public boolean isPhoneNumberVerified() {
		return phoneNumberVerified;
	}

    @JsonProperty("phoneNumberVerified")
	public void setPhoneNumberVerified(boolean phoneNumberVerified) {
		this.phoneNumberVerified = phoneNumberVerified;
	}

	@JsonProperty("address")
	public Address getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(Address address) {
		this.address = address;
	}
    
}