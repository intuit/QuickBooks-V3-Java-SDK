package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PurchaseOrderStatusEnum;

/**
 * Custom JsonSerializer for reading PurchaseOrderStatusEnum values
 * 
 */
public class PurchaseOrderStatusEnumJsonSerializer extends JsonSerializer<PurchaseOrderStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PurchaseOrderStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
