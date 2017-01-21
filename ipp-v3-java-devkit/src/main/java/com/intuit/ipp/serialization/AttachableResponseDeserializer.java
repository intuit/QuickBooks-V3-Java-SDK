package com.intuit.ipp.serialization;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.Fault;

/**
 * Custom deserializer class to handle AttachableResponse while unmarshall
 *
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
	
	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public AttachableResponse deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the QueryResponse to be returned
		AttachableResponse qr = new AttachableResponse();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.readValue(jn.get(FAULT), Fault.class));
			} else if (key.equalsIgnoreCase(ATTACHABLE)) {
				qr.setAttachable(mapper.readValue(jn.get(ATTACHABLE), Attachable.class));
			}
		}
		return qr;
	}

}
