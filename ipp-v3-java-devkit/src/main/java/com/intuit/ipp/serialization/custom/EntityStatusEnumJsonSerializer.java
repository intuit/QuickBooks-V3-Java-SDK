package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.EntityStatusEnum;

/**
 * Custom JsonSerializer for reading EntityStatusEnum values
 * 
 */
public class EntityStatusEnumJsonSerializer extends JsonSerializer<EntityStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(EntityStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
