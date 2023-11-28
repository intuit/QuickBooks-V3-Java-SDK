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

import jakarta.xml.bind.JAXBElement;

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
//import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
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
	 * variable ATTACHABLE_RESPONSE
	 */
	private static final String RECURRING_TXN_RESPONSE = "RecurringTransaction";
	
	/**
	 * variable objFactory
	 */
	private ObjectFactory objFactory = new ObjectFactory();
	private static final ObjectMapper deserializeMapper = getDeserializeMapper();
	private static final ObjectMapper customDeserializeMapper = getCustomDeserializeMapper();
	private static final ObjectMapper queryResponseMapper = getQueryResponseMapper();
	private static final ObjectMapper cdcQueryResponseMapper = getCDCQueryResponseMapper();
	private static final ObjectMapper attachableResponseMapper = getAttachableResponseMapper();
	private static final ObjectMapper batchItemResponseMapper = getBatchItemResponseMapper();
	private static final ObjectMapper syncErrorResponseMapper = getSyncErrorResponseMapper();

	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public IntuitResponse deserialize(JsonParser jp, DeserializationContext desContext) 
			throws IOException {
		Report report = new Report();
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
				deserializeMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
				qr.setFault(deserializeMapper.treeToValue(jn.get(key), Fault.class));
				continue;
			} else if (key.equalsIgnoreCase(REPORT)) {
				qr.setReport(deserializeMapper.treeToValue(jn.get(key), Report.class));
			} else if (key.equalsIgnoreCase(HEADER)) {
				ReportHeader header = deserializeMapper.treeToValue(jn.get(HEADER), ReportHeader.class);
				report.setHeader(header);
			} else if (key.equalsIgnoreCase(ROWS)) {
				Rows rows= deserializeMapper.treeToValue(jn.get(ROWS), Rows.class);
				report.setRows(rows);
			} else if (key.equalsIgnoreCase(COLUMNS)) {
				Columns columns= deserializeMapper.treeToValue(jn.get(COLUMNS), Columns.class);
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
                qr.setSyncErrorResponse(syncErrorResponseMapper.treeToValue(jn.get(key), SyncErrorResponse.class));
			} else if (key.equals(QUERY_RESPONSE)) {
                qr.setQueryResponse(queryResponseMapper.treeToValue(jn.get(key), QueryResponse.class));
			} else if (key.equals(CDC_QUERY_RESPONSE)) {
                List<CDCResponse> cdcResponses = new ArrayList<CDCResponse>();
				JsonNode jn1 = jn.get(key);
				if (jn1.isArray()) {
					Iterator<JsonNode> iteJson = jn1.iterator();
					while (iteJson.hasNext()) {
						JsonNode jn2 = iteJson.next();
						cdcResponses.add(cdcQueryResponseMapper.treeToValue(jn2, CDCResponse.class));
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
							batchItemResponses.add(batchItemResponseMapper.treeToValue(jn2, BatchItemResponse.class));
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
							attachableResponses.add(attachableResponseMapper.treeToValue(jn2, AttachableResponse.class));
						}
					}
					qr.setAttachableResponse(attachableResponses);
				}
			} else if (key.equals(RECURRING_TXN_RESPONSE)) {
				if (JsonResourceTypeLocator.lookupType(key) != null) {
					LOG.debug("processing recurring transaction response");

					JsonNode rtNode = jn.get(key);
					Object recurringTxn = deserializeMapper.treeToValue(rtNode, JsonResourceTypeLocator.lookupType(key));
					RecurringTransaction rt = new RecurringTransaction();
					rt = (RecurringTransaction) recurringTxn;

					String entityName = rtNode.fieldNames().next();
					LOG.debug("RecurringTransaction : " + entityName);

					//read the underlying txn node
					JsonNode entityNode = rtNode.get(entityName);

					//Force the data to be casted to its type
					Object entity = deserializeMapper.treeToValue(entityNode, JsonResourceTypeLocator.lookupType(entityName));

					//Double check
					if (entity instanceof IntuitEntity) {
						intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) entity);
						JAXBElement<? extends IntuitEntity> intuitObject = objFactory.createIntuitObject((IntuitEntity) entity);
						rt.setIntuitObject(intuitObject);
					}

					Object obj = rt;
					if (obj instanceof IntuitEntity) {
						intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) obj);
						JAXBElement<? extends IntuitEntity> intuitObject = objFactory.createIntuitObject((IntuitEntity) obj);
						qr.setIntuitObject(intuitObject);
					}
				}
			} else {
                // It has to be an IntuitEntity
				String entity = key;
				LOG.debug("entity key : " + key);
				if (JsonResourceTypeLocator.lookupType(entity) != null) {
					Object intuitType = customDeserializeMapper.treeToValue(jn.get(key), JsonResourceTypeLocator.lookupType(entity));
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
	 * QueryResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getQueryResponseMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("QueryResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(QueryResponse.class, new QueryResponseDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * CDCQueryResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getCDCQueryResponseMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("CDCQueryResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(CDCResponse.class, new CDCQueryResponseDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * BatchItemResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getBatchItemResponseMapper(){
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("BatchItemResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(BatchItemResponse.class, new BatchItemResponseDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * AttachableResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getAttachableResponseMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("AttachableResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(AttachableResponse.class, new AttachableResponseDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * SyncErrorResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getSyncErrorResponseMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("SyncErrorResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(SyncErrorResponse.class, new SyncErrorResponseDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * Deserialize mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getDeserializeMapper() {
		ObjectMapper mapper = new ObjectMapper();
		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JakartaXmlBindAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * Custom mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
	 * @return ObjectMapper
	 */
	private static ObjectMapper getCustomDeserializeMapper() {
		ObjectMapper objectMapper = deserializeMapper.copy();
		SimpleModule simpleModule = new SimpleModule("CustomFieldDefinition", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(CustomFieldDefinition.class, new CustomFieldDefinitionDeserializer());
		objectMapper.registerModule(simpleModule);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

}
