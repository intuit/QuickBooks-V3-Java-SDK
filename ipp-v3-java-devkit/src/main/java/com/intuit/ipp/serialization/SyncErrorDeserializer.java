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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.SyncError;
import com.intuit.ipp.data.SyncObject;
import com.intuit.ipp.util.Logger;

public class SyncErrorDeserializer extends JsonDeserializer<SyncError> {
	
	ObjectMapper mapper = new ObjectMapper();
	
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

	@SuppressWarnings("deprecation")
	@Override
	public SyncError deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
			} else if(key.equals(ERROR)) {
				se.setError(mapper.treeToValue(jn.get(key),Error.class));
			}
		}

		return se;
	}

	
	/**
	 * Method to Object Intuitobject
	 * 
	 * @param JsonNode
	 */
	private SyncObject getSyncObject(JsonNode jsonNode) {
		
		String name = null;
		JsonNode jn1 =null;
		SyncObject syncObject = new SyncObject();
		
		
		Iterator<String> ite = jsonNode.fieldNames();

		while (ite.hasNext()) {
			String key = ite.next();
            if (JsonResourceTypeLocator.lookupType(key) != null) {

                jn1 = jsonNode.get(key);
                LOG.debug("Query response entity Key :" + key);

                try {
                    //Force the data to be casted to its type
                    Object intuitType = mapper.treeToValue(jn1, JsonResourceTypeLocator.lookupType(key));
                    //Double check
                    if (intuitType instanceof IntuitEntity) {
                        JAXBElement<? extends IntuitEntity> intuitObject = objFactory
                                .createIntuitObject((IntuitEntity) intuitType);
                        syncObject.setIntuitObject(intuitObject);
                    }
                }  catch (JsonParseException e) {
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

}

