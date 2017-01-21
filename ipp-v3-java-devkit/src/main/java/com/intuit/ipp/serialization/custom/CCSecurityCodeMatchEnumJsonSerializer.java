package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CCSecurityCodeMatchEnum;

/**
 * Custom JsonSerializer for reading CCSecurityCodeMatchEnum values
 * 
 */
public class CCSecurityCodeMatchEnumJsonSerializer extends JsonSerializer<CCSecurityCodeMatchEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CCSecurityCodeMatchEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
