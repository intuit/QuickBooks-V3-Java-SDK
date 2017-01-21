package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import com.intuit.ipp.data.ColumnTypeEnum;

/**
 * Custom JsonSerializer for reading ColTypeEnum values
 * 
 */
public class ColTypeEnumJsonSerializer extends JsonSerializer<ColumnTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(ColumnTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
