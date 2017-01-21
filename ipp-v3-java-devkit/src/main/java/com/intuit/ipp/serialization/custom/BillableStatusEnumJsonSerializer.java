package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.BillableStatusEnum;

/**
 * Custom JsonSerializer for reading BillableStatusEnum values
 * 
 */
public class BillableStatusEnumJsonSerializer extends JsonSerializer<BillableStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(BillableStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
