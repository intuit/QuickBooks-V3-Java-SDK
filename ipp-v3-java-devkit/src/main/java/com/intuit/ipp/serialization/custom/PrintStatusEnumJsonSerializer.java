package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PrintStatusEnum;

/**
 * Custom JsonSerializer for reading PrintStatusEnum values
 * 
 */
public class PrintStatusEnumJsonSerializer extends JsonSerializer<PrintStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PrintStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
