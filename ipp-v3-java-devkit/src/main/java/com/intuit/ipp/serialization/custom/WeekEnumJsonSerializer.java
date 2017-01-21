package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.WeekEnum;

/**
 * Custom JsonSerializer for reading WeekEnum values
 * 
 */
public class WeekEnumJsonSerializer extends JsonSerializer<WeekEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(WeekEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
