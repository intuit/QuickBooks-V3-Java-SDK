package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CCAVSMatchEnum;

/**
 * Custom JsonSerializer for reading CCAVSMatchEnum values
 * 
 */
public class CCAVSMatchEnumJsonSerializer extends JsonSerializer<CCAVSMatchEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CCAVSMatchEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
