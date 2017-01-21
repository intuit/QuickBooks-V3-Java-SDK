package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CCTxnModeEnum;

/**
 * Custom JsonSerializer for reading CCTxnModeEnum values
 * 
 */
public class CCTxnModeEnumJsonSerializer extends JsonSerializer<CCTxnModeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CCTxnModeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
