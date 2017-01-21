package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CreditCardTypeEnum;

/**
 * Custom JsonSerializer for reading CreditCardTypeEnum values
 * 
 */
public class CreditCardTypeEnumJsonSerializer extends JsonSerializer<CreditCardTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CreditCardTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
