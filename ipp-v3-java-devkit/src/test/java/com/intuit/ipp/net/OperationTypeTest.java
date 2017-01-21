package com.intuit.ipp.net;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OperationTypeTest {

	@Test
	public void testOperationType_values() {
		OperationType[] operationTypes = OperationType.values();
		//Assert.assertEquals(operationTypes.length, 10);
	}
	
	@Test
	public void testOperationType_valueOf() {
		OperationType operationType = OperationType.valueOf("ADD");
		Assert.assertEquals(operationType.toString(), OperationType.ADD.toString());
	}
}
