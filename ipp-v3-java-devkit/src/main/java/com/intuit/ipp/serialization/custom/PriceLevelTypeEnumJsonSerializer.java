package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PriceLevelTypeEnum;

/**
 * Custom JsonSerializer for reading PriceLevelTypeEnum values
 * 
 */
public class PriceLevelTypeEnumJsonSerializer extends JsonSerializer<PriceLevelTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PriceLevelTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
