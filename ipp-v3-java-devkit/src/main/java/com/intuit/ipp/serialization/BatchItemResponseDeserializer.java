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

import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.CustomFieldDefinition;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.util.Logger;

/**
 * Custom deserializer class to handle BatchItemResponse while unmarshall
 *
 */
public class BatchItemResponseDeserializer extends JsonDeserializer<BatchItemResponse> {
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
	 * variable REPORT
	 */
	private static final String REPORT = "Report";
	
	/**
	 * variable BID
	 */
	private static final String BID = "bId";
	
	/**
	 * variable QUERYRESPONSE
	 */
	private static final String QUERYRESPONSE = "QueryResponse";
	
	/**
	 * variable CDC_QUERY_RESPONSE
	 */
	private static final String CDC_QUERY_RESPONSE = "CDCResponse";
	
	/**
	 * variable objFactory
	 */
	private ObjectFactory objFactory = new ObjectFactory();

	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BatchItemResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
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
		BatchItemResponse qr = new BatchItemResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.treeToValue(jn.get(FAULT), Fault.class));
				continue;
			} else if (key.equalsIgnoreCase(REPORT)) {
				qr.setReport(mapper.treeToValue(jn.get(REPORT), Report.class));
			} else if (key.equalsIgnoreCase(BID)) {
				qr.setBId(jn.get(BID).textValue());
			} else if (key.equals(QUERYRESPONSE)) {
				qr.setQueryResponse(getQueryResponse(jn.get(key)));
			} else if (key.equals(CDC_QUERY_RESPONSE)) {
				qr.setCDCResponse(getCDCQueryResponse(jn.get(key)));
			} else {
                // It has to be an IntuitEntity
				String entity = key;
				LOG.debug("entity key : " + key);
				if (JsonResourceTypeLocator.lookupType(entity) != null) {
					// set the CustomFieldDefinition deserializer
					registerModulesForCustomFieldDef(mapper);
					Object intuitType = mapper.treeToValue(jn.get(key), JsonResourceTypeLocator.lookupType(entity));
					if (intuitType instanceof IntuitEntity) {
                        intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) intuitType);
						JAXBElement<? extends IntuitEntity> intuitObject = objFactory
								.createIntuitObject((IntuitEntity) intuitType);
						qr.setIntuitObject(intuitObject);
					}
				}
			}
		}
		return qr;
	}

	/**
	 * Method to deserialize the QueryResponse object
	 * 
	 * @param jsonNode
	 * @return QueryResponse
	 */
	private QueryResponse getQueryResponse(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("QueryResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(QueryResponse.class, new QueryResponseDeserializer());

		mapper.registerModule(simpleModule);

		return mapper.treeToValue(jsonNode, QueryResponse.class);
	}
	
	/**
	 * Method to deserialize the CDCQueryResponse object
	 * 
	 * @param jsonNode
	 * @return CDCResponse
	 */
	private CDCResponse getCDCQueryResponse(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("CDCQueryResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(CDCResponse.class, new CDCQueryResponseDeserializer());

		mapper.registerModule(simpleModule);

		return mapper.treeToValue(jsonNode, CDCResponse.class);
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
