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
/**
 * 
 * @author Aditi
 *
 *This interface will help in adding appropriate authorisation header in the 
 *HTTP request
 */
package com.intuit.ipp.security;

import java.net.HttpURLConnection;
import org.apache.http.client.methods.HttpRequestBase;

import com.intuit.ipp.exception.FMSException;

/**
 * Interface for Authorizer
 *
 */
public interface IAuthorizer {
	/**
	 * This method will authorise an http request using the selected authorisation mechanism
	 * 
	 * @param httpRequest
	 * @throws FMSException
	 */
	void authorize(HttpRequestBase httpRequest) throws FMSException;
	/**
	 * Authorize a http url connection using Signpost
	 * @param httpUrlConnection
	 * @throws FMSException
	 */
	void authorize(HttpURLConnection httpUrlConnection) throws FMSException;
}
