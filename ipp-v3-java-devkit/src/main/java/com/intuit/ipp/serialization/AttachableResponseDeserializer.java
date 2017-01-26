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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

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
	
	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public AttachableResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		AttachableResponse qr = new AttachableResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.readValue(jn.get(FAULT), Fault.class));
			} else if (key.equalsIgnoreCase(ATTACHABLE)) {
				qr.setAttachable(mapper.readValue(jn.get(ATTACHABLE), Attachable.class));
			}
		}
		return qr;
	}

}
