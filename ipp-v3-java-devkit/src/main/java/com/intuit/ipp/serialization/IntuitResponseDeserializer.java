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
import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.intuit.ipp.data.*;
import com.intuit.ipp.util.Config;
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
import com.fasterxml.jackson.databind.MapperFeature;

import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;

/**
 * Custom deserializer class to handle IntuitResponse while unmarshall
 *
 */
public class IntuitResponseDeserializer extends JsonDeserializer<IntuitResponse> {

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
	 * variable REPORT
	 */
	private static final String HEADER = "Header";
	/**
	 * variable REPORT
	 */
	private static final String ROWS = "Rows";
	/**
	 * variable REPORT
	 */
	private static final String COLUMNS = "Columns";
	
	/**
	 * variable REQUESTID
	 */
	private static final String REQUESTID = "requestId";
	
	/**
	 * variable STATUS
	 */
	private static final String STATUS = "status";
	
	/**
	 * variable TIME
	 */
	private static final String TIME = "time";
	
	/**
	 * variable SYNC_ERROR_RESPONSE
	 */
	private static final String SYNC_ERROR_RESPONSE = "SyncErrorResponse";
	
	/**
	 * variable QUERY_RESPONSE
	 */
	private static final String QUERY_RESPONSE = "QueryResponse";
	
	/**
	 * variable CDC_QUERY_RESPONSE
	 */
	private static final String CDC_QUERY_RESPONSE = "CDCResponse";
	
	/**
	 * variable BATCH_ITEM_RESPONSE
	 */
	private static final String BATCH_ITEM_RESPONSE = "BatchItemResponse";
	
	/**
	 * variable ATTACHABLE_RESPONSE
	 */
	private static final String ATTACHABLE_RESPONSE = "AttachableResponse";
	
	/**
	 * variable objFactory
	 */
	private ObjectFactory objFactory = new ObjectFactory();

	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public IntuitResponse deserialize(JsonParser jp, DeserializationContext desContext) 
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Report report = new Report();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        //Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		IntuitResponse qr = new IntuitResponse();

		List<BatchItemResponse> batchItemResponses = null;
		
		List<AttachableResponse> attachableResponses = null;
		
		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
				qr.setFault(mapper.treeToValue(jn.get(key), Fault.class));
				continue;
			} else if (key.equalsIgnoreCase(REPORT)) {
				qr.setReport(mapper.treeToValue(jn.get(key), Report.class));
			} else if (key.equalsIgnoreCase(HEADER)) {
				ReportHeader header = mapper.treeToValue(jn.get(HEADER), ReportHeader.class);
				report.setHeader(header);
			} else if (key.equalsIgnoreCase(ROWS)) {
				Rows rows= mapper.treeToValue(jn.get(ROWS), Rows.class);
				report.setRows(rows);
			} else if (key.equalsIgnoreCase(COLUMNS)) {
				Columns columns= mapper.treeToValue(jn.get(COLUMNS), Columns.class);
				report.setColumns(columns);
			} else if (key.equalsIgnoreCase(REQUESTID)) {
				qr.setRequestId(jn.get(REQUESTID).textValue());
			} else if (key.equals(TIME)) {
				try {
					qr.setTime(DateUtils.getDateFromString(jn.get(TIME).textValue()));
				} catch (Exception e) {
					//LOG.error("Exception while converting to date", e);
				}
			} else if (key.equals(STATUS)) {
				qr.setStatus(jn.get(STATUS).textValue());
			} else if (key.equals(SYNC_ERROR_RESPONSE)) {
				//qr.setSyncErrorResponse(mapper.readValue(jn.get(SYNC_ERROR_RESPONSE), SyncErrorResponse.class));
                qr.setSyncErrorResponse(getSyncErrorResponse(jn.get(key)));
			} else if (key.equals(QUERY_RESPONSE)) {
                qr.setQueryResponse(getQueryResponse(jn.get(key)));
			} else if (key.equals(CDC_QUERY_RESPONSE)) {
                List<CDCResponse> cdcResponses = new ArrayList<CDCResponse>();
				JsonNode jn1 = jn.get(key);
				if (jn1.isArray()) {
					Iterator<JsonNode> iteJson = jn1.iterator();
					while (iteJson.hasNext()) {
						JsonNode jn2 = iteJson.next();
						cdcResponses.add(getCDCQueryResponse(jn2));
					}
				}
				qr.setCDCResponse(cdcResponses);
			} else if (key.equals(BATCH_ITEM_RESPONSE)) {
				if (JsonResourceTypeLocator.lookupType(key) != null) {
					LOG.debug("processing batch item response");
					JsonNode jn1 = jn.get(key);
					batchItemResponses = new ArrayList<BatchItemResponse>();
					if (jn1.isArray()) {
						Iterator<JsonNode> iteJson = jn1.iterator();
						while (iteJson.hasNext()) {
							JsonNode jn2 = iteJson.next();
							batchItemResponses.add(getBatchItemResponse(jn2));
						}
					}
					qr.setBatchItemResponse(batchItemResponses);
				}
			} else if (key.equals(ATTACHABLE_RESPONSE)) {
				if (JsonResourceTypeLocator.lookupType(key) != null) {
					LOG.debug("processing attachable response");
					JsonNode jn1 = jn.get(key);
					
					attachableResponses = new ArrayList<AttachableResponse>();
					if (jn1.isArray()) {
						Iterator<JsonNode> iteJson = jn1.iterator();
						while (iteJson.hasNext()) {
							JsonNode jn2 = iteJson.next();
							attachableResponses.add(getAttachableResponse(jn2));
						}
					}
					qr.setAttachableResponse(attachableResponses);
				}
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
					} else if ((intuitType instanceof OLBStatus))
					{
						//JAXBElement<? extends OLBStatus> intuitObject = objFactory.createOLBStatus((OLBStatus)intuitType);
						//qr.setIntuitObject(intuitObject);
						qr.setOLBStatus((OLBStatus)intuitType);
					}
					else if ((intuitType instanceof OLBTransaction))
					{
						//JAXBElement<? extends OLBStatus> intuitObject = objFactory.createOLBStatus((OLBStatus)intuitType);
						//qr.setIntuitObject(intuitObject);
						qr.setOLBTransaction((OLBTransaction)intuitType);
					}
				}
			}
		}
		qr.setReport(report);
		return qr;
	}

    /**
     * Updates instances of BigDecimal with new scale in intuitEntity
     * @param intuitType

    private void updateBigDecimalScale(IntuitEntity intuitType) {
        IntuitResponseDeserializer.Feature feature =  new IntuitResponseDeserializer.Feature() {
            private IntuitEntity obj;
            public <T extends IntuitEntity> void set(T object) {
                obj = object;
            }
            public void execute() {
                (new IntuitResponseDeserializer.BigDecimalScaleUpdater()).execute(obj);
            }
        };
        feature.set(intuitType);

        invokeFeature(Config.BIGDECIMAL_SCALE_SHIFT, feature);
    }*/



	/**
	 * Method to deserialize the SyncErrorResponse object
	 * 
	 * @param jsonNode
	 * @return QueryResponse
	 */
    /*
	private SyncErrorResponse getSyncErrorResponse(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("SyncErrorResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(SyncErrorResponse.class, new SyncErrorResponseDeserializer());

		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.readValue(jsonNode, SyncErrorResponse.class);
	}
	*/
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
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.treeToValue(jsonNode, CDCResponse.class);
	}
	
	/**
	 * Method to deserialize the BatchItemResponse object
	 * 
	 * @param jsonNode the json node
	 * @return BatchItemResponse the batch item response
	 */
	private BatchItemResponse getBatchItemResponse(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("BatchItemResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(BatchItemResponse.class, new BatchItemResponseDeserializer());

		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.treeToValue(jsonNode, BatchItemResponse.class);
	}
	
	/**
	 * Method to deserialize the QueryResponse object
	 * 
	 * @param jsonNode
	 * @return QueryResponse
	 */
	private AttachableResponse getAttachableResponse(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("AttachableResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(AttachableResponse.class, new AttachableResponseDeserializer());

		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.treeToValue(jsonNode, AttachableResponse.class);
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
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

    private SyncErrorResponse getSyncErrorResponse(JsonNode jsonNode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule("SyncErrorResponseDeserializer", new Version(1, 0, 0, null));
        simpleModule.addDeserializer(SyncErrorResponse.class, new SyncErrorResponseDeserializer());

        mapper.registerModule(simpleModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.treeToValue(jsonNode, SyncErrorResponse.class);
    }


}
