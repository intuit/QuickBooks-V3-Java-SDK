package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.ContactTypeEnum;

/**
 * Custom JsonSerializer for reading ContactTypeEnum values
 * 
 */
public class ContactTypeEnumJsonSerializer extends JsonSerializer<ContactTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(ContactTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
