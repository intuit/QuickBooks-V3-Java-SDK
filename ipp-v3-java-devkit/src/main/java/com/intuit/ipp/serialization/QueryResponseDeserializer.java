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
import com.intuit.ipp.data.*;
import com.intuit.ipp.util.Logger;
import jakarta.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.Iterator;

/**
 * Custom deserializer class to handle QueryResponse while unmarshall
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
     * variable RECURRINGTXN
     */
    private static final String RECURRINGTXN = "RecurringTransaction";

    /**
     * variable objFactory
     */
    private ObjectFactory objFactory = new ObjectFactory();
    private static final ObjectMapper deserializeMapper = getDeserializeMapper();
    private static final ObjectMapper customDeserializeMapper = getCustomDeserializeMapper();

    @SuppressWarnings("deprecation")
    @Override
    public QueryResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
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
                qr.setFault(deserializeMapper.treeToValue(jn.get(FAULT), Fault.class));
                continue;
            } else if (key.equals(STARTPOSITION)) {
                qr.setStartPosition(jn.get(STARTPOSITION).intValue());
            } else if (key.equals(MAXRESULTS)) {
                qr.setMaxResults(jn.get(MAXRESULTS).intValue());
            } else if (key.equals(TOTALCOUNT)) {
                qr.setTotalCount(jn.get(TOTALCOUNT).intValue());
            } else if (key.equals(RECURRINGTXN)) {
                if (JsonResourceTypeLocator.lookupType(key) != null) {
                    JsonNode jn1 = jn.get(key);
                    if (jn1.isArray()) {
                        Iterator<JsonNode> iteJson = jn1.iterator();

                        // read the recurring transactions array
                        while (iteJson.hasNext()) {

                            RecurringTransaction rt = new RecurringTransaction();
                            JsonNode jn2 = iteJson.next();
                            Iterator<JsonNode> iteJson2 = jn2.iterator();

                            // read the underlying IntuitObject transaction
                            while (iteJson2.hasNext()) {
                                Iterator<String> s = jn2.fieldNames();
                                String rtKey = s.next();
                                LOG.debug("RecurringTransaction : " + rtKey);
                                JsonNode jn3 = iteJson2.next();
                                //Force the data to be casted to its type
                                Object intuitType = customDeserializeMapper.treeToValue(jn3, JsonResourceTypeLocator.lookupType(rtKey));
                                //Double check
                                if (intuitType instanceof IntuitEntity) {
                                    intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) intuitType);
                                    JAXBElement<? extends IntuitEntity> intuitObject = objFactory.createIntuitObject((IntuitEntity) intuitType);
                                    rt.setIntuitObject(intuitObject);
                                }
                            }

                            // set the query response object to be the recurring transaction
                            Object intuitType = rt;
                            //Double check
                            if (intuitType instanceof IntuitEntity) {
                                intuitResponseDeserializerHelper.updateBigDecimalScale((IntuitEntity) intuitType);
                                JAXBElement<? extends IntuitEntity> intuitObject = objFactory.createIntuitObject((IntuitEntity) intuitType);
                                qr.getIntuitObject().add(intuitObject);
                            }
                        }
                    }
                }
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
                            //Force the data to be casted to its type
                            Object intuitType = customDeserializeMapper.treeToValue(jn2, JsonResourceTypeLocator.lookupType(key));
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
     * CustomDeserialize mapper for {@link #deserialize(JsonParser, DeserializationContext)}}
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getCustomDeserializeMapper() {
        ObjectMapper mapper = deserializeMapper.copy();
        SimpleModule simpleModule = new SimpleModule("CustomFieldDefinition", new Version(1, 0, 0, null));
        simpleModule.addDeserializer(CustomFieldDefinition.class, new CustomFieldDefinitionDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }
}
