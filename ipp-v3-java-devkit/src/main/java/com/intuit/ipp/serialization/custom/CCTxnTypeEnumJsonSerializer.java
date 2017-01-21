package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CCTxnTypeEnum;

/**
 * Custom JsonSerializer for reading CCTxnTypeEnum values
 * 
 */
public class CCTxnTypeEnumJsonSerializer extends JsonSerializer<CCTxnTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CCTxnTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
