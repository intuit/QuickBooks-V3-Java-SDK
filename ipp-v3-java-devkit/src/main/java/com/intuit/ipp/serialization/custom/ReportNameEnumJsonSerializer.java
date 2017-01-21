//package com.intuit.ipp.serialization.custom;
//
//import java.io.IOException;
//
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.SerializerProvider;
//
//import com.intuit.ipp.data.ReportNameEnum;
//
///**
// * Custom JsonSerializer for reading ReportNameEnum values
// * 
// */
//public class ReportNameEnumJsonSerializer extends JsonSerializer<ReportNameEnum> {
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void serialize(ReportNameEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//		jgen.writeString(value.value());
//	}
//
//}
