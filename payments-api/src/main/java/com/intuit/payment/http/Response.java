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
package com.intuit.payment.http;

import com.intuit.payment.data.Errors;

/**
 * Class to hold the http response attributes
 * statusCode - httpstatus code
 * intuit_tid - constains value for intuit_tid paramter from response header
 * content - content returned back in the response
 * responseObject - deserialized object
 * errors - deserialized error object
 * 
 * @author dderose
 *
 */
public class Response {

	private Object responseObject;
	private final int statusCode;
	private final String content;
	private final String intuit_tid;
	private Errors errors = null;

	public Response(final int statusCode, final String content, final String intuit_tid) {
		this.content = content;
		this.statusCode = statusCode;
		this.intuit_tid = intuit_tid;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getContent() {
		return content;
	}

	public String getIntuit_tid() {
		return intuit_tid;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}
}
