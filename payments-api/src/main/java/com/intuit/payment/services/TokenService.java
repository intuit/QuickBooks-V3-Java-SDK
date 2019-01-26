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

import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.LoggerImpl;

/**
 * Provides operations for Token API
 * 
 * @author dderose
 *
 */
public class TokenService extends ServiceBase {
	
	private static final Logger logger = LoggerImpl.getInstance();

	private RequestContext requestContext;

	public TokenService(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Hiding the default constructor
	 */
	protected TokenService() {

	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Method to create BankAccount
	 * 
	 * @param token
	 * @return
	 * @throws BaseException
	 */
	public Token createToken(Token token) throws BaseException {
		
		logger.debug("Enter TokenService::createToken");

		// prepare API url
		String apiUrl = requestContext.getBaseUrl() + "tokens";
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<Token> typeReference = new TypeReference<Token>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).ignoreAuthHeader(true)
				.requestObject(token).typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// retrieve response
		Token tokenResponse = (Token) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, tokenResponse);
		return tokenResponse;
	}

}
