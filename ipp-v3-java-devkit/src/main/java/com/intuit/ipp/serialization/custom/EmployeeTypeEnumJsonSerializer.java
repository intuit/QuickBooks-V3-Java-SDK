package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.EmployeeTypeEnum;

/**
 * Custom JsonSerializer for reading EmployeeTypeEnum values
 * 
 */
public class EmployeeTypeEnumJsonSerializer extends JsonSerializer<EmployeeTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(EmployeeTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
