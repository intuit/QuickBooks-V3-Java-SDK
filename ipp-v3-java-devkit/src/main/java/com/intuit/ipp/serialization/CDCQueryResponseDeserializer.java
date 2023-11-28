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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.util.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Custom deserializer class to handle CDCResponse while unmarshall
 */
public class CDCQueryResponseDeserializer extends JsonDeserializer<CDCResponse> {

    /**
     * logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();

    /**
     * variable FAULT
     */
    private static final String FAULT = "Fault";

    /**
     * variable SIZE
     */
    private static final String SIZE = "size";

    /**
     * variable QUERY_RESPONSE
     */
    private static final String QUERY_RESPONSE = "QueryResponse";
    private static final ObjectMapper deserializeMapper = getDeserializeMapper();
    private static final ObjectMapper queryResponseMapper = getQueryResponseMapper();

    @SuppressWarnings("deprecation")
    @Override
    public CDCResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
        //Read the QueryResponse as a tree
        JsonNode jn = jp.readValueAsTree();

        //Create the QueryResponse to be returned
        CDCResponse qr = new CDCResponse();

        //Iterate over the field names
        Iterator<String> ite = jn.fieldNames();

        while (ite.hasNext()) {
            String key = ite.next();

            //Attributes
            if (key.equals(FAULT)) {
                qr.setFault(deserializeMapper.treeToValue(jn.get(FAULT), Fault.class));
                continue;
            } else if (key.equals(SIZE)) {
                qr.setSize(jn.get(SIZE).intValue());
            } else if (key.equals(QUERY_RESPONSE)) {
                JsonNode jn1 = jn.get(key);
                if (jn1.isArray()) {
                    List<QueryResponse> queryResponses = new ArrayList<QueryResponse>();
                    Iterator<JsonNode> iteJson = jn1.iterator();
                    while (iteJson.hasNext()) {
                        JsonNode jn2 = iteJson.next();
                        QueryResponse queryResponse = queryResponseMapper.treeToValue(jn2, QueryResponse.class);
                        queryResponses.add(queryResponse);
                    }
                    qr.setQueryResponse(queryResponses);
                }
            } else {
                // Unknown  key
                LOG.warn("Unknown key for CDCResponse :" + key);
            }
        }

        return qr;
    }

    /**
     * Deserialize mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getDeserializeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //Make the mapper JAXB annotations aware
        AnnotationIntrospector primary = new JakartaXmlBindAnnotationIntrospector(mapper.getTypeFactory());
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
        mapper.setAnnotationIntrospector(pair);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * QueryResponse mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getQueryResponseMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule("QueryResponseDeserializer", new Version(1, 0, 0, null));
        simpleModule.addDeserializer(QueryResponse.class, new QueryResponseDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }
}
