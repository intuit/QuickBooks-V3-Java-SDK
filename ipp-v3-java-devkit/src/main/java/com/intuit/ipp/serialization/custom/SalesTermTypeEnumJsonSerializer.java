package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.SalesTermTypeEnum;

/**
 * Custom JsonSerializer for reading SalesTermTypeEnum values
 * 
 */
public class SalesTermTypeEnumJsonSerializer extends JsonSerializer<SalesTermTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(SalesTermTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
