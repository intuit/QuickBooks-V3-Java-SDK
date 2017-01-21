package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PaySalesTaxEnum;

/**
 * Custom JsonSerializer for reading PaySalesTaxEnum values
 * 
 */
public class PaySalesTaxEnumJsonSerializer extends JsonSerializer<PaySalesTaxEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PaySalesTaxEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
