package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.AccountSubTypeEnum;

/**
 * Custom JsonSerializer for reading AccountSubTypeEnum values
 * 
 */
public class AccountSubTypeEnumJsonSerializer extends JsonSerializer<AccountSubTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(AccountSubTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
