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
 *  Object to hold the Address attributes 
 * 
 * @author dderose
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "streetAddress",
        "locality",
        "region",
        "postalCode",
        "country"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    
    @JsonProperty("streetAddress")
    private String streetAddress;
    
    @JsonProperty("locality")
    private String locality;
    
    @JsonProperty("region")
    private String region;
   
    @JsonProperty("postalCode")
    private String postalCode;
    
    @JsonProperty("country")
    private String country;

    @JsonProperty("streetAddress")
	public String getStreetAddress() {
		return streetAddress;
	}

    @JsonProperty("streetAddress")
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

    @JsonProperty("locality")
	public String getLocality() {
		return locality;
	}

    @JsonProperty("locality")
	public void setLocality(String locality) {
		this.locality = locality;
	}

    @JsonProperty("region")
	public String getRegion() {
		return region;
	}

    @JsonProperty("region")
	public void setRegion(String region) {
		this.region = region;
	}

    @JsonProperty("postalCode")
	public String getPostalCode() {
		return postalCode;
	}

    @JsonProperty("postalCode")
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

    @JsonProperty("country")
	public String getCountry() {
		return country;
	}

    @JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}
 
    

}