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
package com.intuit.ipp.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringUtilsTest {

	@Test
	public void testHasText_true() {
		String text = "data";
		boolean status = StringUtils.hasText(text);
		Assert.assertTrue(status);
	}
	
	@Test
	public void testHasText_false() {
		String text = "";
		boolean status = StringUtils.hasText(text);
		Assert.assertFalse(status);
	}
	
	@Test
	public void testHasText_null() {
		boolean status = StringUtils.hasText(null);
		Assert.assertFalse(status);
	}
	
	@Test
	public void testHasBytes_true() {
		byte[] data = "data".getBytes();
		boolean status = StringUtils.hasBytes(data);
		Assert.assertTrue(status);
	}
	
	@Test
	public void testHasBytes_false() {
		byte[] data = "".getBytes();
		boolean status = StringUtils.hasBytes(data);
		Assert.assertFalse(status);
	}
	
	@Test
	public void testHasBytes_null() {
		boolean status = StringUtils.hasBytes(null);
		Assert.assertFalse(status);
	}
	
	@Test
	public void testStringUtils() {
		StringUtils stringUtils = StringUtils.getInstance();
		Assert.assertNotNull(stringUtils);
	}
}
