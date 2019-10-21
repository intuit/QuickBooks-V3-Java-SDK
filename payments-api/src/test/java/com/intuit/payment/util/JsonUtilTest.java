/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.util;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.data.Card;
import com.intuit.payment.exception.SerializationException;

/**
 * @author dderose
 *
 */
public class JsonUtilTest {

	// Test class used to ensure exception is thrown inside of JsonUtil
	public class ClassWithPrivateFields {
		ClassWithPrivateFields(int id, String name){
			id = id;
			name = name;
		}
		int id;
		String name;
	}

	@Test
	public void testSerialize() throws SerializationException {
		Card card = new Card.Builder().number("12345").build();
		String cardStr = JsonUtil.serialize(card);
		Assert.assertNotNull(cardStr);
		String result = "{\n  \"number\" : \"12345\"\n}";
		Assert.assertEquals(cardStr, result);
	}

	@Test
	public void testNullSerialize() throws SerializationException {
		String cardStr = JsonUtil.serialize(null);
		Assert.assertNull(cardStr);
	}

	@Test(expectedExceptions = SerializationException.class)
	public void testErrorSerialize() throws SerializationException {
		// Serializing this causes a InvalidDefinitionException to be thrown
		ClassWithPrivateFields classWithPrivateFields = new ClassWithPrivateFields(1, "John");
		JsonUtil.serialize(classWithPrivateFields);
	}

	@Test
    public void testDeserialize() throws SerializationException {
        String cardStr = "{\"number\":\"12345\"}";
        Card card = (Card) JsonUtil.deserialize(cardStr,  new TypeReference<Card>() {} );
        Assert.assertEquals(card.getNumber(), "12345");
    }

	@Test(expectedExceptions = SerializationException.class)
	public void testErrorDeserialize() throws SerializationException {
		String cardStr = "{\"number\":\"12345\"\"}";
		JsonUtil.deserialize(cardStr,  new TypeReference<Card>() {} );
	}

	@SuppressWarnings("unchecked")
	@Test
    public void testDeserializeList() throws SerializationException {
        String cardList = "[{\"number\":\"12345\"}]";      
		List<Card> cards = (List<Card>) JsonUtil.deserialize(cardList,  new TypeReference<List<Card>>() {} );
        Assert.assertEquals(cards.size(), 1);
        Assert.assertEquals(cards.get(0).getNumber(), "12345");
    }


}
