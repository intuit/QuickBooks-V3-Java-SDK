/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dderose
 *
 */
@JsonIgnoreProperties({"intuit_tid", "requestId"})
public abstract class Entity implements Serializable, IEntity {

	private static final long serialVersionUID = 1L;

	private String intuit_tid = null;
	private String requestId = null;

	/**
	 * @return
	 */
	public String getIntuit_tid() {
		return intuit_tid;
	}

	/**
	 * @param intuit_tid
	 */
	public void setIntuit_tid(String intuit_tid) {
		this.intuit_tid = intuit_tid;
	}

	/**
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


}

