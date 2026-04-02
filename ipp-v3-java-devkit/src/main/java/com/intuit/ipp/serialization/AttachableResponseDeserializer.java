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

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;

import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.Fault;

/**
 * Custom deserializer class to handle AttachableResponse while unmarshall
 *
 */
public class AttachableResponseDeserializer extends JsonDeserializer<AttachableResponse> {

	/**
	 * variable FAULT
	 */
	private static final String FAULT = "Fault";
	
	/**
	 * variable ATTACHABLE
	 */
	private static final String ATTACHABLE = "Attachable";

	/** Shared ObjectMapper instance (thread-safe, reuse for performance) */
	@SuppressWarnings("deprecation")
	private static final ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		mapper.setAnnotationIntrospector(new AnnotationIntrospectorPair(primary, secondary));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public AttachableResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		AttachableResponse qr = new AttachableResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.treeToValue(jn.get(FAULT), Fault.class));
			} else if (key.equalsIgnoreCase(ATTACHABLE)) {
				qr.setAttachable(mapper.treeToValue(jn.get(ATTACHABLE), Attachable.class));
			}
		}
		return qr;
	}

}
