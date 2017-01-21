package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PaymentStatusEnum;

/**
 * Custom JsonSerializer for reading PaymentStatusEnum values
 * 
 */
public class PaymentStatusEnumJsonSerializer extends JsonSerializer<PaymentStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PaymentStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
