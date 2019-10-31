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
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.QueryResponse;
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
import java.util.ArrayList;
import java.util.List;

public class BankAccountServiceTest {

    @Mocked
    private ServiceBase serviceBase;

    @Test
    public void testBankAccountServiceCreation() {
        BankAccountService bankService = new BankAccountService();
        Assert.assertNull(bankService.getRequestContext());
    }

    @Test
    public void testBankAccountServiceRequestContext() {
        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankService = new BankAccountService(requestContext);
        Assert.assertEquals(requestContext, bankService.getRequestContext());

        RequestContext secondRequestContext = new RequestContext();
        secondRequestContext.setBaseUrl("secondBaseUrl");
        bankService.setRequestContext(secondRequestContext);
        Assert.assertEquals(secondRequestContext, bankService.getRequestContext());
    }

    @Test
    public void testCreateBankAccount() throws BaseException {
        BankAccount expectedBankAccount = new BankAccount.Builder().build();
        final String bankAccountString = JsonUtil.serialize(expectedBankAccount);
        final Response mockedResponse = new Response(200, bankAccountString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedBankAccount);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankService = new BankAccountService(requestContext);

        try {
            BankAccount bankAccount = new BankAccount();
            BankAccount bankAccountGenerated = bankService.create(bankAccount, "customerId");
            Assert.assertEquals(expectedBankAccount, bankAccountGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testCreateBankAccountNullBankAccountId() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.create(null, null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.create(null, "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }


    @Test
    public void testCreateFromTokenBankAccount() throws BaseException {
        BankAccount expectedBankAccount = new BankAccount.Builder().build();
        final String bankAccountGeneratedString = JsonUtil.serialize(expectedBankAccount);
        final Response mockedResponse = new Response(200, bankAccountGeneratedString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedBankAccount);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankService = new BankAccountService(requestContext);

        try {
            Token token = new Token();
            BankAccount bankAccountGenerated = bankService.createFromToken(token, "customerId");
            Assert.assertEquals(expectedBankAccount, bankAccountGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testCreateFromTokenBankAccountNullBankAccountId() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.createFromToken(null, null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.createFromToken(null, "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveBankAccount() throws BaseException {
        BankAccount expectedBankAccount = new BankAccount.Builder().id("ijids").accountNumber("accountId").build();
        final String bankAccountGeneratedString = JsonUtil.serialize(expectedBankAccount);
        final Response mockedResponse = new Response(200, bankAccountGeneratedString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedBankAccount);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankAccountService = new BankAccountService(requestContext);

        try {
            BankAccount bankAccount = bankAccountService.getBankAccount("customerId", "accountId");
            Assert.assertEquals(expectedBankAccount, bankAccount);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveBankAccountNullBankAccountId() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.getBankAccount(null, null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.getBankAccount("customerId", null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.getBankAccount("customerId", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.getBankAccount("", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveAllBankAccount() throws BaseException {
        List<BankAccount> bankAccounts = new ArrayList<>();
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountNumber("accountNumber1");
        bankAccounts.add(bankAccount1);
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountNumber("accountNumber2");
        bankAccounts.add(bankAccount2);
        QueryResponse expectedQueryResponse = new QueryResponse.Builder().bankAccounts(bankAccounts).build();
        final String bankAccountString = JsonUtil.serialize(expectedQueryResponse);
        final Response mockedResponse = new Response(200, bankAccountString, "fjkdlsfd");
        mockedResponse.setResponseObject(bankAccounts);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankAccountService = new BankAccountService(requestContext);

        try {
            QueryResponse queryResponse = bankAccountService.getAllBankAccounts("customerId");
            Assert.assertEquals(expectedQueryResponse.getBankAccounts(), bankAccounts);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveAllBankAccountNullBankAccountId() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.getAllBankAccounts(null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.getAllBankAccounts("");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }


    @Test
    public void testRetrieveAllBankAccountWithCount() throws BaseException {
        List<BankAccount> bankAccounts = new ArrayList<>();
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountNumber("accountNumber1");
        bankAccounts.add(bankAccount1);
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setAccountNumber("accountNumber2");
        bankAccounts.add(bankAccount2);
        QueryResponse expectedQueryResponse = new QueryResponse.Builder().bankAccounts(bankAccounts).build();
        final String bankAccountString = JsonUtil.serialize(expectedQueryResponse);
        final Response mockedResponse = new Response(200, bankAccountString, "fjkdlsfd");
        mockedResponse.setResponseObject(bankAccounts);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankAccountService = new BankAccountService(requestContext);

        try {
            QueryResponse queryResponse = bankAccountService.getAllBankAccounts("customerId", 2);
            Assert.assertEquals(expectedQueryResponse.getBankAccounts(), bankAccounts);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveAllBankAccountNullBankAccountIdWithCount() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.getAllBankAccounts(null, 0);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.getAllBankAccounts("", 0);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testDeleteBankAccount() throws BaseException {
        BankAccount expectedBankAccount = new BankAccount.Builder().accountNumber("accountId").build();
        final String bankAccountString = JsonUtil.serialize(expectedBankAccount);
        final Response mockedResponse = new Response(200, bankAccountString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedBankAccount);

        new Expectations() {{
            serviceBase.sendRequest((Request) any);
            result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        BankAccountService bankService = new BankAccountService(requestContext);

        try {
            BankAccount bankAccount = new BankAccount();
            BankAccount bankAccountGenerated = bankService.delete("customerId", "bankAccountId");
            // NOTE: No asserts required as a dummy response is being sent by delete API
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }


    @Test
    public void testDeleteBankAccountNullBankAccountId() {
        BankAccountService bankService = new BankAccountService();
        try {
            bankService.delete(null, null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.delete("customerId", null);
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.delete("customerId", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
        try {
            bankService.delete("", "");
            Assert.fail("Expected IllegalArgumentException thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true, "Expected IllegalArgumentException was thrown");
        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }
}
