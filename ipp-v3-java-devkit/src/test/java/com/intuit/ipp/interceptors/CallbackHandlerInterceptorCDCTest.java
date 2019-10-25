package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.CDCQueryResult;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class CallbackHandlerInterceptorCDCTest extends CallbackHandlerBase {

    @Test
    public void  emptyListIsOk() throws FMSException {
        assertNull(invokeCDC(Collections.<CDCResponse>emptyList()));
    }

    @Test
    public void  singleWithResponse() throws FMSException {
        assertEmptyResult(invokeCDC(Collections.singletonList(new CDCResponse())));
    }

    @Test
    public void  singleResponseWithEmptyQuery() throws FMSException {
        final CDCResponse o = new CDCResponse();
        o.setQueryResponse(Collections.singletonList(new QueryResponse()));

        assertEmptyResult(invokeCDC(Collections.singletonList(o)));
    }

    @Test
    public void  singleEntityIsOk() throws FMSException {
        final CDCResponse response = new CDCResponse();
        final QueryResponse queryResponse = new QueryResponse();

        queryResponse.setIntuitObject(Collections.<JAXBElement<? extends IntuitEntity>>singletonList(getDummyTestEntity()));

        response.setQueryResponse(Collections.singletonList(queryResponse));

        new ResultChecker( assertAndGetFirst(invokeCDC(Collections.singletonList(response))))
            .assertQueryKeys("IntuitTestEntity");
    }


    /**
     * Illustrates https://github.com/intuit/QuickBooks-V3-Java-SDK/issues/75
     * Error is expected in result, but it gives empty result.
     *
     * @throws FMSException
     */
    @Test
    public void  errorNoResponse() throws FMSException {
        final CDCResponse o = new CDCResponse();
        final Fault fault = new Fault();
        final Error error = new Error();
        fault.setError(Collections.singletonList(error));
        error.setDetail("My custom request error");
        o.setFault(fault);
        o.setQueryResponse(null);

        new ResultChecker( assertAndGetFirst(invokeCDC(Collections.singletonList(o))))
                .assertErrorsDetails("My custom request error");
    }

    @Test
    public void  error() throws FMSException {
        final CDCResponse o = new CDCResponse();
        final QueryResponse queryResponse = new QueryResponse();
        final Fault fault = new Fault();
        final Error error = new Error();
        fault.setError(Collections.singletonList(error));
        error.setDetail("My custom error");
        queryResponse.setFault(fault);

        o.setQueryResponse(Collections.singletonList(queryResponse));
        new ResultChecker( assertAndGetFirst(invokeCDC(Collections.singletonList(o))))
                .assertErrorsDetails("My custom error");

    }

    @Test
    public void  partialError() throws FMSException {
        final CDCResponse o = new CDCResponse();
        final QueryResponse queryResponse = new QueryResponse();
        final Fault fault = new Fault();
        final Error error = new Error();
        fault.setError(Collections.singletonList(error));
        error.setDetail("My custom error");
        queryResponse.setFault(fault);
        queryResponse.setIntuitObject(Collections.<JAXBElement<? extends IntuitEntity>>singletonList(getDummyTestEntity()));

        o.setQueryResponse(Collections.singletonList(queryResponse));
        new ResultChecker( assertAndGetFirst(invokeCDC(Collections.singletonList(o))))
                .assertErrorsDetails("My custom error")
                .assertQueryKeys("IntuitTestEntity");
    }


    /**
     * Asserts that query result contains exactly one response without any fields set
     * @param results
     */
    private void assertEmptyResult(List<CDCQueryResult> results) {
       new ResultChecker(assertAndGetFirst(results)).assertEmpty();
    }

    /**
     * Asserts exactly single response and returns first {@link CDCQueryResult}
     * @param results
     * @return
     */
    private CDCQueryResult assertAndGetFirst(List<CDCQueryResult> results) {
        return new ListChecker<>(results)
                    .exactlyOne()
                    .first();
    }


    /**
     * Declaratively verifies an arbitrary list
     * @param <T>
     */
    static class ListChecker<T> {

        private List<T> it;

        public ListChecker(List<T> it) {
            this.it = it;
        }

        /**
         * Asserts that list has exactly one element
         * @return
         */
        ListChecker<T> exactlyOne() {
            assertNotNull(it);
            assertFalse(it.isEmpty());
            assertEquals(1, it.size());
            return this;
        }

        /** Returns first element from list
         *
         * @return
         */
        T first() {
            return it.get(0);
        }
    }

    /**
     * Holds verified object
     * @param <T>
     */
    static class Checker<T> {

        private T it;

        public Checker(T it) {
            this.it = it;
        }

        public T it() {
            return it;
        }
    }

    /**
     * Declaratively verifies result
     */
    private static class ResultChecker extends Checker<CDCQueryResult> {
        ResultChecker(CDCQueryResult it) {
            super(it);
        }

        /**
         * Asserts query result has no errors nor responses
         * @return
         */
        ResultChecker assertEmpty() {
            assertNull( it().getFalut());
            assertNull( it().getQueryResults());
            assertNull( it().getSize());
            return this;

        }

        /**
         * Asserts CDC Query Result to have exact number of {@link com.intuit.ipp.services.QueryResult}
         * using its key
         *
         * @param keys
         * @return
         */
        ResultChecker assertQueryKeys(String... keys) {
            assertEquals(Arrays.asList(keys), it().getQueryResults().keySet());
            return this;
        }

        /**
         * Asserts all error details in query result
         * @param details
         * @return
         */
        ResultChecker assertErrorsDetails(String... details) {
            List<String> actualDetails = new ArrayList<>();
            for(Error error : it().getFalut().getError()) {
                actualDetails.add(error.getDetail());
            }
            assertEquals(Arrays.asList(details), actualDetails);
            return this;
        }

    }


}
