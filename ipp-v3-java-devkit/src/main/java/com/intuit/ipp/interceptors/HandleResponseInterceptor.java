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

import org.apache.http.StatusLine;

import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.TaxService;
import com.intuit.ipp.exception.AuthenticationException;
import com.intuit.ipp.exception.AuthorizationException;
import com.intuit.ipp.exception.BadRequestException;
import com.intuit.ipp.exception.InternalServiceException;
import com.intuit.ipp.exception.InvalidRequestException;
import com.intuit.ipp.exception.InvalidTokenException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.ServiceException;
import com.intuit.ipp.exception.ServiceUnavailableException;
import com.intuit.ipp.exception.ValidationException;
import com.intuit.ipp.util.Logger;

/**
 * Interceptor to handle Http response. 
 * Validates the response code and throws the corresponding exception for the fault codes.
 *
 */
public class HandleResponseInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable HTTP_ERROR_400
	 */
	private static final int HTTP_ERROR_400 = 400;
	
	/**
	 * variable HTTP_ERROR_401
	 */
	private static final int HTTP_ERROR_401 = 401;
	
	/**
	 * variable HTTP_ERROR_403
	 */
	private static final int HTTP_ERROR_403 = 403;
	
	/**
	 * variable HTTP_ERROR_404
	 */
	private static final int HTTP_ERROR_404 = 404;
	
	/**
	 * variable HTTP_ERROR_500
	 */
	private static final int HTTP_ERROR_500 = 500;
	
	/**
	 * variable HTTP_ERROR_503
	 */
	private static final int HTTP_ERROR_503 = 503;
    
    /**
     * variable HTTP_ERROR_503
     */
    private static final int HTTP_ERROR_429 = 429;
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {

		LOG.debug("Enter HandleResponseInterceptor...");
		
		IntuitResponse intuitResponse = null;
		ResponseElements responseElements = intuitMessage.getResponseElements();
		if(responseElements.getResponse() instanceof TaxService)
		{
			LOG.debug("Tax Service Response" );
		} else if(responseElements.getResponse() instanceof EntitlementsResponse)
		{
			LOG.debug("Entitlements Response" );
		} else{
		intuitResponse = (IntuitResponse) responseElements.getResponse();
		}

		//if intuitResponse is not null and has fault element
		if (intuitResponse != null && intuitResponse.getFault() != null) {
			Fault fault = intuitResponse.getFault();

			if (fault.getType().equalsIgnoreCase("Validation")) {
				throw new ValidationException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("Service")) {
				throw new ServiceException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("AuthenticationFault")) {
				throw new AuthenticationException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("Authentication")) {
				throw new AuthenticationException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("ApplicationAuthenticationFailed")) {
				throw new AuthenticationException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("Authorization")) {
				throw new AuthorizationException(fault.getError());
			} else if (fault.getType().equalsIgnoreCase("AuthorizationFault")) {
				throw new AuthorizationException(fault.getError());
			} else {
				//not able to recognize the type of exception
				throw new FMSException(fault.getError());
			}
		} else if (intuitResponse == null) {
			//intuitResponse is null means that message received contains error as message 
			// which should be included in exception based on http error codes.
			StatusLine statusLine = responseElements.getStatusLine();
			String responseMessage = statusLine.getReasonPhrase();
			int status = responseElements.getStatusCode();
			
			if (responseMessage == null)
			{
				responseMessage = Integer.toString(status); 
			}
			if (status == HTTP_ERROR_400) {
				throw new BadRequestException(responseMessage);
			} else if (status == HTTP_ERROR_401) {
				throw new InvalidTokenException(responseMessage);
			} else if (status == HTTP_ERROR_403) {
				throw new InvalidRequestException(responseMessage);
			} else if (status == HTTP_ERROR_404) {
				throw new InvalidRequestException(responseMessage);
			} else if (status == HTTP_ERROR_500) {
				throw new InternalServiceException(responseMessage);
			} else if (status == HTTP_ERROR_503) {
				throw new ServiceUnavailableException(responseMessage);
            } else if (status == HTTP_ERROR_429) {
                throw new ServiceException(responseMessage);
            }
            //TODO Do we need to support error code 504 -- gateway timeout?
		}
		
		LOG.debug("Exit HandleResponseInterceptor.");
	}

}
