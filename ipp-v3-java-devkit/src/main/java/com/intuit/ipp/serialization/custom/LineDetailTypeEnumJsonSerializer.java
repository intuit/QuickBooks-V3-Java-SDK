package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.LineDetailTypeEnum;

/**
 * Custom JsonSerializer for reading LineDetailTypeEnum values
 * 
 */
public class LineDetailTypeEnumJsonSerializer extends JsonSerializer<LineDetailTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(LineDetailTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
