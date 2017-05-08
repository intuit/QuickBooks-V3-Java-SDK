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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;

import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.CDCQuery;
import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Custom Json serializer class to handle BatchItemRequest while marshalling
 * 
 */
public class BatchItemRequestSerializer extends JsonSerializer<BatchItemRequest> {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * variable BID
	 */
	private static final String BID = "bId";

	/**
	 * variable QUERY
	 */
	private static final String QUERY = "Query";

	/**
	 * variable REPORT_QUERY
	 */
	private static final String REPORT_QUERY = "ReportQuery";

	/**
	 * variable CDCQuery
	 */
	private static final String CDC_QUERY = "CDCQuery";

	/**
	 * variable OPERATION
	 */
	private static final String OPERATION = "operation";

	/**
	 * variable OPTIONS_DATA
	 */
	private static final String OPTIONS_DATA = "optionsData";

	@Override
	public void serialize(BatchItemRequest batchItemRequest, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeStartObject();

		// for bId
		if (StringUtils.hasText(batchItemRequest.getBId())) {
			jgen.writeFieldName(BID);
			jgen.writeString(batchItemRequest.getBId());
		}

		// for IntuitObject
		if (batchItemRequest.getIntuitObject() != null) {
			jgen.writeFieldName(batchItemRequest.getIntuitObject().getValue().getClass().getSimpleName());
			try {
				jgen.writeNumber(new JSONSerializer().serialize(batchItemRequest.getIntuitObject()));
			} catch (SerializationException e) {
				LOG.error("SerializationException while generating Json for IntuitObject in BatchItemRequestSerializer.", e);
				throw new IOException(e);
			}
		}

		// for CDCQuery
		if (batchItemRequest.getCDCQuery() != null) {
			jgen.writeFieldName(CDC_QUERY);
			try {
				jgen.writeNumber(getCDCQueryJson(batchItemRequest.getCDCQuery()));
			} catch (Exception e) {
				LOG.error("SerializationException while generating Json for CDCQuery in BatchItemRequestSerializer.", e);
				throw new IOException(e);
			}
		}

		// for Query
		if (StringUtils.hasText(batchItemRequest.getQuery())) {
			jgen.writeFieldName(QUERY);
			jgen.writeString(batchItemRequest.getQuery());
		}

		// for ReportQuery
		if (StringUtils.hasText(batchItemRequest.getReportQuery())) {
			jgen.writeFieldName(REPORT_QUERY);
			jgen.writeString(batchItemRequest.getReportQuery());
		}

		// for operation
		if (batchItemRequest.getOperation() != null) {
			jgen.writeFieldName(OPERATION);
			jgen.writeString(batchItemRequest.getOperation().value());
		}

		// for OptionsData
		if (StringUtils.hasText(batchItemRequest.getOptionsData())) {
			jgen.writeFieldName(OPTIONS_DATA);
			jgen.writeString(batchItemRequest.getOptionsData());
		}

		jgen.writeEndObject();
	}

	/**
	 * Method to get the Json string for CDCQuery object
	 * 
	 * @param cdcQuery
	 *            the CDCQuery entity describing need for query
	 * @return the json string
	 * @throws SerializationException
	 *             throws SerializationException
	 */
	private String getCDCQueryJson(CDCQuery cdcQuery) throws SerializationException {
		ObjectMapper mapper = getObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(cdcQuery);
		} catch (Exception e) {
			throw new SerializationException(e);
		}

		return json;
	}

	/**
	 * Method to get the Jackson object mapper
	 * 
	 * @return ObjectMapper the object mapper
	 */
	private ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);

		mapper.setAnnotationIntrospector(pair);
		mapper.setSerializationInclusion(Include.NON_NULL);

		return mapper;
	}

}
