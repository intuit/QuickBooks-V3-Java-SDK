package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.QboEstimateStatusEnum;

/**
 * Custom JsonSerializer for reading QboEstimateStatusEnum values
 * 
 */
public class QboEstimateStatusEnumJsonSerializer extends JsonSerializer<QboEstimateStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(QboEstimateStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
