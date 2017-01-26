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
package com.intuit.ipp.query;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Logger;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * For intercepting method and adding the name of method called in threadlocal stringbuilder.
 * 
 * @author lokeshg
 * 
 */
public class MyMethodInterceptor implements MethodInterceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable NUM_3
	 */
	private static final int NUM_3 = 3;
	
	/**
	 * variable NUM_2
	 */
	private static final int NUM_2 = 2;
	
	/**
	 * Constructor MyMethodInterceptor
	 * 
	 */
	public MyMethodInterceptor() {
	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws FMSException {

		if (GenerateQuery.path.get() == null) {
			GenerateQuery.path.set(new Path<Object>(extractPropertyName(arg1), extractEntity(arg0)));
		} else {
			String parentPath = GenerateQuery.path.get().getPathString();
			GenerateQuery.path.get().setPathString(parentPath.concat(".").concat(extractPropertyName(arg1)));
		}
		return createInstance(arg0, arg1, arg2, arg3);
	}

	/**
	 * Method to extract the entity
	 * @param obj the object
	 * @return String the extracted entity
	 */
	private String extractEntity(Object obj) {
		String name = obj.getClass().getSimpleName();
		String[] extracted = name.split("\\$\\$");
		if (extracted.length == NUM_3) {
			return extracted[0];
		}
		return null;
	}

	/**
	 * extract the name of property from method called.
	 * 
	 * @param method
	 * @return
	 */
	protected String extractPropertyName(Method method) {
		String name = method.getName();
		name = name.startsWith("is") ? name.substring(NUM_2) : name.substring(NUM_3);
		return name;
	}

	/**
	 * create the object for linked method call or object of leaf node
	 * 
	 * @param type
	 * @return
	 * @throws Throwable 
	 */
	@SuppressWarnings("unchecked")
	public <T> T createInstance(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) 
			throws FMSException {
		Object obj = null;
		Class<?> type = arg1.getReturnType();
		if (String.class.equals(type)) {
			obj = null;
		} else if (Integer.class.equals(type) || int.class.equals(type)) {
			obj = Integer.valueOf(0);
		} else if (Byte.class.equals(type) || byte.class.equals(type)) {
			obj = Integer.valueOf(0);
		} else if (java.util.Date.class.equals(type)) {
			obj = new Date();
		} else if (java.sql.Timestamp.class.equals(type)) {
			obj = new Timestamp(System.currentTimeMillis());
		} else if (java.sql.Date.class.equals(type)) {
			obj = new java.sql.Date(System.currentTimeMillis());
		} else if (java.sql.Time.class.equals(type)) {
			obj = new java.sql.Time(System.currentTimeMillis());
		} else if (Long.class.equals(type) || long.class.equals(type)) {
			obj = Long.valueOf(0);
		} else if (Short.class.equals(type) || short.class.equals(type)) {
			obj = Short.valueOf("0");
		} else if (Double.class.equals(type) || double.class.equals(type)) {
			obj = Double.valueOf(0);
		} else if (Float.class.equals(type) || float.class.equals(type)) {
			obj = Float.valueOf(0);
		} else if (BigInteger.class.equals(type)) {
			obj = BigInteger.valueOf(0);
		} else if (BigDecimal.class.equals(type)) {
			obj = BigDecimal.valueOf(0);
		} else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
			obj = Boolean.TRUE;
		} else if (List.class.isAssignableFrom(type)) {
			try {
				Type t = arg1.getGenericReturnType();
				Object value = getObject(t);
				Object queryValue = GenerateQuery.createQueryEntity(value);
				obj = arg3.invokeSuper(arg0, arg2);
				((List<Object>) obj).add(queryValue);
			} catch (Throwable t) {
				throw new FMSException(t);
			}
		} else if (Enum.class.isAssignableFrom(type)) {
			LOG.debug("Create instance for Enum");
			// Create EnumObject
		} else if (type.isArray()) {
			// Create ArrayObject
			LOG.debug("Create instance for Array Object");
		} else if (!Modifier.isFinal(type.getModifiers())) {
			obj = GenerateQuery.createQueryEntity(type);
		} else {
			obj = null;
		}
		return (T) obj;
	}

	/**
	 * Method to get the object for the given type
	 * 
	 * @param type the type
	 * @return Object the object
	 * @throws Throwable
	 */
	public Object getObject(Type type) throws FMSException {
		Object obj = null;
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			String typeString = pt.getActualTypeArguments()[0].toString().split(" ")[1];
			try {
				obj = Class.forName(typeString).newInstance();
			} catch (Exception e) {
				throw new FMSException(e);
			}
		}
		return obj;
	}	
}
