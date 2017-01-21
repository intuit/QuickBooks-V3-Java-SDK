package com.intuit.ipp.serialization;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.intuit.ipp.core.Response;
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
        String xml10pattern = "[^"
                + "\u0009\r\n"
                + "\u0020-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]";

        String validXmlString = str.replaceAll(xml10pattern,"");
        LOG.trace("valid : " + validXmlString);
		Object unmarshalledObject;
		try {
			Unmarshaller unmarshaller = JAXBContext.newInstance(cl.getPackage().getName()).createUnmarshaller();
			unmarshalledObject = unmarshaller.unmarshal(new StringReader(new String(validXmlString.getBytes(), "UTF-8")));
		} catch (Exception e) {
			LOG.error("unable to unmarshall in XML deserializer s 1", e);
			throw new SerializationException(e);
		}
		return ((JAXBElement<Response>) unmarshalledObject).getValue();
	}

}
