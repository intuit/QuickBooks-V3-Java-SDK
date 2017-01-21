package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.UOMFeatureTypeEnum;

/**
 * Custom JsonSerializer for reading UOMFeatureTypeEnum values
 * 
 */
public class UOMFeatureTypeEnumJsonSerializer extends JsonSerializer<UOMFeatureTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(UOMFeatureTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
