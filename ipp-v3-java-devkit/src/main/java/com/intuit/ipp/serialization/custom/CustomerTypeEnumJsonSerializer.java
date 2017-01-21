package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.CustomerTypeEnum;

/**
 * Custom JsonSerializer for reading CustomerTypeEnum values
 * 
 */
public class CustomerTypeEnumJsonSerializer extends JsonSerializer<CustomerTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(CustomerTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
