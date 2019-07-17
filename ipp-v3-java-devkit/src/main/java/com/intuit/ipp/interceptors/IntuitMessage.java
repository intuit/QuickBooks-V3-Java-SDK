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
package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.TaxService;
/**
 * Class to have request and response element objects which are being used in interceptors flow
 * 
 */
public class IntuitMessage {

	/**
	 * variable platformService
	 */
	private boolean platformService = false;
	private boolean entitlementService = false;
	
	/**
	 * variable requestElements
	 */
	private RequestElements requestElements = new RequestElements();

	/**
	 * variable responseElements
	 */
	private ResponseElements responseElements = new ResponseElements();
	private TaxService taxService = new TaxService();

	/**
	 * Gets requestElements
	 * 
	 * @return requestElements
	 */
	public RequestElements getRequestElements() {
		return requestElements;
	}

	/**
	 * Gets responseElements
	 * 
	 * @return responseElements
	 */
	public ResponseElements getResponseElements() {
		return responseElements;
	}

	public boolean isPlatformService() {
		return platformService;
	}

	public void setPlatformService(boolean platformService) {
		this.platformService = platformService;
	}
	
	public boolean isEntitlementService() {
		return entitlementService;
	}

	public void setEntitlementService(boolean entitlementService) {
		this.entitlementService = entitlementService;
	}

	public TaxService getTaxService() {
		return taxService;
	}

}
