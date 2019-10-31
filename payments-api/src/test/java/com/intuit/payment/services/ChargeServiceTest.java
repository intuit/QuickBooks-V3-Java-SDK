/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
import com.intuit.payment.data.Card;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.DeviceInfo;
import com.intuit.payment.data.PaymentContext;
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
import java.util.Date;

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
        requestContext.setBaseUrl("SomeBaseUrl");

        ChargeService chargeService = new ChargeService(requestContext);
        Assert.assertEquals(requestContext, chargeService.getRequestContext());

        RequestContext secondRequestContext = new RequestContext();
        secondRequestContext.setBaseUrl("AnotherBaseUrl");
        chargeService.setRequestContext(secondRequestContext);
        Assert.assertEquals(secondRequestContext, chargeService.getRequestContext());
    }

    @Test
    public void testCreateCharge() throws BaseException {

        // Build response object
        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .currency("SomeCurrency")
                .card(new Card.Builder().name("SomeCardName").build())
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Charge chargeRequest = new Charge();
        Charge actualCharge = chargeService.create(chargeRequest);

        Assert.assertEquals(expectedCharge.getAmount(), actualCharge.getAmount());
        Assert.assertEquals(expectedCharge.getCurrency(), actualCharge.getCurrency());
        Assert.assertEquals(expectedCharge.getCard().getName(), actualCharge.getCard().getName());
    }

    @Test(expectedExceptions = BaseException.class)
    public void testCreateChargeServiceFailure() throws BaseException {

        // Build response object
        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .currency("SomeCurrency")
                .card(new Card.Builder().name("SomeCardName").build())
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = new BaseException("Unexpected Error , service response object was null ");
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Charge chargeRequest = new Charge();
        Charge actualCharge = chargeService.create(chargeRequest);  // Should throw exception
    }

    @Test
    public void testRetrieveCharge() throws BaseException {

        // Build response object
        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .currency("SomeCurrency")
                .card(new Card.Builder().name("SomeCardName").build())
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Charge actualCharge = chargeService.retrieve("SomeChargeId");

        Assert.assertEquals(expectedCharge.getAmount(), actualCharge.getAmount());
        Assert.assertEquals(expectedCharge.getCurrency(), actualCharge.getCurrency());
        Assert.assertEquals(expectedCharge.getCard().getName(), actualCharge.getCard().getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRetrieveChargeInvalidChargeId() throws BaseException {

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        // Pass in null ChargeId
        Charge actualCharge = chargeService.retrieve(null);
    }

    @Test
    public void testCaptureCharge() throws BaseException {

        // Build response object
        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .context(new PaymentContext.Builder().mobile("false").isEcommerce("true").build())
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedCharge);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedCharge);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Charge chargeRequest = new Charge();
        Capture captureChargeRequest = new Capture.Builder()
                .amount(expectedCharge.getAmount())
                .context(expectedCharge.getContext())
                .build();
        Charge actualCharge = chargeService.capture("SomeChargeId", captureChargeRequest);

        Assert.assertEquals(expectedCharge.getAmount(), actualCharge.getAmount());
        Assert.assertEquals(expectedCharge.getContext().getMobile(), actualCharge.getContext().getMobile());
        Assert.assertEquals(expectedCharge.getContext().getIsEcommerce(), actualCharge.getContext().getIsEcommerce());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCaptureChargeInvalidChargeId() throws BaseException {

        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .context(new PaymentContext.Builder().mobile("false").isEcommerce("true").build())
                .build();

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Capture captureChargeRequest = new Capture.Builder()
                .amount(expectedCharge.getAmount())
                .context(expectedCharge.getContext())
                .build();

        // Pass in null ChargeId
        Charge actualCharge = chargeService.capture(null, captureChargeRequest);
    }

    @Test
    public void testRefundCharge() throws BaseException {

        // Build response object
        Refund expectedRefund = new Refund.Builder()
                .amount(new BigDecimal(1234))
                .description("SomeDescription")
                .context(
                        new PaymentContext.Builder()
                                .tax(new BigDecimal(5678))
                                .recurring(true)
                                .deviceInfo(
                                        new DeviceInfo.Builder()
                                                .macAddress("SomeMacAddress")
                                                .ipAddress("SomeIpAddress")
                                                .longitude("SomeLongitude")
                                                .latitude("SomeLatitude")
                                                .phoneNumber("1800-FAKE-FAKE")
                                                .type("mobile")
                                                .build())
                                .build())
                .id("ThisIsAGeneratedRefundId")
                .created(new Date(System.currentTimeMillis()))
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Refund refundRequest = new Refund.Builder()
                .amount(expectedRefund.getAmount())
                .description(expectedRefund.getDescription())
                .context(expectedRefund.getContext())
                .build();

        Refund actualRefund = chargeService.refund("SomeChargeId", refundRequest);

        Assert.assertEquals(expectedRefund.getId(), actualRefund.getId());
        Assert.assertEquals(expectedRefund.getAmount(), actualRefund.getAmount());
        Assert.assertEquals(expectedRefund.getDescription(), actualRefund.getDescription());
        Assert.assertEquals(expectedRefund.getContext().getTax(), actualRefund.getContext().getTax());
        Assert.assertEquals(expectedRefund.getContext().getRecurring(), actualRefund.getContext().getRecurring());

        Assert.assertEquals(expectedRefund.getCreated(), actualRefund.getCreated());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRefundChargeInvalidChargeId() throws BaseException {

        Charge expectedCharge = new Charge.Builder()
                .amount(new BigDecimal(1234))
                .context(new PaymentContext.Builder().mobile("false").isEcommerce("true").build())
                .build();

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Capture captureChargeRequest = new Capture.Builder()
                .amount(expectedCharge.getAmount())
                .context(expectedCharge.getContext())
                .build();

        // Pass in null ChargeId
        Refund actualRefund = chargeService.refund(null, null);
    }

    @Test
    public void testGetRefund() throws BaseException {

        // Build response object
        Refund expectedRefund = new Refund.Builder()
                .amount(new BigDecimal(1234))
                .description("SomeDescription")
                .context(
                        new PaymentContext.Builder()
                                .tax(new BigDecimal(5678))
                                .recurring(true)
                                .deviceInfo(
                                        new DeviceInfo.Builder()
                                                .macAddress("SomeMacAddress")
                                                .ipAddress("SomeIpAddress")
                                                .longitude("SomeLongitude")
                                                .latitude("SomeLatitude")
                                                .phoneNumber("1800-FAKE-FAKE")
                                                .type("mobile")
                                                .build())
                                .build())
                .id("ThisIsAGeneratedRefundId")
                .created(new Date(System.currentTimeMillis()))
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Refund actualRefund = chargeService.getRefund("SomeChargeId", "SomeRefundId");

        Assert.assertEquals(expectedRefund.getId(), actualRefund.getId());
        Assert.assertEquals(expectedRefund.getAmount(), actualRefund.getAmount());
        Assert.assertEquals(expectedRefund.getDescription(), actualRefund.getDescription());
        Assert.assertEquals(expectedRefund.getContext().getTax(), actualRefund.getContext().getTax());
        Assert.assertEquals(expectedRefund.getContext().getRecurring(), actualRefund.getContext().getRecurring());

        Assert.assertEquals(expectedRefund.getCreated(), actualRefund.getCreated());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetRefundInvalidChargeId() throws BaseException {

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        // Pass in null ChargeId
        Refund actualRefund = chargeService.getRefund(null, "SomeRefundId");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetRefundInvalidRefundId() throws BaseException {

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        // Pass in null RefundId
        Refund actualRefund = chargeService.getRefund("SomeChargeId", null);
    }

    @Test
    public void testVoidTransaction() throws BaseException {

        // Build response object
        Refund expectedRefund = new Refund.Builder()
                .amount(new BigDecimal(1234))
                .description("SomeDescription")
                .context(
                        new PaymentContext.Builder()
                                .tax(new BigDecimal(5678))
                                .recurring(true)
                                .deviceInfo(
                                        new DeviceInfo.Builder()
                                                .macAddress("SomeMacAddress")
                                                .ipAddress("SomeIpAddress")
                                                .longitude("SomeLongitude")
                                                .latitude("SomeLatitude")
                                                .phoneNumber("1800-FAKE-FAKE")
                                                .type("mobile")
                                                .build())
                                .build())
                .id("ThisIsAGeneratedRefundId")
                .created(new Date(System.currentTimeMillis()))
                .build();

        // Serialize response object as JSON string
        final String serializedResponseString = JsonUtil.serialize(expectedRefund);
        final Response mockedResponse = new Response(200, serializedResponseString, "some_intuit_tid");
        mockedResponse.setResponseObject(expectedRefund);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        Refund actualRefund = chargeService.voidTransaction("SomeChargeRequestId");

        Assert.assertEquals(expectedRefund.getId(), actualRefund.getId());
        Assert.assertEquals(expectedRefund.getAmount(), actualRefund.getAmount());
        Assert.assertEquals(expectedRefund.getDescription(), actualRefund.getDescription());
        Assert.assertEquals(expectedRefund.getContext().getTax(), actualRefund.getContext().getTax());
        Assert.assertEquals(expectedRefund.getContext().getRecurring(), actualRefund.getContext().getRecurring());

        Assert.assertEquals(expectedRefund.getCreated(), actualRefund.getCreated());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testVoidTransactionInvalidChargeId() throws BaseException {

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("SomeBaseUrl");

        // Set mock RequestContext against ChargeService
        ChargeService chargeService = new ChargeService(requestContext);

        // Pass in null RefundId
        Refund actualRefund = chargeService.voidTransaction(null);
    }
}