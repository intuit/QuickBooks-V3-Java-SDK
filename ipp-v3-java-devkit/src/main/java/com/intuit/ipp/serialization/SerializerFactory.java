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

import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.util.StringUtils;

/**
 * Factory class to get the corresponding serialization class object for the given serialization format.
 * 
 */
public final class SerializerFactory {
	
	/**
	 * variable XML_SERIALIZE_FORMAT
	 */
	public static final String XML_SERIALIZE_FORMAT = "xml";
	
	/**
	 * variable JSON_SERIALIZE_FORMAT
	 */
	public static final String JSON_SERIALIZE_FORMAT = "json";
	
	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private SerializerFactory() {
	}

	/**
	 * Method to get the SerializerFactory instance
	 * 
	 * @return SerializerFactory
	 */
	public static SerializerFactory getInstance() {
		return new SerializerFactory();
	}
	
	/**
	 * Method to get the corresponding serialize instance for the given serializer format
	 * 
	 * @param serializerFormat
	 * @return IEntitySerializer
	 * @throws SerializationException
	 */
	public static IEntitySerializer getSerializer(final String serializeFormat) throws SerializationException {
		IEntitySerializer serializer = null;
		if (isValidSerializeFormat(serializeFormat)) {
			if (serializeFormat.equalsIgnoreCase(XML_SERIALIZE_FORMAT)) {
				serializer = new XMLSerializer();
			} else if (serializeFormat.equalsIgnoreCase(JSON_SERIALIZE_FORMAT)) {
				serializer = new JSONSerializer();
			}
		}
		return serializer;
	}
	
	/**
	 * Method to validate whether the given serialization format is correct
	 * 
	 * @param serializeFormat
	 * @return boolean
	 * @throws SerializationException
	 */
	public static boolean isValidSerializeFormat(final String serializeFormat) throws SerializationException {
		if (!StringUtils.hasText(serializeFormat)) {
			throw new SerializationException("serialization format is either null or empty!");
		} else if (serializeFormat.equalsIgnoreCase(XML_SERIALIZE_FORMAT) || serializeFormat.equalsIgnoreCase(JSON_SERIALIZE_FORMAT)) {
			return true;
		} else {
			throw new SerializationException("Serializer not supported for the given serialization format : " + serializeFormat);
		}
	}
}
