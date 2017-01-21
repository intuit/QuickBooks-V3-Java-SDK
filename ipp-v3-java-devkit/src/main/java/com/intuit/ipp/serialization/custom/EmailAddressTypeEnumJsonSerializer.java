package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.EmailAddressTypeEnum;

/**
 * Custom JsonSerializer for reading EmailAddressTypeEnum values
 * 
 */
public class EmailAddressTypeEnumJsonSerializer extends JsonSerializer<EmailAddressTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(EmailAddressTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
