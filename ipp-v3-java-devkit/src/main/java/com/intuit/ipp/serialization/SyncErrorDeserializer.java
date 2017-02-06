package com.intuit.ipp.serialization;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.bind.JAXBElement;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

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
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the SyncError to be returned
		SyncError se = new SyncError();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		
		
		while (ite.hasNext()) {
			String key = ite.next();
			if (key.equals(CLOUDVERSION)) {
			se.setCloudVersion(getSyncObject(jn.get(key)));
			} else if (key.equals(QBVERSION)) {
				se.setQBVersion(getSyncObject(jn.get(key)));
			} else if(key.equals(ERROR)) {
				se.setError(mapper.readValue(jn.get(key),Error.class));
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
		
		JsonNode jn1 =null;
		SyncObject syncObject = new SyncObject();
		
		
		Iterator<String> ite = jsonNode.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();
            if (JsonResourceTypeLocator.lookupType(key) != null) {

                jn1 = jsonNode.get(key);
                LOG.debug("Query response entity Key :" + key);

                try {
                    //Force the data to be casted to its type
                    Object intuitType = mapper.readValue(jn1, JsonResourceTypeLocator.lookupType(key));
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


