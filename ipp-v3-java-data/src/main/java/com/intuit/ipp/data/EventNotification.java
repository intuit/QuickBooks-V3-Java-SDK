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
package com.intuit.ipp.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNotification {

	String realmId;
	DataChangeEvent dataChangeEvent;
	
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	public DataChangeEvent getDataChangeEvent() {
		return dataChangeEvent;
	}
	public void setDataChangeEvent(DataChangeEvent dataChangeEvent) {
		this.dataChangeEvent = dataChangeEvent;
	}
	
	
}

