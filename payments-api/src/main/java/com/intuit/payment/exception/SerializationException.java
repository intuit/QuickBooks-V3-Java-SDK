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
 * Exception class to handle data serialization specific exceptions
 * 
 * @author dderose
 * 
 */
public class SerializationException extends BaseException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor SerializationException
	 * 
	 * @param errorMessage the error message
	 */
	public SerializationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor SerializationException
	 * 
	 * @param throwable the throwable
	 */
	public SerializationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor SerializationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public SerializationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
