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
 * Exception class to handle authorization specific exceptions
 * 
 */
public class AuthorizationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -5055305291301191852L;

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param error the list of errors
	 */
	public AuthorizationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param errorMessage the error message
	 */
	public AuthorizationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param throwable the throwable
	 */
	public AuthorizationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AuthorizationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
	
	/**
	 * Constructor AuthorizationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 * @param intuit_tid the transaction id
	 */
	public AuthorizationException(String errorMessage, Throwable throwable, String intuit_tid) {
		super(errorMessage, throwable, intuit_tid);
	}

}
