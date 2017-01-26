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

public class PropertyHelperTest {

	@Test
	public void testPropertyHelper() {
		PropertyHelper propertyHelper = PropertyHelper.getInstance();
		Assert.assertNotNull(propertyHelper);
	}
	
	@Test
	public void testPropertyVersion() {
		PropertyHelper propertyHelper = PropertyHelper.getInstance();
		String version = propertyHelper.getVersion();
		Assert.assertNotNull(version);
	}
	
	@Test
	public void testPropertyRequestSource() {
		PropertyHelper propertyHelper = PropertyHelper.getInstance();
		String requestSource = propertyHelper.getRequestSource();
		Assert.assertNotNull(requestSource);
	}
}
