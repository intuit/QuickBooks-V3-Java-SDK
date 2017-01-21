package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CCPaymentStatusEnum;

/**
 * Custom JsonSerializer for reading CCPaymentStatusEnum values
 * 
 */
public class CCPaymentStatusEnumJsonSerializer extends JsonSerializer<CCPaymentStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CCPaymentStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
