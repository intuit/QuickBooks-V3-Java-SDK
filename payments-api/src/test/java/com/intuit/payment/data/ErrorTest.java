package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ErrorTest {
    private String code;
    private String type;
    private String message;
    private String detail;
    private String moreInfo;
    private String infoLink;
    private Error error;

    @BeforeTest
    public void init() {
        code = "code";
        type = "type";
        message = "message";
        detail = "detail";
        moreInfo = "moreInfo";
        infoLink = "infoLink";
    }

    @BeforeMethod
    public void setUp() {
        error = new Error.Builder()
                .code(code)
                .type(type)
                .message(message)
                .detail(detail)
                .moreInfo(moreInfo)
                .infoLink(infoLink)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(error.getCode(), code);
        Assert.assertEquals(error.getType(), type);
        Assert.assertEquals(error.getMessage(), message);
        Assert.assertEquals(error.getDetail(), detail);
        Assert.assertEquals(error.getMoreInfo(), moreInfo);
        Assert.assertEquals(error.getInfoLink(), infoLink);
    }

    @Test
    public void testAllSetters() {
        String newCode = "newCode";
        String newType = "newType";
        String newMessage = "newMessage";
        String newDetail = "newDetail";
        String newMoreInfo = "newMoreInfo";
        String newInfoLink = "newInfoLink";

        error.setCode(newCode);
        error.setType(newType);
        error.setMessage(newMessage);
        error.setDetail(newDetail);
        error.setMoreInfo(newMoreInfo);
        error.setInfoLink(newInfoLink);

        Assert.assertEquals(error.getCode(), newCode);
        Assert.assertEquals(error.getType(), newType);
        Assert.assertEquals(error.getMessage(), newMessage);
        Assert.assertEquals(error.getDetail(), newDetail);
        Assert.assertEquals(error.getMoreInfo(), newMoreInfo);
        Assert.assertEquals(error.getInfoLink(), newInfoLink);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(error);
        String actualResult = error.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
