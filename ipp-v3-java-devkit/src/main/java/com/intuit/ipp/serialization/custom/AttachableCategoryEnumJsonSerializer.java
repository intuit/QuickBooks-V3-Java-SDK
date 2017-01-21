package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.AttachableCategoryEnum;

/**
 * Custom JsonSerializer for reading AttachableCategoryEnum values
 * 
 */
public class AttachableCategoryEnumJsonSerializer extends JsonSerializer<AttachableCategoryEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(AttachableCategoryEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
