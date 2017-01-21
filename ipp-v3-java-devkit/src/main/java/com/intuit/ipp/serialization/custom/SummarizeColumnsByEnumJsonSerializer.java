package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.SummarizeColumnsByEnum;

/**
 * Custom JsonSerializer for reading SummarizeColumnsByEnum values
 * 
 */
public class SummarizeColumnsByEnumJsonSerializer extends JsonSerializer<SummarizeColumnsByEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(SummarizeColumnsByEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
