package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.JobStatusEnum;

/**
 * Custom JsonSerializer for reading JobStatusEnum values
 * 
 */
public class JobStatusEnumJsonSerializer extends JsonSerializer<JobStatusEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(JobStatusEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
