package com.intuit.payment.services;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.Card;
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

public class TokenServiceTest {

    @Mocked
    private ServiceBase serviceBase;

    @Test
    public void testTokenServiceCreation() {
        TokenService tokenService = new TokenService();
        Assert.assertNull(tokenService.getRequestContext());
    }
    @Test
    public void testTokenServiceRequestContext() {
        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        TokenService tokenService = new TokenService(requestContext);
        Assert.assertEquals(requestContext, tokenService.getRequestContext());

        RequestContext secondRequestContext = new RequestContext();
        secondRequestContext.setBaseUrl("secondBaseUrl");
        tokenService.setRequestContext(secondRequestContext);
        Assert.assertEquals(secondRequestContext, tokenService.getRequestContext());

    }

    @Test
    public void testCreateToken() throws BaseException {
        Token expectedToken = new Token.Builder()
                .card(new Card.Builder().name("cardName").build())
                .bankAccount(new BankAccount.Builder().id("fakeID").build())
                .value("fake value")
                .build();
        final String tokenString = JsonUtil.serialize(expectedToken);
        final Response mockedResponse = new Response(200, tokenString, "fjkdlsfd");
        mockedResponse.setResponseObject(expectedToken);

        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        TokenService tokenService = new TokenService(requestContext);

        try {
            Token token = new Token();
            Token tokenGenerated = tokenService.createToken(token);
            Assert.assertEquals(expectedToken, tokenGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

    @Test
    public void testCreateTokenBadResponse() throws BaseException {
        final Response mockedResponse = new Response(500, "", "fjkdlsfd");
        new Expectations() {{
            serviceBase.sendRequest((Request) any); result = mockedResponse;
        }};

        RequestContext requestContext = new RequestContext();
        requestContext.setBaseUrl("fakeBaseUrl");
        TokenService tokenService = new TokenService(requestContext);

        try {
            Token token = new Token();
            Token tokenGenerated = tokenService.createToken(token);
            Assert.assertNull(tokenGenerated);

        } catch (BaseException e) {
            Assert.fail("Unexpected BaseException thrown " + e.getMessage());
        }
    }

}
