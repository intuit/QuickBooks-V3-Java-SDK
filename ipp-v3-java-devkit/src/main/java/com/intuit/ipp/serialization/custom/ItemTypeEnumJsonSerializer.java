package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.ItemTypeEnum;

/**
 * Custom JsonSerializer for reading ItemTypeEnum values
 * 
 */
public class ItemTypeEnumJsonSerializer extends JsonSerializer<ItemTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(ItemTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
