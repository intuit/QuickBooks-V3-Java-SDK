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
 * Exception class to handle data configuration specific exceptions
 * 
 */
public class ConfigurationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8415364890369303956L;

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param errorMessage the error message
	 */
	public ConfigurationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param throwable the throwable
	 */
	public ConfigurationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ConfigurationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
