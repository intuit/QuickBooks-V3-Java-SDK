package com.intuit.ipp.net;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MethodTypeTest {

	@Test
	public void testMethodType_values() {
		MethodType[] methodTypes = MethodType.values();
		Assert.assertEquals(methodTypes.length, 2);
	}
	
	@Test
	public void testMethodType_valueOf() {
		MethodType methodType = MethodType.valueOf("GET");
		Assert.assertEquals(methodType.toString(), MethodType.GET.toString());
	}
}
