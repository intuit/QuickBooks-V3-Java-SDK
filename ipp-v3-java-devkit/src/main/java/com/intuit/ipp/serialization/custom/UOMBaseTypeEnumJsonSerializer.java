package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.UOMBaseTypeEnum;

/**
 * Custom JsonSerializer for reading UOMBaseTypeEnum values
 * 
 */
public class UOMBaseTypeEnumJsonSerializer extends JsonSerializer<UOMBaseTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(UOMBaseTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
