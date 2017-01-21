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
		Assert.assertEquals(contentTypes.length, 6);
	}
	
	@Test
	public void testGetContentType_valueOf() {
		ContentTypes contentTypes = ContentTypes.valueOf("JSON");
		Assert.assertEquals(contentTypes.toString(), ContentTypes.JSON.toString());
	}
}
