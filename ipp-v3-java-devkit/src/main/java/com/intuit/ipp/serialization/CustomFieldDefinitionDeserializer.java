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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.intuit.ipp.data.CustomField;
import com.intuit.ipp.data.CustomFieldDefinition;
import com.intuit.ipp.util.Logger;

/**
 * Custom deserializer class to handle CustomFieldDefinition while unmarshall
 *
 */
public class CustomFieldDefinitionDeserializer extends JsonDeserializer<CustomFieldDefinition> {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * variable FAULT
	 */
	private static final String CUSTOM_FIELD = "CustomField";
	
	/**
	 * variable FAULT
	 */
	private static final String TYPE = "Type";
	
	@SuppressWarnings("deprecation")
	@Override
	public CustomFieldDefinition deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the CustomFieldDefinition as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the CustomFieldDefinition to be returned
		CustomFieldDefinition qr = null; 

		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();
			// look for CustomField element
			if (key.equals(CUSTOM_FIELD)) {
				JsonNode jn1 = jn.get(key);
				// get the corresponding type
				qr = getCustomFieldDefinitionType(jn1);
				LOG.debug("The CustomFieldDefinition implementation type " + qr);
				if (qr != null) {
					List<CustomField> customFields = new ArrayList<CustomField>();
					Iterator<JsonNode> iteJson = jn1.iterator();
					while (iteJson.hasNext()) {
						JsonNode jn2 = iteJson.next();
						customFields.add(mapper.treeToValue(jn2, CustomField.class));
					}
					
					qr.setCustomField(customFields);
					return qr;
				}
			}
		}

		return null;
	}

	/**
	 * Method to get the CustomFieldDefinition implementation type object
	 * 
	 * @param jn the Json Node
	 * @return CustomFieldDefinition implementation reference
	 * @throws IOException
	 */
	private CustomFieldDefinition getCustomFieldDefinitionType(JsonNode jn) throws IOException {
		if (jn.isArray()) {
			JsonNode jn1 = jn.get(0);
			String type = jn1.get(TYPE).textValue();
			try {
				return (CustomFieldDefinition) Class.forName("com.intuit.ipp.data." + type + "CustomFieldDefinition").newInstance();
			} catch (Exception e) {
				throw new IOException("Exception while deserializing CustomFieldDefinition", e);
			}
		}
		
		return null;
	}
}
