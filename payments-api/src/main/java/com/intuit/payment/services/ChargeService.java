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
import com.intuit.payment.data.Capture;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.LoggerImpl;

/**
 * Provides operations for Charge API
 * 
 * @author dderose
 *
 */
public class ChargeService extends ServiceBase {
	
	private static final Logger logger = LoggerImpl.getInstance();

	private RequestContext requestContext;

	public ChargeService(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Hiding the default constructor
	 */
	protected ChargeService() {
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Method to create Charge
	 * 
	 * @param charge
	 * @return
	 * @throws BaseException
	 */
	public Charge create(Charge charge) throws BaseException {

		logger.debug("Enter ChargeService::create");
		
		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "charges".replaceAll("\\{format\\}", "json");
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<Charge> typeReference = new TypeReference<Charge>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(charge)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Charge chargeResponse = (Charge) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, chargeResponse);
		return chargeResponse;
	}

	/**
	 * Method to retrieve Charge
	 * 
	 * @param chargeId
	 * @return
	 * @throws BaseException
	 */
	public Charge retrieve(String chargeId) throws BaseException {

		logger.debug("Enter ChargeService::retrieve");
		
		if (StringUtils.isBlank(chargeId)) {
			logger.error("IllegalArgumentException {}", chargeId);
			throw new IllegalArgumentException("chargeId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "charges/{id}".replaceAll("\\{format\\}", "json")
				.replaceAll("\\{" + "id" + "\\}", chargeId.toString());
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<Charge> typeReference = new TypeReference<Charge>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Charge chargeResponse = (Charge) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, chargeResponse);
		return chargeResponse;
	}

	/**
	 * Method to capture Charge
	 * 
	 * @param chargeId
	 * @param capture
	 * @return
	 * @throws BaseException
	 */
	public Charge capture(String chargeId, Capture capture) throws BaseException {

		logger.debug("Enter ChargeService::capture");
		
		if (StringUtils.isBlank(chargeId)) {
			logger.error("IllegalArgumentException {}", chargeId);
			throw new IllegalArgumentException("chargeId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl()
				+ "charges/{id}/capture".replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", chargeId);
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<Charge> typeReference = new TypeReference<Charge>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(capture)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Charge chargeResponse = (Charge) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, chargeResponse);
		return chargeResponse;
	}

	/**
	 * Method to refund Charge
	 * 
	 * @param chargeId
	 * @param refund
	 * @return
	 * @throws BaseException
	 */
	public Refund refund(String chargeId, Refund refund) throws BaseException {

		logger.debug("Enter ChargeService::refund");
		
		if (StringUtils.isBlank(chargeId)) {
			logger.error("IllegalArgumentException {}", chargeId);
			throw new IllegalArgumentException("chargeId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl()
				+ "charges/{id}/refunds".replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", chargeId);
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
	 * Method to retrieve Refund for Charge
	 * 
	 * @param chargeId
	 * @param refundId
	 * @return
	 * @throws BaseException
	 */
	public Refund getRefund(String chargeId, String refundId) throws BaseException {

		logger.debug("Enter ChargeService::getRefund");
		
		if (StringUtils.isBlank(chargeId)) {
			logger.error("IllegalArgumentException {}", chargeId);
			throw new IllegalArgumentException("chargeId cannot be empty or null");
		}

		if (StringUtils.isBlank(refundId)) {
			logger.error("IllegalArgumentException {}", refundId);
			throw new IllegalArgumentException("refundId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "charges/{id}/refunds/{refund_id}"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", chargeId.toString())
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
	
	/**
	 * Method to void a Charge
	 * 
	 * @param chargeId
	 * @param refund
	 * @return
	 * @throws BaseException
	 */
	public Refund voidTransaction(String chargeRequestId) throws BaseException {

		logger.debug("Enter ChargeService::refund");
		
		if (StringUtils.isBlank(chargeRequestId)) {
			logger.error("IllegalArgumentException {}", chargeRequestId);
			throw new IllegalArgumentException("chargeRequestId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl()
				+ "txn-requests/{id}/void".replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", chargeRequestId);
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<Refund> typeReference = new TypeReference<Refund>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Refund refundResponse = (Refund) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, refundResponse);
		return refundResponse;
	}

}
