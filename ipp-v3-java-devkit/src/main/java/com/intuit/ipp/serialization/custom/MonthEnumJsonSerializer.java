package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.MonthEnum;

/**
 * Custom JsonSerializer for reading MonthEnum values
 * 
 */
public class MonthEnumJsonSerializer extends JsonSerializer<MonthEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(MonthEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
