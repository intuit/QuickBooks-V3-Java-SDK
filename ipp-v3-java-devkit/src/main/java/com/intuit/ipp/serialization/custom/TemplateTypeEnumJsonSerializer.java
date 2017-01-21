package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TemplateTypeEnum;

/**
 * Custom JsonSerializer for reading TemplateTypeEnum values
 * 
 */
public class TemplateTypeEnumJsonSerializer extends JsonSerializer<TemplateTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TemplateTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
