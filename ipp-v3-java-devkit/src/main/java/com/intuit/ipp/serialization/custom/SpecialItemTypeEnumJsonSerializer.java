package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.SpecialItemTypeEnum;

/**
 * Custom JsonSerializer for reading SpecialItemTypeEnum values
 * 
 */
public class SpecialItemTypeEnumJsonSerializer extends JsonSerializer<SpecialItemTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(SpecialItemTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
