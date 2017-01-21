package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TaxTypeApplicablityEnum;

/**
 * Custom JsonSerializer for reading TaxTypeApplicablityEnum values
 * 
 */
public class TaxTypeApplicablityEnumJsonSerializer extends JsonSerializer<TaxTypeApplicablityEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TaxTypeApplicablityEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
