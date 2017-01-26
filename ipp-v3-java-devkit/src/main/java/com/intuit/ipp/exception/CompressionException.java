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

/**
 * Exception class to handle data compression specific exceptions
 * 
 */
public class CompressionException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor CompressionException
	 * 
	 * @param errorMessage the error message
	 */
	public CompressionException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor CompressionException
	 * 
	 * @param throwable the throwable
	 */
	public CompressionException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor CompressionException
	 * 
	 * @param errorMessage
	 *            the error message
	 * @param e
	 *            the throwable object
	 */
	public CompressionException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
