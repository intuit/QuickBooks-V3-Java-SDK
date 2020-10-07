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
package com.intuit.oauth2.exception;

import com.intuit.oauth2.http.Response;

/**
 * Exception class to handle OAuth exceptions
 * 
 * @author dderose
 *
 */
public class OAuthException extends PlatformException {
	
	private static final long serialVersionUID = -1985285089721112172L;

	/**
	 * variable intuit_tid
	 */
	private String intuit_tid;

	public OAuthException(String errorMessage,String errorCode){
		super(errorMessage,errorCode);
		
	}
	public OAuthException(String errorMessage){
		super(errorMessage);
	}
	
	
	public OAuthException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}

	public OAuthException(String errorMessage, Throwable e, String intuit_tid) {
		super(errorMessage, e);
		this.intuit_tid = intuit_tid;
	}

	public OAuthException( String errorMessage,  String statusCode,  String intuit_tid, Response response){
		super(errorMessage, statusCode, intuit_tid, response);
		this.intuit_tid = intuit_tid;
	}

	@Override
	public String getIntuit_tid() {
		return intuit_tid;
	}

	@Override
	public void setIntuit_tid(String intuit_tid) {
		this.intuit_tid = intuit_tid;
	}
}
