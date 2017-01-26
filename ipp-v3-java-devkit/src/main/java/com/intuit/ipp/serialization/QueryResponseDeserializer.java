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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

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
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		QueryResponse qr = new QueryResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equals(FAULT)) {
				qr.setFault(mapper.readValue(jn.get(FAULT), Fault.class));
				continue;
			} else if (key.equals(STARTPOSITION)) {
				qr.setStartPosition(jn.get(STARTPOSITION).getIntValue());
			} else if (key.equals(MAXRESULTS)) {
				qr.setMaxResults(jn.get(MAXRESULTS).getIntValue());
			} else if (key.equals(TOTALCOUNT)) {
				qr.setTotalCount(jn.get(TOTALCOUNT).getIntValue());
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
							Object intuitType = mapper.readValue(jn2, JsonResourceTypeLocator.lookupType(key));
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
