package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TxnTypeEnum;

/**
 * Custom JsonSerializer for reading TxnTypeEnum values
 * 
 */
public class TxnTypeEnumJsonSerializer extends JsonSerializer<TxnTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TxnTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
