package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TaxRateDisplayTypeEnum;

/**
 * Custom JsonSerializer for reading TaxRateDisplayTypeEnum values
 * 
 */
public class TaxRateDisplayTypeEnumJsonSerializer extends JsonSerializer<TaxRateDisplayTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TaxRateDisplayTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
