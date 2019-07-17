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

import java.util.Iterator;
import java.util.List;

import com.intuit.payment.data.Errors;

/**
 * Base Exception class to handle Exceptions thrown from SDK
 * 
 * @author dderose
 * 
 */
public class BaseException extends Exception {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private Errors errors = null;

	/**
	 * variable throwable
	 */
	private Throwable throwable;
	
	public BaseException(Errors errors) {
		super(getString(errors.getErrorList()));
		this.errors = errors;
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param errorMessage the error message
	 */
	public BaseException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param throwable the throwable
	 */
	public BaseException(Throwable throwable) {
		super(throwable);
		this.throwable = throwable;
	}

	/**
	 * Constructor FMSException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public BaseException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		this.throwable = throwable;
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
	 * @return
	 */
	public Errors getErrors() {
		return errors;
	}

    /**
	 * Method to get the error codes received from server as String message
	 * 
	 * @param errorList
	 * @return
	 */
	protected static String getString(List<com.intuit.payment.data.Error> errorList) {
		String exceptionDetails = "";
		if (errorList != null) {
			Iterator<com.intuit.payment.data.Error> iter = errorList.iterator();
			while (iter.hasNext()) {
				com.intuit.payment.data.Error error = iter.next();
				StringBuilder sb = new StringBuilder();
				sb.append("ERROR CODE:").append(error.getCode())
				.append(", ERROR MESSAGE:").append(error.getMessage())
				.append(", ERROR DETAIL:").append(error.getDetail())
				.append(", ERROR INFO LINK:").append(error.getInfoLink())
				.append(", ERROR MORE INFO:").append(error.getMoreInfo())
				.append(", ERROR TYPE:").append(error.getType());
				
				sb.append("\r\n");
				exceptionDetails = exceptionDetails + sb.toString();
			}
		}
		return exceptionDetails;
	}
	


}
