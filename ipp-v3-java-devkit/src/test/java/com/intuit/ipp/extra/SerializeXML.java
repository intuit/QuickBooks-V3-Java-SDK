package com.intuit.ipp.extra;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.ObjectFactory;

public class SerializeXML {
	public static <T> String serialize(T object){
		JAXBContext jc;
		try {
			StringWriter writer = new StringWriter();
			
			jc = JAXBContext.newInstance(object.getClass().getPackage().getName());
			Marshaller marshaller = jc.createMarshaller();
			
			Class<?> objectClass = object.getClass();
			String methodName = "create".concat(objectClass.getName().substring(objectClass.getName().lastIndexOf(".")+1));
			String paramName = object.getClass().getPackage().getName().concat(".").concat(objectClass.getName().substring(objectClass.getName().lastIndexOf(".")+1));
			
			ObjectFactory objectEntity = new ObjectFactory();
	        Class<?> objectEntityClass = objectEntity.getClass();
	        
	        Method method = objectEntityClass.getMethod(methodName, Class.forName(paramName));

	        @SuppressWarnings("unchecked")
			JAXBElement<? extends IEntity> jaxbElement = (JAXBElement<? extends IEntity>) method.invoke(objectEntity, object);
	        
	        marshaller.marshal(jaxbElement, writer);
	        return writer.toString();
	        
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
