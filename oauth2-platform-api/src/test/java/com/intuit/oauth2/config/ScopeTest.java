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
package com.intuit.oauth2.config;

import com.intuit.oauth2.exception.InvalidRequestException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Config class to hold the proxy properties
 *
 * @author dderose
 */
public class ScopeTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testfromValueforIllegalArgument()
	{
			String badenum ="I am bad value";
			Scope.fromValue(badenum);
			fail(badenum + " is not present in the Scope enum");
	}


	@DataProvider(parallel = true, name = "validScopeValuesdp")
	public Iterator<Object[]> validScopeValuesdp() {

		Set<Object[]> dataToBeReturned = new HashSet<>();

		for (Scope scope : Scope.values()) {
			dataToBeReturned.add(new Object[]{scope.name(), scope.value()});
		}
		return dataToBeReturned.iterator();
	}


	@Test(dataProvider = "validScopeValuesdp")
	public void testfromValueforInvalidArgument(String name, String value)
	{
			try{
				Scope returnedScope = Scope.fromValue(value);
				assertNotNull(returnedScope);
				assertTrue(returnedScope.name().equals(name));
			} catch (Exception e)
			{
				fail(value + "is a valid Scope enumvalue.");
			}
	}


}
