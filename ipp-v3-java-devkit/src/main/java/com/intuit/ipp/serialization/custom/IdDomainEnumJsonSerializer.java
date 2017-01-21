package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.IdDomainEnum;

/**
 * Custom JsonSerializer for reading IdDomainEnum values
 * 
 */
public class IdDomainEnumJsonSerializer extends JsonSerializer<IdDomainEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(IdDomainEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
