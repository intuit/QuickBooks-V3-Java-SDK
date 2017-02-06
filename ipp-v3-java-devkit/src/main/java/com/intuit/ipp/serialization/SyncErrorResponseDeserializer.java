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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.intuit.ipp.data.SyncError;
import com.intuit.ipp.data.SyncErrorResponse;

public class SyncErrorResponseDeserializer extends JsonDeserializer<SyncErrorResponse> {
	

	
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
	 * variable latestUploadTime
	 */
	private static final String LATESTUPLOADTIME = "latestUploadTime";
	
	
	@SuppressWarnings("deprecation")
	@Override
	public SyncErrorResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the SyncErrorResponse to be returned
		SyncErrorResponse sr = new SyncErrorResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equals(STARTPOSITION)) {
				sr.setStartPosition(jn.get(STARTPOSITION).getIntValue());
			} else if (key.equals(MAXRESULTS)) {
				sr.setMaxResults(jn.get(MAXRESULTS).getIntValue());
			} else if (key.equals(TOTALCOUNT)) {
				sr.setTotalCount(jn.get(TOTALCOUNT).getIntValue());
			} else if (key.equals(LATESTUPLOADTIME)) {
				try {
				sr.setLatestUploadTime(date.parse(jn.get(LATESTUPLOADTIME).getTextValue()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
				//Check if the SyncError is in the resource locator 
				if (JsonResourceTypeLocator.lookupType(key) != null) {
					
					JsonNode jn1 = jn.get(key);
					List<SyncError> syncErrorlist = new ArrayList<SyncError>();

					
					if (jn1.isArray()) {
						Iterator<JsonNode> iteJson = jn1.iterator();
						while (iteJson.hasNext()) {
							JsonNode jn2 = iteJson.next();
							SyncError serror = getSyncError(jn2);
							syncErrorlist.add(serror);
						}
					}
						sr.setSyncError(syncErrorlist);
				}
			}
		}
		return sr;
	}
						

	/**
	 * Method to deserialize the SyncError object
	 * 
	 * @param jsonNode
	 * @return QueryResponse
	 */
	private SyncError getSyncError(JsonNode jsonNode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("SyncErrorDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(SyncError.class, new SyncErrorDeserializer());

		mapper.registerModule(simpleModule);

		return mapper.readValue(jsonNode, SyncError.class);
	}

}
