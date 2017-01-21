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
