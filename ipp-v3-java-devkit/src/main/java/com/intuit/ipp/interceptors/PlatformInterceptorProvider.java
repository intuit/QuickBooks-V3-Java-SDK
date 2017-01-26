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
package com.intuit.ipp.interceptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.exception.FMSException;

/**
 * Class to provide the provision to add interceptors in the order those have to be executed. 
 * 
 */
public class PlatformInterceptorProvider {



    /**
	 * variable requestInterceptors is used for keeping the request interceptors
	 */

	private List<Interceptor> requestInterceptors = new ArrayList<Interceptor>();

	/**
	 * variable responseInterceptors is used for keeping the response interceptors
	 */
	private List<Interceptor> responseInterceptors = new ArrayList<Interceptor>();

	/**
	 * Constructor IntuitInterceptorProvider
	 */
	public PlatformInterceptorProvider() {

		requestInterceptors.add(new PrepareRequestInterceptor());
		requestInterceptors.add(new HTTPClientConnectionInterceptor());

		responseInterceptors.add(new DecompressionInterceptor());
		responseInterceptors.add(new DeserializeInterceptor());
	}

	/**
	 * Method to execute the interceptors (request and response) which are added
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	public void executeInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		executeRequestInterceptors(intuitMessage);
		executeResponseInterceptors(intuitMessage);
	}

	/**
	 * Method to execute only request interceptors which are added to requestInterceptors list
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	private void executeRequestInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		Iterator<Interceptor> itr = requestInterceptors.iterator();
		while (itr.hasNext()) {
			Interceptor interceptor = itr.next();
			interceptor.execute(intuitMessage);
		}
	}

	/**
	 * Method to execute only response interceptors which are added to the responseInterceptors list
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	private void executeResponseInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		Iterator<Interceptor> itr = responseInterceptors.iterator();
		while (itr.hasNext()) {
			Interceptor interceptor = itr.next();
			interceptor.execute(intuitMessage);
		}
	}

}
