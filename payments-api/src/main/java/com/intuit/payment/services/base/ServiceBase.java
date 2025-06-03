/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.services.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.data.Entity;
import com.intuit.payment.data.Error;
import com.intuit.payment.data.Errors;
import com.intuit.payment.exception.AuthorizationException;
import com.intuit.payment.exception.BadRequestException;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.exception.SerializationException;
import com.intuit.payment.exception.ServiceException;
import com.intuit.payment.http.HttpRequestClient;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.util.JsonUtil;
import com.intuit.payment.util.LoggerImpl;

/**
 * Base service class that makes the HTTP call.
 * - Serializes the request
 * - Prepares HttpRequestClient and makes API call
 * - Deserialize the response
 * - Handle success/error scenarios
 * - Returns the response back
 * 
 * @author dderose
 *
 */
public class ServiceBase {

	private static final Logger logger = LoggerImpl.getInstance();

	/**
	 * @param serviceRequest
	 * @return
	 * @throws BaseException
	 */
	public Response sendRequest(Request serviceRequest) throws BaseException {
		
		logger.debug("Enter ServiceBase::sendRequest");

		// serialize the request object to send POST data
		if (serviceRequest.getMethod().equals(MethodType.POST)) {
			String payload = JsonUtil.serialize(serviceRequest.getRequestObject());
			serviceRequest.setPostJson(payload);
			logger.debug("request payload" + payload);
		}

		//prepare httpclient request
		HttpRequestClient client = new HttpRequestClient(serviceRequest.getContext().getProxyConfig());

		//call API
		Response serviceResponse = client.makeRequest(serviceRequest);

		//handle null response
		if (serviceResponse == null) {
			logger.error("No response from Service: it is null");
			throw new BaseException("Unexpected Error , service response object was null ");
		}

		int httpStatusCode = serviceResponse.getStatusCode();
		logger.info("httpStatusCode::" + httpStatusCode);
		
		// handle successful response - 200 to 299
		if (httpStatusCode >= HttpStatus.SC_OK && httpStatusCode <= 299) {
			logger.info("API call succeeded");

			// response may not have an real payload in some cases example void requests
			if (!serviceResponse.getContent().isEmpty() && serviceRequest.getTypeReference() != null) {
				Object object = JsonUtil.deserialize(serviceResponse.getContent(), serviceRequest.getTypeReference());
				serviceResponse.setResponseObject(object);
			}
			return serviceResponse;

		} else {		
			// handle error scenarios - deserialize error object
			Errors errors = null;
			if (StringUtils.isNotBlank(serviceResponse.getContent())) {
				try {
					TypeReference<Errors> typeReference = new TypeReference<Errors>() {
					};
					errors = (Errors) JsonUtil.deserialize(serviceResponse.getContent(), typeReference);
					serviceResponse.setErrors(errors);
				} catch (SerializationException se) {
					logger.debug("unable to deserialize to an error list", se);
				}
			}
			/**
			 * if there is no error list at this time, that means we were
			 * unsuccessful in creating the errorlist from the response. So
			 * create one manually and set the httpstatus code and the payload
			 * back
			 **/
			if (errors == null) {
				errors = new Errors();
				List<Error> errorList = new ArrayList<Error>();
				Error error = new Error();
				error.setCode("HttpStatusCode-" + httpStatusCode);
				error.setDetail("ResponsePayload: " + serviceResponse.getContent());
				errorList.add(error);
				errors.setErrorList(errorList);
				serviceResponse.setErrors(errors);
			}
			
			/**
			 * Prepare exeception classes based on http status codes
			 * 401- AuthorizationException
			 * 400 - 499 excluding 401 - BadRequestException
			 * 500 - 599 - ServiceException
			 * everything else - BaseException
			 **/
			if (httpStatusCode == HttpStatus.SC_UNAUTHORIZED) {
				logger.error("Authorization Error httpStatusCode {}", httpStatusCode);
				AuthorizationException exception = new AuthorizationException(errors);
				throw exception;
			} else if (httpStatusCode >= HttpStatus.SC_BAD_REQUEST && httpStatusCode <= 499) {
				logger.error("Bad Request Exception {}", httpStatusCode);
				BadRequestException badRequestException = new BadRequestException(errors);
				logger.debug("Client Side Error httpStatusCode {}", httpStatusCode);
				throw badRequestException;
			} else if (httpStatusCode >= HttpStatus.SC_INTERNAL_SERVER_ERROR && httpStatusCode <= 599) {
				logger.error("Service exception {}", httpStatusCode);
				ServiceException serviceException = new ServiceException(errors);
				throw serviceException;
			} else { // catch all status code
				logger.error("unexpected exception {}", httpStatusCode);
				BaseException baseException = new BaseException(errors);
				throw baseException;
			}

		}

	}

	/**
	 * Sets additional attributes to the entity
	 * 
	 * @param request
	 * @param res
	 * @param entity
	 */
	public void prepareResponse(Request request, Response response, Entity entity) {
		entity.setIntuit_tid(response.getIntuit_tid());
		entity.setRequestId(request.getContext().getRequestId());
	}

}
