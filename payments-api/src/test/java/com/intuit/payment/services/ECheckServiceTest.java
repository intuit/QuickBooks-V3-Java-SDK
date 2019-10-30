package com.intuit.payment.services;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.ECheck;
import com.intuit.payment.data.Refund;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.JsonUtil;
import mockit.Expectations;
import mockit.Mocked;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ECheckServiceTest {

    @Mocked
    private ServiceBase serviceBase;

    @Test
    public void testECheckServiceCreation() {
        ECheckService eCheckService = new ECheckService();
        Assert.assertNull(eCheckService.getRequestContext());
    }
    @Test
    public void testECheckServiceRequestContext() {
        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ECheckService eCheckService = new ECheckService(requestContext);
        Assert.assertEquals(requestContext, eCheckService.getRequestContext());

        RequestContext secondRequestContext = new RequestContext();
        secondRequestContext.setBaseUrl("secondBaseUrl");
        eCheckService.setRequestContext(secondRequestContext);
        Assert.assertEquals(secondRequestContext, eCheckService.getRequestContext());
    }

    @Test
    public void testCreateECheck() throws BaseException {
        ECheck expectedECheck = new ECheck.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String eCheckString = JsonUtil.serialize(expectedECheck);
        final Response mockedResponse = new Response(200, eCheckString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedECheck);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ECheckService eCheckService = new ECheckService(requestContext);

        try {
            ECheck eCheck = new ECheck();
            ECheck eCheckGenerated = eCheckService.create(eCheck);
            Assert.assertEquals(expectedECheck, eCheckGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveECheck() throws BaseException {
        ECheck expectedECheck = new ECheck.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String eCheckString = JsonUtil.serialize(expectedECheck);
        final Response mockedResponse = new Response(200, eCheckString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedECheck);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ECheckService eCheckService = new ECheckService(requestContext);

        try {
            ECheck eCheckGenerated = eCheckService.retrieve("myECheckId");
            Assert.assertEquals(expectedECheck, eCheckGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveECheckNullECheckId() {
        ECheckService eCheckService = new ECheckService();
        try {
            eCheckService.retrieve(null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            eCheckService.retrieve("");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundECheck() throws BaseException {
        Refund expectedRefund = new Refund.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String eCheckString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, eCheckString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ECheckService eCheckService = new ECheckService(requestContext);

        try {
            Refund refund = new Refund.Builder().id("refundId").type("type").build();
            Refund refundGenerated = eCheckService.refund("myECheckId", refund);
            Assert.assertEquals(expectedRefund, refundGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundNullECheckId() {
        ECheckService eCheckService = new ECheckService();
        Refund refund = new Refund();
        try {
            eCheckService.refund(null, refund);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            eCheckService.refund("", refund);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testGetRefund() throws BaseException {
        Refund expectedRefund = new Refund.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String eCheckString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, eCheckString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ECheckService eCheckService = new ECheckService(requestContext);

        try {
            Refund refund = new Refund.Builder().id("refundId").type("type").build();
            Refund refundGenerated = eCheckService.getRefund("myECheckId", "myRefundId");
            Assert.assertEquals(expectedRefund, refundGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundECheckNullCheckId() {
        ECheckService eCheckService = new ECheckService();
        try {
            eCheckService.getRefund(null, "refundid");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            eCheckService.getRefund("", "refundid");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundECheckNullRefundId() {
        ECheckService eCheckService = new ECheckService();
        try {
            eCheckService.getRefund("echeckid", null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            eCheckService.getRefund("echeckid", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }


}
