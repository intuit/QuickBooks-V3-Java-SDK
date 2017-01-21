package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.SymbolPositionEnum;

/**
 * Custom JsonSerializer for reading SymbolPositionEnum values
 * 
 */
public class SymbolPositionEnumJsonSerializer extends JsonSerializer<SymbolPositionEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(SymbolPositionEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
