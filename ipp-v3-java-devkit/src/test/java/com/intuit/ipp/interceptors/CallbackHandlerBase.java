package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.CDCQueryResult;
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
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

    public class IntuitTestEntity extends IntuitEntity {}

    public JAXBElement<? extends IntuitEntity> getDummyTestEntity() {
        QName qname = new QName("http://www.example.com", "interceptor-test");
        return new JAXBElement<>(qname, IntuitTestEntity.class, new IntuitTestEntity());
    }
}
