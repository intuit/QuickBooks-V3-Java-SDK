package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.SalesRepTypeEnum;

/**
 * Custom JsonSerializer for reading SalesRepTypeEnum values
 * 
 */
public class SalesRepTypeEnumJsonSerializer extends JsonSerializer<SalesRepTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(SalesRepTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
