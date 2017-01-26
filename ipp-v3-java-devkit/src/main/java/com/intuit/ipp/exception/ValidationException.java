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
 * Exception class to handle validation specific exceptions
 * 
 */
public class ValidationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2852044978205674856L;

	/**
	 * Constructor ValidationException
	 * 
	 * @param error the error message
	 */
	public ValidationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param errorMessage the error message
	 */
	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param throwable the throwable
	 */
	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ValidationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
