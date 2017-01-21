package com.intuit.ipp.interceptors;


import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.FMSException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DeserializeInterceptorTest {

    private DeserializeInterceptor instance = new DeserializeInterceptor();
    private IntuitMessage message = new IntuitMessage();


    @Test
    public void testCheckTouchlessExecution() throws FMSException  {
        message.getResponseElements().setContentTypeHeader("application/pdf");
        IntuitResponse response = new IntuitResponse();
        response.setStatus("example");
        message.getResponseElements().setResponse(response);
        instance.execute(message);
        Assert.assertEquals(message.getResponseElements().getResponse(), null);

    }


}