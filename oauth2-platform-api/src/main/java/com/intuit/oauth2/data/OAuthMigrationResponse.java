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

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
	@Generated("org.jsonschema2pojo")
	@JsonPropertyOrder({
	        "realmId"
	})

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class OAuthMigrationResponse extends BearerTokenResponse{
	    
	    @JsonProperty("realmId")
	    private String realmId;

	    @JsonProperty("realmId")
		public String getRealmId() {
			return realmId;
		}

	    @JsonProperty("realmId")
		public void setRealmId(String realmId) {
			this.realmId = realmId;
		}
	    
}
