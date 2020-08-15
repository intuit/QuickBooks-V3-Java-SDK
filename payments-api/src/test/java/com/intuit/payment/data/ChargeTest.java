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
package com.intuit.payment.data;

import com.intuit.payment.data.Charge.ChargeStatus;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class ChargeTest {

    private String id;
    private Date created;
    private ChargeStatus status;
    private BigDecimal amount;
    private String currency;
    private String token;
    private Card card;
    private PaymentContext context;
    private Boolean capture;
    private String authCode;
    private Capture captureDetail;
    private Refund[] refundDetail;
    private String description;
    private String avsStreet;
    private String avsZip;
    private String cardSecurityCodeMatch;
    private String appType;
    private String cardOnFile;

    private Charge charge;

    @BeforeTest
    public void init() {
        id = "id";
        created = new Date(1572473285);
        status = ChargeStatus.AUTHORIZED;
        amount = new BigDecimal(1);
        currency = "money";
        token = "token";
        card = new Card();
        context = new PaymentContext();
        capture = false;
        authCode = "123456";
        captureDetail = new Capture();
        refundDetail = new Refund[]{new Refund()};
        description = "description";
        avsStreet = "avsStreet";
        avsZip = "avsZip";
        cardSecurityCodeMatch = "cardSecurityCodeMatch";
        appType = "appType";
        cardOnFile = "cardOnFile";
    }

    @BeforeMethod
    public void setUpWithBuilder() {
        charge = new Charge.Builder()
            .id(id)
            .created(created)
            .status(status)
            .amount(amount)
            .currency(currency)
            .token(token)
            .card(card)
            .context(context)
            .capture(capture)
            .authCode(authCode)
            .captureDetail(captureDetail)
            .refundDetail(refundDetail)
            .description(description)
            .avsStreet(avsStreet)
            .avsZip(avsZip)
            .cardSecurityCodeMatch(cardSecurityCodeMatch)
            .appType(appType)
            .cardOnFile(cardOnFile)
            .build();
    }

    @Test
    public void testConstructor() {
        charge = new Charge();
        Assert.assertNotNull(charge);
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(charge.getId(), id);
        Assert.assertEquals(charge.getCreated(), created);
        Assert.assertEquals(charge.getStatus(), status);
        Assert.assertEquals(charge.getAmount(), amount);
        Assert.assertEquals(charge.getCurrency(), currency);
        Assert.assertEquals(charge.getToken(), token);
        Assert.assertEquals(charge.getCard(), card);
        Assert.assertEquals(charge.getContext(), context);
        Assert.assertEquals(charge.getCapture(), capture);
        Assert.assertEquals(charge.getAuthCode(), authCode);
        Assert.assertEquals(charge.getCaptureDetail(), captureDetail);
        Assert.assertEquals(charge.getRefundDetail(), refundDetail);
        Assert.assertEquals(charge.getDescription(), description);
        Assert.assertEquals(charge.getAvsStreet(), avsStreet);
        Assert.assertEquals(charge.getAvsZip(), avsZip);
        Assert.assertEquals(charge.getCardSecurityCodeMatch(), cardSecurityCodeMatch);
        Assert.assertEquals(charge.getAppType(), appType);
        Assert.assertEquals(charge.getCardOnFile(), cardOnFile);
    }

    @Test
    public void testAllSetters() {
        String newId = "id";
        Date newCreated = new Date(1572473285);
        ChargeStatus newStatus = ChargeStatus.AUTHORIZED;
        BigDecimal newAmount = new BigDecimal(1);
        String newCurrency = "money";
        String newToken = "token";
        Card newCard = new Card();
        PaymentContext newContext = new PaymentContext();
        Boolean newCapture = false;
        String newAuthCode = "123456";
        Capture newCaptureDetail = new Capture();
        Refund[] newRefundDetail = new Refund[]{new Refund()};
        String newDescription = "description";
        String newAvsStreet = "avsStreet";
        String newAvsZip = "avsZip";
        String newCardSecurityCodeMatch = "cardSecurityCodeMatch";
        String newAppType = "appType";
        String newCardOnFile = "cardOnFile";

        charge.setId(newId);
        charge.setCreated(newCreated);
        charge.setStatus(newStatus);
        charge.setAmount(newAmount);
        charge.setCurrency(newCurrency);
        charge.setToken(newToken);
        charge.setCard(newCard);
        charge.setContext(newContext);
        charge.setCapture(newCapture);
        charge.setAuthCode(newAuthCode);
        charge.setCaptureDetail(newCaptureDetail);
        charge.setRefundDetail(newRefundDetail);
        charge.setDescription(newDescription);
        charge.setAvsStreet(newAvsStreet);
        charge.setAvsZip(newAvsZip);
        charge.setCardSecurityCodeMatch(newCardSecurityCodeMatch);
        charge.setAppType(newAppType);
        charge.setCardOnFile(newCardOnFile);

        Assert.assertEquals(charge.getId(), newId);
        Assert.assertEquals(charge.getCreated(), newCreated);
        Assert.assertEquals(charge.getStatus(), newStatus);
        Assert.assertEquals(charge.getAmount(), newAmount);
        Assert.assertEquals(charge.getCurrency(), newCurrency);
        Assert.assertEquals(charge.getToken(), newToken);
        Assert.assertEquals(charge.getCard(), newCard);
        Assert.assertEquals(charge.getContext(), newContext);
        Assert.assertEquals(charge.getCapture(), newCapture);
        Assert.assertEquals(charge.getAuthCode(), newAuthCode);
        Assert.assertEquals(charge.getCaptureDetail(), newCaptureDetail);
        Assert.assertEquals(charge.getRefundDetail(), newRefundDetail);
        Assert.assertEquals(charge.getDescription(), newDescription);
        Assert.assertEquals(charge.getAvsStreet(), newAvsStreet);
        Assert.assertEquals(charge.getAvsZip(), newAvsZip);
        Assert.assertEquals(charge.getCardSecurityCodeMatch(), newCardSecurityCodeMatch);
        Assert.assertEquals(charge.getAppType(), newAppType);
        Assert.assertEquals(charge.getCardOnFile(), newCardOnFile);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(charge);
        String actualResult = charge.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
