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

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tfung
 */
public class ECheckTest {
    private String id;
    private Date created;
    private String authCode;
    private ECheck.ECheckStatus status;
    private BigDecimal amount;
    private BankAccount bankAccount;
    private String token;
    private String bankAccountOnFile;
    private CheckContext context;
    private ECheck.PaymentModeType paymentMode;
    private String description;
    private String checkNumber;

    private ECheck echeck;

    @BeforeTest
    public void init() {
        id = "id";
        created = new Date(01/31/2019);
        authCode = "authCode";
        status = ECheck.ECheckStatus.SUCCEEDED;
        amount = new BigDecimal(100000);
        token = "token";
        bankAccountOnFile = "bankAccountOnFile";
        context = new CheckContext();
        paymentMode = ECheck.PaymentModeType.WEB;
        description = "description";
        checkNumber = "checkNumber";

        bankAccount = new BankAccount.Builder()
            .id("bankAccountId")
            .build();
    }

    @BeforeMethod
    public void setUp() {
        echeck = new ECheck.Builder()
                .id(id)
                .created(created)
                .authCode(authCode)
                .status(status)
                .amount(amount)
                .bankAccount(bankAccount)
                .token(token)
                .bankAccountOnFile(bankAccountOnFile)
                .context(context)
                .paymentMode(paymentMode)
                .description(description)
                .checkNumber(checkNumber)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(echeck.getId(), id);
        Assert.assertEquals(echeck.getCreated(), created);
        Assert.assertEquals(echeck.getAuthCode(), authCode);
        Assert.assertEquals(echeck.getStatus(), status);
        Assert.assertEquals(echeck.getAmount(), amount);
        Assert.assertEquals(echeck.getBankAccount(), bankAccount);
        Assert.assertEquals(echeck.getToken(), token);
        Assert.assertEquals(echeck.getBankAccountOnFile(), bankAccountOnFile);
        Assert.assertEquals(echeck.getContext(), context);
        Assert.assertEquals(echeck.getPaymentMode(), paymentMode);
        Assert.assertEquals(echeck.getDescription(), description);
        Assert.assertEquals(echeck.getCheckNumber(), checkNumber);
    }

    @Test
    public void testAllSetters() {
        String newId = "new id";
        Date newCreated = new Date(02/28/2019);
        String newAuthCode = "new authCode";
        ECheck.ECheckStatus newStatus = ECheck.ECheckStatus.DECLINED;
        BigDecimal newAmount = new BigDecimal(200000);
        String newToken = "new token";
        String newBankAccountOnFile = "new bankAccountOnFile";
        ECheck.PaymentModeType newPaymentMode = null;
        String newDescription = "new description";
        String newCheckNumber = "new checkNumber";

        CheckContext newContext = new CheckContext();
        newContext.setRequestId("requestId");

        BankAccount newBankAccount = new BankAccount.Builder()
                .id("bankAccountId 2")
                .build();

        echeck.setId(newId);
        echeck.setCreated(newCreated);
        echeck.setAuthCode(newAuthCode);
        echeck.setStatus(newStatus);
        echeck.setAmount(newAmount);
        echeck.setBankAccount(newBankAccount);
        echeck.setToken(newToken);
        echeck.setBankAccountOnFile(newBankAccountOnFile);
        echeck.setContext(newContext);
        echeck.setPaymentMode(newPaymentMode);
        echeck.setDescription(newDescription);
        echeck.setCheckNumber(newCheckNumber);

        Assert.assertEquals(echeck.getId(), newId);
        Assert.assertEquals(echeck.getCreated(), newCreated);
        Assert.assertEquals(echeck.getAuthCode(), newAuthCode);
        Assert.assertEquals(echeck.getStatus(), newStatus);
        Assert.assertEquals(echeck.getAmount(), newAmount);
        Assert.assertEquals(echeck.getBankAccount(), newBankAccount);
        Assert.assertEquals(echeck.getToken(), newToken);
        Assert.assertEquals(echeck.getBankAccountOnFile(), newBankAccountOnFile);
        Assert.assertEquals(echeck.getContext(), newContext);
        Assert.assertEquals(echeck.getPaymentMode(), newPaymentMode);
        Assert.assertEquals(echeck.getDescription(), newDescription);
        Assert.assertEquals(echeck.getCheckNumber(), newCheckNumber);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(echeck);
        String actualResult = echeck.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
