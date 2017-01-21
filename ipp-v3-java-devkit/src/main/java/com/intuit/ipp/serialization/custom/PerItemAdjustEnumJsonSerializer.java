package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PerItemAdjustEnum;

/**
 * Custom JsonSerializer for reading PerItemAdjustEnum values
 * 
 */
public class PerItemAdjustEnumJsonSerializer extends JsonSerializer<PerItemAdjustEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PerItemAdjustEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
