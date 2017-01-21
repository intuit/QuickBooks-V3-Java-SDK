package com.intuit.ipp.serialization.custom;

import java.lang.reflect.Type;

import javax.xml.bind.JAXBElement;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.util.MessageUtils;

public class JAXBElementJsonSerializer implements JsonSerializer<JAXBElement<? extends IntuitEntity>> {

	  @Override
	  public JsonElement serialize(final JAXBElement<? extends IntuitEntity> jaxbVariable, final Type typeOfSrc, final JsonSerializationContext context) {
		  return new JsonPrimitive(MessageUtils.getGson().toJson(jaxbVariable.getValue()));
	  }
}
