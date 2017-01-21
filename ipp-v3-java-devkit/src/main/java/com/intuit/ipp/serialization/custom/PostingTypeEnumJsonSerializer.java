package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.PostingTypeEnum;

/**
 * Custom JsonSerializer for reading PostingTypeEnum values
 * 
 */
public class PostingTypeEnumJsonSerializer extends JsonSerializer<PostingTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(PostingTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
