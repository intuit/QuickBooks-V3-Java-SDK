package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.APCreditCardOperationEnum;

/**
 * Custom JsonSerializer for reading APCreditCardOperationEnum values
 * 
 */
public class APCreditCardOperationEnumJsonSerializer extends JsonSerializer<APCreditCardOperationEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(APCreditCardOperationEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
