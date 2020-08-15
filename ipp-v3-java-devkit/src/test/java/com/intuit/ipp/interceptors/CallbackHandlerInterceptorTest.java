package com.intuit.ipp.interceptors;


import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CallbackHandlerInterceptorTest extends CallbackHandlerBase {


    @Test(expectedExceptions = NullPointerException.class)
    public void nullMessageNotOK() throws FMSException {
        callback().execute(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void emptyMessageNotOK() throws FMSException {
        callback().execute(new IntuitMessage());
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void arbitraryResponseNotOk() throws FMSException {
        IntuitMessage m = new IntuitMessage();
        m.getResponseElements().setResponse(new Response() {});
        callback().execute(m);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void noCallBackMessageNotOk() throws FMSException {
        IntuitMessage m = new IntuitMessage();
        m.getResponseElements().setResponse(new IntuitResponse());
        callback().execute(m);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void noCallBackHandlerNotOk() throws FMSException {
        IntuitMessage m = new IntuitMessage();
        m.getResponseElements().setResponse(new IntuitResponse());
        m.getResponseElements().setCallbackMessage(new CallbackMessage());
        callback().execute(m);
    }

    /**
     * This test demonstrates minimum required setup
     * for interceptor to successfully execute on request
     *
     * @throws FMSException
     */
    @Test
    public void stubOK() throws FMSException {
        IntuitMessage m = new IntuitMessage();
        m.getResponseElements().setResponse(new IntuitResponse());
        final CallbackMessage message = new CallbackMessage();
        m.getResponseElements().setCallbackMessage(message);
        m.getRequestElements().setCallbackHandler(new CallbackHandler() {
            @Override
            public void execute(CallbackMessage callbackMessage) {
                assertEquals(message,callbackMessage, "Callback message is not the same as one in response");
            }
        });
        callback().execute(m);
    }

    @Test
    public void  entityIsOk() throws FMSException {
        final IntuitResponse response = new IntuitResponse();
        response.setIntuitObject(getDummyTestEntity());

        final CallbackMessage message  = invokeInterceptor(response);
        assertNotNull(message.getEntity());
        assertTrue(message.getEntity() instanceof IntuitTestEntity);
    }

    @Test
    public void  queryIsOk() throws FMSException {

        final QueryResponse value = new QueryResponse();
        value.setMaxResults(10);

        final IntuitResponse response = new IntuitResponse();
        response.setQueryResponse(value);

        final CallbackMessage message = invokeInterceptor(response);
        assertNotNull(message.getQueryResult());
        assertEquals(10,  (int)message.getQueryResult().getMaxResults());
    }




}