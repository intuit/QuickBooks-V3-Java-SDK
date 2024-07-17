/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.serialization;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.MessageUtils;

/**
 * class to serialize/deserialize the given data using XMLserialization algorithm
 * 
 */
public class XMLSerializer implements IEntitySerializer {

	/**
	 * the logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}
	 */
	public <T> String serialize(T object) throws SerializationException {

		if (object == null) {
			return null;
		}
		
		StringWriter writer = new StringWriter();
		try {
			Marshaller marshaller = MessageUtils.createMarshaller();
			marshaller.marshal(object, writer);
		} catch (JAXBException e) {
			LOG.error("unable to marshall in XML Serializer", e);
			throw new SerializationException(e);
		}
		String documentToPost = writer.toString();
		LOG.debug("XML serialized data : " + documentToPost);
		return documentToPost;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Response deserialize(String str, Class<?> cl) throws SerializationException {
		Object unmarshalledObject = deserializeData(str, cl);
		return ((JAXBElement<Response>) unmarshalledObject).getValue();
	}
	


	private Object deserializeData(String str, Class<?> cl) throws SerializationException {
		String validXmlString = getValidatedXmlString(str);
        LOG.trace("valid : " + validXmlString);
		Object unmarshalledObject;
		try {
			Unmarshaller unmarshaller = JAXBContext.newInstance(cl.getPackage().getName()).createUnmarshaller();
			unmarshalledObject = unmarshaller.unmarshal(new StringReader(new String(validXmlString.getBytes(), "UTF-8")));
		} catch (Exception e) {
			LOG.error("unable to unmarshall in XML deserializer s 1", e);
			throw new SerializationException(e);
		}
		return unmarshalledObject;
	}

	private String getValidatedXmlString(String str) {
		String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";

        String validXmlString = str.replaceAll(xml10pattern,"");
		return validXmlString;
	}

	@Override
	public Response deserializeEntitlements(String decompressedData, Class<EntitlementsResponse> cl)
			throws SerializationException {
		
		String validXmlString = getValidatedXmlString(decompressedData);
        LOG.trace("valid : " + validXmlString);
		EntitlementsResponse response;
		try {
		    //create the jaxbcontext 
			Unmarshaller unmarshaller = JAXBContext.newInstance(EntitlementsResponse.class).createUnmarshaller();
		    //call the unmarshall method
		    response=(EntitlementsResponse) unmarshaller.unmarshal(new StringReader(new String(validXmlString.getBytes(), "UTF-8")));
		    		    
		} catch (Exception e) {
			LOG.error("unable to unmarshall in XML deserializer s 1", e);
			throw new SerializationException(e);
		}
        return response;
	}

}
