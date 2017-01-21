package com.intuit.ipp.serialization.custom;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.intuit.ipp.data.TelephoneDeviceTypeEnum;

/**
 * Custom JsonSerializer for reading TelephoneDeviceTypeEnum values
 * 
 */
public class TelephoneDeviceTypeEnumJsonSerializer extends JsonSerializer<TelephoneDeviceTypeEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(TelephoneDeviceTypeEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.value());
	}

}
