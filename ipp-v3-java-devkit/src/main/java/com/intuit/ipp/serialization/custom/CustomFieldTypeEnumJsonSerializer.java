package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CustomFieldTypeEnum;

/**
 * Custom JsonSerializer for reading CustomFieldTypeEnum values
 * 
 */
public class CustomFieldTypeEnumJsonSerializer extends JsonSerializer<CustomFieldTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CustomFieldTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
