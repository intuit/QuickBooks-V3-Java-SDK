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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.SerializationException;

public class SerializerFactoryTest {

	@Test
	public void testGetSerializer_xml() throws SerializationException {
		IEntitySerializer serializer = SerializerFactory.getSerializer("xml");
		Assert.assertTrue(serializer instanceof XMLSerializer, "Object serializer is not instance of XMLSerializer");
	}
	
	@Test
	public void testGetSerializer_json() throws SerializationException {
		IEntitySerializer serializer = SerializerFactory.getSerializer("json");
		Assert.assertTrue(serializer instanceof JSONSerializer, "Object serializer is not instance of JSONSerializer");
	}

	@Test
	public void testGetSerializer_others() throws SerializationException {
		boolean isException = false;
		try {
			SerializerFactory.getSerializer("others");
		} catch (SerializationException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test
	public void testGetSerializer_null() throws SerializationException {
		boolean isException = false;
		try {
			SerializerFactory.getSerializer(null);
		} catch (SerializationException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test
	public void testIsValidSerializationFormat_xml() throws SerializationException {
		boolean isValid = SerializerFactory.isValidSerializeFormat("xml");
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void testIsValidSerializationFormat_json() throws SerializationException {
		boolean isValid = SerializerFactory.isValidSerializeFormat("json");
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void testIsValidSerializationFormat_others() throws SerializationException {
		boolean isValid = false;
		try {
			isValid = SerializerFactory.isValidSerializeFormat("others");
		} catch (SerializationException e) {
			isValid = false;
		}
		Assert.assertFalse(isValid);
	}
	
	//@Test(expectedExceptions = SerializationException.class)
	@Test
	public void testIsValidSerializationFormat_null() throws SerializationException {
		boolean isValid = false;
		try {
			isValid = SerializerFactory.isValidSerializeFormat(null);
		} catch (SerializationException e) {
			isValid = false;
		}
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testSerializerFactory() {
		SerializerFactory factory = SerializerFactory.getInstance();
		Assert.assertNotNull(factory);
	}
}
