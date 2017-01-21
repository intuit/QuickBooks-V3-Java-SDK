package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.BillPaymentTypeEnum;

/**
 * Custom JsonSerializer for reading BillPaymentTypeEnum values
 * 
 */
public class BillPaymentTypeEnumJsonSerializer extends JsonSerializer<BillPaymentTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(BillPaymentTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
