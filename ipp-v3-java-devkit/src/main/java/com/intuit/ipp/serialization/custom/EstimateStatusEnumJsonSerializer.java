package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.EstimateStatusEnum;

/**
 * Custom JsonSerializer for reading EstimateStatusEnum values
 * 
 */
public class EstimateStatusEnumJsonSerializer extends JsonSerializer<EstimateStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(EstimateStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
