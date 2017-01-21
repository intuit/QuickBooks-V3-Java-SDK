package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.ReportBasisEnum;

/**
 * Custom JsonSerializer for reading ReportBasisEnum values
 * 
 */
public class ReportBasisEnumJsonSerializer extends JsonSerializer<ReportBasisEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(ReportBasisEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
