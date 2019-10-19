package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.CDCQueryResult;
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;

import java.util.List;

public class CallbackHandlerBase {

    private static CallbackHandlerInterceptor interceptor = new CallbackHandlerInterceptor();


    protected List<CDCQueryResult> invokeCDC(List<CDCResponse> cdcResponse) throws FMSException {
        final IntuitResponse response = new IntuitResponse();
        response.setCDCResponse(cdcResponse);
        return invokeInterceptor(response).getCDCQueryResults();
    }

    protected CallbackMessage invokeInterceptor(final IntuitResponse response) throws FMSException {
        final CallbackMessage message = new CallbackMessage();
        IntuitMessage m = new IntuitMessage();
        m.getResponseElements().setResponse(response);
        m.getResponseElements().setCallbackMessage(message);
        m.getRequestElements().setCallbackHandler(new  CallbackHandler() {
            @Override
            public void execute(CallbackMessage callbackMessage) {

            }
        } );
        interceptor.execute(m);
        return message;
    }

    public static CallbackHandlerInterceptor callback() {
        return interceptor;
    }
}
