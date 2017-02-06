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
package com.intuit.ipp.services;
import java.util.Map;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.data.TaxService;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.interceptors.IntuitInterceptorProvider;
import com.intuit.ipp.interceptors.IntuitMessage;
import com.intuit.ipp.interceptors.RequestElements;
import com.intuit.ipp.net.MethodType;


public class GlobalTaxService {
	
	
	/**
	 * variable context
	 */
	private transient Context context = null;

/**
	 * Hiding the default constructor as Context is always required to function properly
	 */
	protected GlobalTaxService() {
		
	}
	
	/**
	 * Constructor GlobalTaxService
	 * 
	 * @param context
	 *            the context
	 */
	public GlobalTaxService(final Context context) {
		this.context = context;
	}

	/**
	 * Method to retrieve records for the given list of query
	 * 
	 * @param query
	 *            the query string
	 * @return query result
	 * @throws FMSException
	 *             throws FMSException
	 */
	public TaxService addTaxCode(TaxService entity) throws FMSException {

		IntuitMessage intuitMessage = prepareAddTaxCode(entity);

		// execute interceptors
		new IntuitInterceptorProvider().executeInterceptors(intuitMessage);

		// Iterate the IntuitObjects list in QueryResponse and convert to <T> entity 
		TaxService taxServiceResponse = (TaxService) intuitMessage.getResponseElements().getResponse();
		return taxServiceResponse;
	}
	
	private IntuitMessage prepareAddTaxCode(TaxService entity) throws FMSException {
		
		IntuitMessage intuitMessage = new IntuitMessage();
		RequestElements requestElements = intuitMessage.getRequestElements();

		//set the request params
			Map<String, String> requestParameters = requestElements.getRequestParameters();
			requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
			requestElements.setEntity(entity);
			requestElements.setObjectToSerialize(entity);
			requestElements.setContext(context);
			
		return intuitMessage;
	}
	
	}
	