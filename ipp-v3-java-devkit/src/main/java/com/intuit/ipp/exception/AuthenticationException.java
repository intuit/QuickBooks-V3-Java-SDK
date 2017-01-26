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
 * Exception class to handle data authentication specific exceptions
 * 
 */
public class AuthenticationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 8955466107559861519L;

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param error the list of errors
	 */
	public AuthenticationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param errorMessage the error message
	 */
	public AuthenticationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param throwable the throwable
	 */
	public AuthenticationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AuthenticationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
