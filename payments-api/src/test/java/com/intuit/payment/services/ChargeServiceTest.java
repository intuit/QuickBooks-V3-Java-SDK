/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.payment.services;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.Capture;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.Refund;
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

public class ChargeServiceTest {

    @Mocked
    private ServiceBase serviceBase;

    @Test
    public void testChargeServiceCreation() {
        ChargeService chargeService = new ChargeService();
        Assert.assertNull(chargeService.getRequestContext());
    }

    @Test
    public void testChargeServiceRequestContext() {
        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);
        Assert.assertEquals(requestContext, chargeService.getRequestContext());

        RequestContext secondRequestContext = new RequestContext();
        secondRequestContext.setBaseUrl("secondBaseUrl");
        chargeService.setRequestContext(secondRequestContext);
        Assert.assertEquals(secondRequestContext, chargeService.getRequestContext());
    }

    @Test
    public void testCreateCharge() throws BaseException {
        Charge expectedCharge = new Charge.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String chargeString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Charge charge = new Charge();
            Charge chargeGenerated = chargeService.create(charge);
            Assert.assertEquals(expectedCharge, chargeGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveCharge() throws BaseException {
        Charge expectedCharge = new Charge.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String chargeString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Charge chargeGenerated = chargeService.retrieve("chargeId");
            Assert.assertEquals(expectedCharge, chargeGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveChargeNullChargeId() {
        ChargeService chargeService = new ChargeService();
        try {
            chargeService.retrieve(null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.retrieve("");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundCharge() throws BaseException {
        Refund expectedRefund = new Refund.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String chargeString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Refund refund = new Refund.Builder().id("refundId").type("type").build();
            Refund refundGenerated = chargeService.refund("myChargeId", refund);
            Assert.assertEquals(expectedRefund, refundGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundNullChargeId() {
        ChargeService chargeService = new ChargeService();
        Refund refund = new Refund();
        try {
            chargeService.refund(null, refund);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.refund("", refund);
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
        final String chargeString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Refund refund = new Refund.Builder().id("refundId").type("type").build();
            Refund refundGenerated = chargeService.getRefund("myChargeId", "myRefundId");
            Assert.assertEquals(expectedRefund, refundGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundChargeNullCheckId() {
        ChargeService chargeService = new ChargeService();
        try {
            chargeService.getRefund(null, "refundid");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.getRefund("", "refundid");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRefundChargeNullRefundId() {
        ChargeService chargeService = new ChargeService();
        try {
            chargeService.getRefund("chargeId", null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.getRefund("chargeId", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testVoidTransaction() throws BaseException {
        Refund expectedRefund = new Refund.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String chargeString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Refund refundGenerated = chargeService.voidTransaction("chargeRequestId");
            Assert.assertEquals(expectedRefund, refundGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testVoidTransactionNullChargeRequestId() {
        ChargeService chargeService = new ChargeService();
        try {
            chargeService.voidTransaction(null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.voidTransaction("");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testCapture() throws BaseException {
        Charge expectedCharge = new Charge.Builder().id("ijids").amount(new BigDecimal("50000")).build();
        final String chargeString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, chargeString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        ChargeService chargeService = new ChargeService(requestContext);

        try {
            Capture capture = new Capture();
            Charge chargeGenerated = chargeService.capture("chargeId", capture);
            Assert.assertEquals(expectedCharge, chargeGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testCaptureNullChargeRequestId() {
        ChargeService chargeService = new ChargeService();
        try {
            chargeService.capture(null, null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            chargeService.capture("", null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }
}
