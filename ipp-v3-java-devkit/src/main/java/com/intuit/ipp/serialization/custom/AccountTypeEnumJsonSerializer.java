package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.AccountTypeEnum;

/**
 * Custom JsonSerializer for reading AccountTypeEnum values
 * 
 */
public class AccountTypeEnumJsonSerializer extends JsonSerializer<AccountTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(AccountTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
