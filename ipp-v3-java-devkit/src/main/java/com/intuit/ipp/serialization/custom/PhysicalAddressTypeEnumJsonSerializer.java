package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PhysicalAddressTypeEnum;

/**
 * Custom JsonSerializer for reading PhysicalAddressTypeEnum values
 * 
 */
public class PhysicalAddressTypeEnumJsonSerializer extends JsonSerializer<PhysicalAddressTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PhysicalAddressTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
