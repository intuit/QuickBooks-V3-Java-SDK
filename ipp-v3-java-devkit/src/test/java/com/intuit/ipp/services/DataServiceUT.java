package com.intuit.ipp.services;


import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.interceptors.IntuitMessage;
import com.intuit.ipp.interceptors.RequestElements;
import com.intuit.ipp.net.OperationType;
import junit.framework.Assert;

import mockit.Mock;
import mockit.MockUp;
import org.testng.annotations.Test;


/**
 * Created by amatiushkin on 8/27/15.
 */
public class DataServiceUT {

    @Test
    public void testVoidRequest() throws Exception
    {

        DataService mock = new  MockUp<DataService>() {
            @Mock(minInvocations = 1)
            public void executeInterceptors(IntuitMessage intuitMessage) {
                Assert.assertTrue(intuitMessage.getRequestElements().getRequestParameters().containsKey(RequestElements.REQ_PARAM_INCLUDE));
                Assert.assertTrue(intuitMessage.getRequestElements().getRequestParameters().containsValue(OperationType.VOID.toString()));
            }



        }.getMockInstance();
        mock.voidRequest(new Purchase());


    }
}
