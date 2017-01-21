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
