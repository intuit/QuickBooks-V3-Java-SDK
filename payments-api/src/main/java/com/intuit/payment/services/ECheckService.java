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
package com.intuit.payment.services;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.ECheck;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.LoggerImpl;

/**
 * Provides operations for ECheck API
 * 
 * @author dderose
 *
 */
public class ECheckService extends ServiceBase {
	
	private static final Logger logger = LoggerImpl.getInstance();

	private RequestContext requestContext;

	public ECheckService(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Hiding the default constructor
	 */
	protected ECheckService() {

	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Method to create ECheck
	 * 
	 * @param eCheck
	 * @return
	 * @throws BaseException
	 */
	public ECheck create(ECheck eCheck) throws BaseException {

		logger.debug("Enter ECheckService::create");
		
		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "echecks".replaceAll("\\{format\\}", "json");
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<ECheck> typeReference = new TypeReference<ECheck>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(eCheck)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		ECheck eCheckResponse = (ECheck) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, eCheckResponse);
		return eCheckResponse;
	}

	/**
	 * Method to retrieve ECheck
	 * 
	 * @param eCheckId
	 * @return
	 * @throws BaseException
	 */
	public ECheck retrieve(String eCheckId) throws BaseException {

		logger.debug("Enter ECheckService::retrieve");
		
		if (StringUtils.isBlank(eCheckId)) {
			logger.error("IllegalArgumentException {}", eCheckId);
			throw new IllegalArgumentException("eCheckId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "echecks/{id}".replaceAll("\\{format\\}", "json")
				.replaceAll("\\{" + "id" + "\\}", eCheckId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<ECheck> typeReference = new TypeReference<ECheck>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		ECheck eCheckResponse = (ECheck) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, eCheckResponse);
		return eCheckResponse;
	}

	/**
	 * Method to refund or void ECheck
	 * 
	 * @param eCheckId
	 * @param refund
	 * @return
	 * @throws BaseException
	 */
	public Refund refund(String eCheckId, Refund refund) throws BaseException {

		logger.debug("Enter ECheckService::refund");
		
		if (StringUtils.isBlank(eCheckId)) {
			logger.error("IllegalArgumentException {}", eCheckId);
			throw new IllegalArgumentException("eCheckId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "echecks/{id}/refunds".replaceAll("\\{format\\}", "json")
				.replaceAll("\\{" + "id" + "\\}", eCheckId.toString());
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<Refund> typeReference = new TypeReference<Refund>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(refund)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Refund refundResponse = (Refund) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, refundResponse);
		return refundResponse;
	}

	/**
	 * Method to retrieve Refund for ECheck
	 * 
	 * @param eCheckId
	 * @param refundId
	 * @return
	 * @throws BaseException
	 */
	public Refund getRefund(String eCheckId, String refundId) throws BaseException {

		logger.debug("Enter ECheckService::getRefund");
		
		if (StringUtils.isBlank(eCheckId)) {
			logger.error("IllegalArgumentException {}", eCheckId);
			throw new IllegalArgumentException("eCheckId cannot be empty or null");
		}

		if (StringUtils.isBlank(refundId)) {
			logger.error("IllegalArgumentException {}", refundId);
			throw new IllegalArgumentException("refundId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "echecks/{id}/refunds/{refund_id}"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", eCheckId.toString())
				.replaceAll("\\{" + "refund_id" + "\\}", refundId);
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<Refund> typeReference = new TypeReference<Refund>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Refund refundResponse = (Refund) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, refundResponse);
		return refundResponse;
	}

}
