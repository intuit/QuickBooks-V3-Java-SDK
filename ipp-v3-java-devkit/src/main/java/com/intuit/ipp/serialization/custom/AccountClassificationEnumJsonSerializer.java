package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.AccountClassificationEnum;

/**
 * Custom JsonSerializer for reading AccountClassificationEnum values
 * 
 */
public class AccountClassificationEnumJsonSerializer extends JsonSerializer<AccountClassificationEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(AccountClassificationEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
