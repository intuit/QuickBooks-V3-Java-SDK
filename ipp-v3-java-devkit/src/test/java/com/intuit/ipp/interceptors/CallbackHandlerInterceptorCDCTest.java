package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.CDCQueryResult;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class CallbackHandlerInterceptorCDCTest extends CallbackHandlerBase {


    @Test
    public void  emptyListIsOk() throws FMSException {
        final List<CDCQueryResult> results = invokeCDC(Collections.<CDCResponse>emptyList());
        assertNull(results);
    }

    @Test
    public void  singleItemIsOk() throws FMSException {
        final List<CDCQueryResult> results = invokeCDC(Collections.singletonList(new CDCResponse()));

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
