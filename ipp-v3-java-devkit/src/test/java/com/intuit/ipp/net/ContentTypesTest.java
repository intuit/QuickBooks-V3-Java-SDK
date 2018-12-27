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
package com.intuit.ipp.net;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ContentTypesTest {

	@Test
	public void testGetContentType_null() {
		String contentType = ContentTypes.getContentType(null);
		Assert.assertNull(contentType);
	}
	
	@Test
	public void testGetContentType_others() {
		String contentType = ContentTypes.getContentType("others");
		Assert.assertNull(contentType);
	}
	
	@Test
	public void testGetContentType_values() {
		ContentTypes[] contentTypes = ContentTypes.values();
		Assert.assertEquals(contentTypes.length, 7);
	}
	
	@Test
	public void testGetContentType_valueOf() {
		ContentTypes contentTypes = ContentTypes.valueOf("JSON");
		Assert.assertEquals(contentTypes.toString(), ContentTypes.JSON.toString());
	}
}
