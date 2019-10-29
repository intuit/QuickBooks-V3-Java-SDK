/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.services;

import org.testng.annotations.Test;

import com.intuit.payment.data.Card;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;

public class CardServiceTest {
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testcreateCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.create(new Card(), "");
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetAllCardsCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getAllCards("");
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetAllCardsCustomerNull() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getAllCards(null);
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetAllCardCountCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getAllCards("", 0);
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetAllCardCountCustomerNull() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getAllCards(null, 0);
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testcreateFromTokenCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.createFromToken(new Token(), "");
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testdeleteCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.delete("", "");
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetCardCustomerBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getCard("", "12345");
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
    public void testgetCardCardIdBlank() throws BaseException {
       CardService cardService = new CardService();
	   cardService.getCard("customerid", "");
    }
}
