package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.GlobalTaxCalculationEnum;

/**
 * Custom JsonSerializer for reading GlobalTaxCalculationEnum values
 * 
 */
public class GlobalTaxCalculationEnumJsonSerializer extends JsonSerializer<GlobalTaxCalculationEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(GlobalTaxCalculationEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
