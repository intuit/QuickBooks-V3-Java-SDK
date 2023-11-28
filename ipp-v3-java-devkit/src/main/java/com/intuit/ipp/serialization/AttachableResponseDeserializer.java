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
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.Fault;
import java.io.IOException;
import java.util.Iterator;

/**
 * Custom deserializer class to handle AttachableResponse while unmarshall
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
    private static final ObjectMapper deserializeMapper = getDeserializeMapper();

    /**
     * {@inheritDoc}}
     */
    @SuppressWarnings("deprecation")
    @Override
    public AttachableResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
        //Read the QueryResponse as a tree
        JsonNode jn = jp.readValueAsTree();

        //Create the QueryResponse to be returned
        AttachableResponse qr = new AttachableResponse();

        //Iterate over the field names
        Iterator<String> ite = jn.fieldNames();

        while (ite.hasNext()) {
            String key = ite.next();

            //Attributes
            if (key.equalsIgnoreCase(FAULT)) {
                qr.setFault(deserializeMapper.treeToValue(jn.get(FAULT), Fault.class));
            } else if (key.equalsIgnoreCase(ATTACHABLE)) {
                qr.setAttachable(deserializeMapper.treeToValue(jn.get(ATTACHABLE), Attachable.class));
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
        AnnotationIntrospector primary = new JakartaXmlBindAnnotationIntrospector();
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
        mapper.setAnnotationIntrospector(pair);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
