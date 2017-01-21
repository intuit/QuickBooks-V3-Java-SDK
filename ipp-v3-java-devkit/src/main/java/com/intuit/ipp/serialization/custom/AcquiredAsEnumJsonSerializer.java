package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.AcquiredAsEnum;

/**
 * Custom JsonSerializer for reading AcquiredAsEnum values
 * 
 */
public class AcquiredAsEnumJsonSerializer extends JsonSerializer<AcquiredAsEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(AcquiredAsEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
