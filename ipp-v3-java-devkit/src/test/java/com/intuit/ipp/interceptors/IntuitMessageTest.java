package com.intuit.ipp.interceptors;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntuitMessageTest {
    private boolean platformService;
	private boolean entitlementService;
    private IntuitMessage intuitMessage;

    @Test
    public void testSetersAndGetters() {
        //test values for testing setter
        platformService = false;
        entitlementService = false;
        intuitMessage = new IntuitMessage();

        //set test values
        intuitMessage.setPlatformService(platformService);
        intuitMessage.setEntitlementService(entitlementService);

        //get test values
        Assert.assertEquals(intuitMessage.isPlatformService(), platformService);
        Assert.assertEquals(intuitMessage.isEntitlementService(), entitlementService);
    }
}