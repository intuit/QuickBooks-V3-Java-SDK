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
package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle Internal Service error in HTTP Status code
 * 
 */
public class InternalServiceException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 656005572669015852L;

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param error the list of errors
	 */
	public InternalServiceException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param errorMessage the error message
	 */
	public InternalServiceException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param throwable the throwable
	 */
	public InternalServiceException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public InternalServiceException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
