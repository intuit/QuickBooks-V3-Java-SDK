package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.Gender;

/**
 * Custom JsonSerializer for reading GenderEnum values
 * 
 */
public class GenderEnumJsonSerializer extends JsonSerializer<Gender> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(Gender value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
