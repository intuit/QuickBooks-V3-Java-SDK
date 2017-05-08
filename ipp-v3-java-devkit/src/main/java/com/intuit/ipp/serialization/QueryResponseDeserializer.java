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

import javax.xml.bind.JAXBElement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.intuit.ipp.data.CustomFieldDefinition;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.util.Logger;

/**
 * Custom deserializer class to handle QueryResponse while unmarshall
 *
 */
public class QueryResponseDeserializer extends JsonDeserializer<QueryResponse> {

    /**
     * IntuitResponseDeserializeHelper instance
     */
    private IntuitResponseDeserializerHelper intuitResponseDeserializerHelper = new IntuitResponseDeserializerHelper();

    /**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * variable FAULT
	 */
	private static final String FAULT = "Fault";
	
	/**
	 * variable STARTPOSITION
	 */
	private static final String STARTPOSITION = "startPosition";
	
	/**
	 * variable MAXRESULTS
	 */
	private static final String MAXRESULTS = "maxResults";
	
	/**
	 * variable TOTALCOUNT
	 */
	private static final String TOTALCOUNT = "totalCount";
	
	/**
	 * variable objFactory
	 */
	private ObjectFactory objFactory = new ObjectFactory();

	@SuppressWarnings("deprecation")
	@Override
	public QueryResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		QueryResponse qr = new QueryResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equals(FAULT)) {
				qr.setFault(mapper.treeToValue(jn.get(FAULT), Fault.class));
				continue;
			} else if (key.equals(STARTPOSITION)) {
				qr.setStartPosition(jn.get(STARTPOSITION).intValue());
			} else if (key.equals(MAXRESULTS)) {
				qr.setMaxResults(jn.get(MAXRESULTS).intValue());
			} else if (key.equals(TOTALCOUNT)) {
				qr.setTotalCount(jn.get(TOTALCOUNT).intValue());
			} else {
				// It has to be an IntuitEntity
				//Check if the entity is in the resource locator 
				if (JsonResourceTypeLocator.lookupType(key) != null) {

					JsonNode jn1 = jn.get(key);
					LOG.debug("Query response entity Key :" + key);

					if (jn1.isArray()) {
						Iterator<JsonNode> iteJson = jn1.iterator();
						while (iteJson.hasNext()) {
							JsonNode jn2 = iteJson.next();
							// set the CustomFieldDefinition deserializer
							registerModulesForCustomFieldDef(mapper);
							//Force the data to be casted to its type
							Object intuitType = mapper.treeToValue(jn2, JsonResourceTypeLocator.lookupType(key));
							//Double check
							if (intuitType instanceof IntuitEntity) {
                                intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) intuitType);
								JAXBElement<? extends IntuitEntity> intuitObject = objFactory
										.createIntuitObject((IntuitEntity) intuitType);
								qr.getIntuitObject().add(intuitObject);
							}
						}
					}
				}
			}
		}

		return qr;
	}

	/**
	 * Method to add custom deserializer for CustomFieldDefinition
	 * 
	 * @param objectMapper the Jackson object mapper
	 */
	private void registerModulesForCustomFieldDef(ObjectMapper objectMapper) {
		SimpleModule simpleModule = new SimpleModule("CustomFieldDefinition", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(CustomFieldDefinition.class, new CustomFieldDefinitionDeserializer());
		objectMapper.registerModule(simpleModule);
	}
}
