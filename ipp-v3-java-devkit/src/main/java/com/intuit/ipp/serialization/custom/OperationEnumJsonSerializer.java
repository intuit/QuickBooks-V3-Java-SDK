package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.OperationEnum;

/**
 * Custom JsonSerializer for reading OperationEnum values
 * 
 */
public class OperationEnumJsonSerializer extends JsonSerializer<OperationEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(OperationEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
