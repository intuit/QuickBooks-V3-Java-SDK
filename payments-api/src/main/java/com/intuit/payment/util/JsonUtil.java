/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.intuit.payment.exception.SerializationException;


/**
 * Class to serialize/deserialze data using jackson library
 * 
 * @author dderose
 *
 */
public class JsonUtil {

	private static final Logger logger = LoggerImpl.getInstance();
	public static ObjectMapper mapper;

	public static final String TIMEZONE_UTC = "UTC";
	public static final String DATETIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	static {
		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		DateFormat dateFormat = new SimpleDateFormat(DATETIMEFORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
		mapper.setDateFormat(dateFormat);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * Serialize object to String
	 * 
	 * @param obj
	 * @return
	 * @throws SerializationException
	 */
	public static String serialize(Object obj) throws SerializationException {
		try {
			if (obj != null) {
				return mapper.writeValueAsString(obj);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("SerializationException {}", e.getMessage());
			throw new SerializationException(e.getMessage());
		}
	}

	/**
	 * Deserialize String to object of TypeReference
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 * @throws SerializationException
	 */
	public static Object deserialize(String json, TypeReference<?> typeReference) throws SerializationException {
		try {
			logger.debug("Json string to deserialize {} ", json);
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			logger.error("SerializationException {}", e.getMessage());
			SerializationException serializationException = new SerializationException(e);
			throw serializationException;
		}
	}

}

