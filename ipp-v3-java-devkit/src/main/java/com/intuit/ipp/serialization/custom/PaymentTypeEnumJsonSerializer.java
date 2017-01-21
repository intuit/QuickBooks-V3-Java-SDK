package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PaymentTypeEnum;

/**
 * Custom JsonSerializer for reading PaymentTypeEnum values
 * 
 */
public class PaymentTypeEnumJsonSerializer extends JsonSerializer<PaymentTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PaymentTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
