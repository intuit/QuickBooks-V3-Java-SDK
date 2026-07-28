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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
	 * variable intuit_tid
	 */
	private String intuit_tid;

	/**
	 * variable responseHeaders - the headers received on the response that produced this exception,
	 * keyed case-insensitively
	 */
	private Map<String, String> responseHeaders;

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
	public FMSException(String errorMessage, Throwable throwable, String intuit_tid) {
		super(errorMessage, throwable);
		this.throwable = throwable;
		this.intuit_tid = intuit_tid;
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
	 * Method to get the intuit_tid
	 * @return intuit_tid
	 */
	public String getIntuit_tid() {
		return intuit_tid;
	}

	public void setIntuit_tid(String intuit_tid) {
		this.intuit_tid = intuit_tid;
	}

	/**
	 * Method to get the headers received on the response that produced this exception.
	 *
	 * <p>Keys are case-insensitive. Where a header was repeated, the last value received is
	 * retained. Returns an empty map rather than null when no headers were captured.
	 *
	 * <p>Useful for auth failures, where the {@code WWW-Authenticate} challenge distinguishes
	 * causes that the response body may not:
	 *
	 * <pre>
	 * catch (AuthenticationException e) {
	 *     String challenge = e.getResponseHeader("WWW-Authenticate");
	 *     // Bearer realm="Intuit", error="invalid_token", error_description="Token expired"
	 * }
	 * </pre>
	 *
	 * @return the response headers, never null
	 */
	public Map<String, String> getResponseHeaders() {
		if (responseHeaders == null) {
			return Collections.emptyMap();
		}
		return responseHeaders;
	}

	/**
	 * Method to get a single response header by name, case-insensitively.
	 *
	 * @param name the header name
	 * @return the header value, or null if absent
	 */
	public String getResponseHeader(String name) {
		if (responseHeaders == null || name == null) {
			return null;
		}
		return responseHeaders.get(name);
	}

	/**
	 * Method to set the response headers. Keys are re-indexed case-insensitively.
	 *
	 * @param responseHeaders the response headers
	 */
	public void setResponseHeaders(Map<String, String> responseHeaders) {
		if (responseHeaders == null) {
			this.responseHeaders = null;
			return;
		}
		Map<String, String> headers = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		headers.putAll(responseHeaders);
		this.responseHeaders = headers;
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
