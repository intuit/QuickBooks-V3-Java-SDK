package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.FaultTypeEnum;

/**
 * Custom JsonSerializer for reading FaultTypeEnum values
 * 
 */
public class FaultTypeEnumJsonSerializer extends JsonSerializer<FaultTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(FaultTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
