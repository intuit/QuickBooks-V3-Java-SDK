package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.EntityTypeEnum;

/**
 * Custom JsonSerializer for reading EntityTypeEnum values
 * 
 */
public class EntityTypeEnumJsonSerializer extends JsonSerializer<EntityTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(EntityTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
