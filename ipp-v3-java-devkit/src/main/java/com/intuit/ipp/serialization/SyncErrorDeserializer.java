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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.*;
import com.intuit.ipp.util.Logger;
import jakarta.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.Iterator;

public class SyncErrorDeserializer extends JsonDeserializer<SyncError> {
    /**
     * logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();


    /**
     * variable CLOUDVERSION
     */
    private static final String CLOUDVERSION = "CloudVersion";

    /**
     * variable QBVERSION
     */
    private static final String QBVERSION = "QBVersion";

    /**
     * variable ERROR
     */
    private static final String ERROR = "Error";


    /**
     * variable objFactory
     */
    private ObjectFactory objFactory = new ObjectFactory();
    private static final ObjectMapper deserializeMapper = getDeserializeMapper();

    @SuppressWarnings("deprecation")
    @Override
    public SyncError deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
        //Read the QueryResponse as a tree
        JsonNode jn = jp.readValueAsTree();
        //Create the SyncError to be returned
        SyncError se = new SyncError();
        //Iterate over the field names
        Iterator<String> ite = jn.fieldNames();
        while (ite.hasNext()) {
            String key = ite.next();
            if (key.equals(CLOUDVERSION)) {
                se.setCloudVersion(getSyncObject(jn.get(key)));
            } else if (key.equals(QBVERSION)) {
                se.setQBVersion(getSyncObject(jn.get(key)));
            } else if (key.equals(ERROR)) {
                se.setError(deserializeMapper.treeToValue(jn.get(key), Error.class));
            }
        }

        return se;
    }


    /**
     * Method to Object Intuitobject
     *
     * @param jsonNode
     */
    private SyncObject getSyncObject(JsonNode jsonNode) {
        String name = null;
        JsonNode jn1 = null;
        SyncObject syncObject = new SyncObject();
        Iterator<String> ite = jsonNode.fieldNames();
        while (ite.hasNext()) {
            String key = ite.next();
            if (JsonResourceTypeLocator.lookupType(key) != null) {

                jn1 = jsonNode.get(key);
                LOG.debug("Query response entity Key :" + key);

                try {
                    //Force the data to be casted to its type
                    Object intuitType = deserializeMapper.treeToValue(jn1, JsonResourceTypeLocator.lookupType(key));
                    //Double check
                    if (intuitType instanceof IntuitEntity) {
                        JAXBElement<? extends IntuitEntity> intuitObject = objFactory
                                .createIntuitObject((IntuitEntity) intuitType);
                        syncObject.setIntuitObject(intuitObject);
                    }
                } catch (JsonParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return syncObject;
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
}

