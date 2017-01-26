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

import java.util.Iterator;
import java.util.List;
import com.intuit.ipp.data.Error;

/**
 * Base Exception class to handle Exceptions thrown from SDK
 * 
 */
public class FMSException extends Exception {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -3232762146006749659L;
	
	/**
	 * variable errorList
	 */
	private List<Error> errorList = null;

	/**
	 * variable throwable
	 */
	private Throwable throwable;
	
	/**
	 * Constructor FMSException
	 * 
	 * @param errorList the list of errors
	 */
	public FMSException(List<Error> errorList) {
		super(getString(errorList));
		this.errorList = errorList;
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param errorMessage the error message
	 */
	public FMSException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param throwable the throwable
	 */
	public FMSException(Throwable throwable) {
		super(throwable);
		this.throwable = throwable;
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public FMSException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		this.throwable = throwable;
	}

	/**
	 * Method to get the list of errors received from server.
	 * 
	 * @return error list
	 */
	public List<Error> getErrorList() {
		return this.errorList;
	}

	/**
	 * Method to get the Throwable object
	 * 
	 * @return Throwable
	 */
	public Throwable getThrowable() {
		return this.throwable;
	}
	
	/**
	 * Method to get the error codes received from server as String message
	 * 
	 * @param errorList
	 * @return
	 */
	protected static String getString(List<Error> errorList) {
		String exceptionDetails = "";
		if (errorList != null) {
			Iterator<Error> iter = errorList.iterator();
			while (iter.hasNext()) {
				Error error = iter.next();
				StringBuilder sb = new StringBuilder();
				sb.append("ERROR CODE:").append(error.getCode()).append(", ERROR MESSAGE:").append(error.getMessage()).append(", ERROR DETAIL:")
						.append(error.getDetail());
				/**
				 * this was done as a result of adding Payment APIs; this wasn't
				 * done before. so keep it consistent with V3 SDK (to preserve
				 * the behaviour for V3) the null check is being done before adding it.
				 */
				if (error.getElement() != null) {
					sb.append(", MORE ERROR DETAIL:").append(error.getElement());
				}
				sb.append("\r\n");
				exceptionDetails = exceptionDetails + sb.toString();
			}
		}
		return exceptionDetails;
	}

}
