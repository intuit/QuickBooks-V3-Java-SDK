/**
 * 
 */
package com.intuit.ipp.serialization;

import com.intuit.ipp.core.Response;
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
}
