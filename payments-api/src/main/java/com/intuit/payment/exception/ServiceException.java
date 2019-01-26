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
package com.intuit.payment.exception;

/**
 * Exception class to handle service specific exceptions
 * 
 * @author dderose
 * 
 */
public class ServiceException extends BaseException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	
	public ServiceException(com.intuit.payment.data.Errors errors) {
		super(errors);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param errorMessage the error message
	 */
	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param throwable the throwable
	 */
	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ServiceException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
