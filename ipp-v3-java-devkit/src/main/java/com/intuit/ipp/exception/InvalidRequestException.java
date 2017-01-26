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
 * Exception class to handle Invalid Request Error in HTTP Status code
 * 
 */
public class InvalidRequestException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2152752316496012401L;

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param error the list of errors
	 */
	public InvalidRequestException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param errorMessage the error message
	 */
	public InvalidRequestException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param throwable the throwable
	 */
	public InvalidRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public InvalidRequestException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
