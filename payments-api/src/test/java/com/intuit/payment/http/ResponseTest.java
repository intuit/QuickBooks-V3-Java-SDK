package com.intuit.payment.http;

import com.intuit.payment.data.Errors;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author praveenadg
 */
public class ResponseTest {
    private static final int STATUS_CODE = 200;
    private static final String INTUIT_TID = "intuit_tid";
    private static final String ERROR_INTUIT_TID = "error_intuit_tid";
    private static final String CONTENT = "content";

    @Test
    public void testAllGetters() {
        Response response = new Response(STATUS_CODE, CONTENT, INTUIT_TID);
        Assert.assertEquals(response.getStatusCode(), STATUS_CODE);
        Assert.assertEquals(response.getContent(), CONTENT);
        Assert.assertEquals(response.getIntuit_tid(), INTUIT_TID);
    }

    @Test
    public void testAllSetters() {
        Response response = new Response(STATUS_CODE, CONTENT, INTUIT_TID);
        response.setResponseObject(INTUIT_TID);
        Errors errors = new Errors();
        errors.setIntuit_tid(ERROR_INTUIT_TID);
        response.setErrors(errors);
        Assert.assertEquals(response.getResponseObject(), INTUIT_TID);
        Assert.assertEquals(response.getErrors().getIntuit_tid(), ERROR_INTUIT_TID);
        Assert.assertEquals(response.getStatusCode(), STATUS_CODE);
        Assert.assertEquals(response.getContent(), CONTENT);
        Assert.assertEquals(response.getIntuit_tid(), INTUIT_TID);

    }
}
