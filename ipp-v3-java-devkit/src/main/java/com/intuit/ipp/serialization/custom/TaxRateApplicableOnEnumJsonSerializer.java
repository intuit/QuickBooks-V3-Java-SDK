package com.intuit.ipp.serialization.custom;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TaxApplicableOnEnum;
import com.intuit.ipp.data.TaxRateApplicableOnEnum;


public class TaxRateApplicableOnEnumJsonSerializer extends JsonSerializer<TaxRateApplicableOnEnum>{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TaxRateApplicableOnEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}


}


