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
package com.intuit.ipp.serialization;

import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.exception.SerializationException;

/**
 * interface used to serialize and deserialize the given objects
 */
public interface IEntitySerializer {

	/**
	 * Method to serialize the given object using the corresponding implementation algorithm
	 * 
	 * @param o
	 * @return String
	 * @throws SerializationException
	 */
	<T> String serialize(T o) throws SerializationException;

	/**
	 * method to deserialize the given data using the corresponding implementation algorithm
	 * 
	 * @param String
	 * @return IntuitResponse
	 * @throws SerializationException
	 */

	Response deserialize(String data, Class<?> cl) throws SerializationException;

	Response deserializeEntitlements(String decompressedData, Class<EntitlementsResponse> cl) throws SerializationException;
}
