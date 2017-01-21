package com.intuit.ipp.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.intuit.ipp.data.CustomField;
import com.intuit.ipp.data.CustomFieldDefinition;
import com.intuit.ipp.util.Logger;

/**
 * Custom deserializer class to handle CustomFieldDefinition while unmarshall
 *
 */
public class CustomFieldDefinitionDeserializer extends JsonDeserializer<CustomFieldDefinition> {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * variable FAULT
	 */
	private static final String CUSTOM_FIELD = "CustomField";
	
	/**
	 * variable FAULT
	 */
	private static final String TYPE = "Type";
	
	@SuppressWarnings("deprecation")
	@Override
	public CustomFieldDefinition deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//Read the CustomFieldDefinition as a tree
		JsonNode jn = jp.readValueAsTree();

		//Create the CustomFieldDefinition to be returned
		CustomFieldDefinition qr = null; 

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();

		while (ite.hasNext()) {
			String key = ite.next();
			// look for CustomField element
			if (key.equals(CUSTOM_FIELD)) {
				JsonNode jn1 = jn.get(key);
				// get the corresponding type
				qr = getCustomFieldDefinitionType(jn1);
				LOG.debug("The CustomFieldDefinition implementation type " + qr);
				if (qr != null) {
					List<CustomField> customFields = new ArrayList<CustomField>();
					Iterator<JsonNode> iteJson = jn1.iterator();
					while (iteJson.hasNext()) {
						JsonNode jn2 = iteJson.next();
						customFields.add(mapper.readValue(jn2, CustomField.class));
					}
					
					qr.setCustomField(customFields);
					return qr;
				}
			}
		}

		return null;
	}

	/**
	 * Method to get the CustomFieldDefinition implementation type object
	 * 
	 * @param jn the Json Node
	 * @return CustomFieldDefinition implementation reference
	 * @throws IOException
	 */
	private CustomFieldDefinition getCustomFieldDefinitionType(JsonNode jn) throws IOException {
		if (jn.isArray()) {
			JsonNode jn1 = jn.get(0);
			String type = jn1.get(TYPE).getTextValue();
			try {
				return (CustomFieldDefinition) Class.forName("com.intuit.ipp.data." + type + "CustomFieldDefinition").newInstance();
			} catch (Exception e) {
				throw new IOException("Exception while deserializing CustomFieldDefinition", e);
			}
		}
		
		return null;
	}
}
