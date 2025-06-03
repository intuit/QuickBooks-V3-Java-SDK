package com.intuit.payment.data;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wontonswaggie
 */
public class CaptureTest {
    private Date created;
    private BigDecimal amount;
    private PaymentContext context;
    private String description;
    private Capture capture;

    @BeforeTest
    public void init() {
        created = new Date();
        amount = new BigDecimal(20.00);
        context = new PaymentContext();
        description = "this is a capture test";
    }

    @BeforeMethod
    public void setUp() {
        capture = new Capture.Builder()
                .created(created)
                .amount(amount)
                .context(context)
                .description(description)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(capture.getCreated(), created);
        Assert.assertEquals(capture.getAmount(), amount);
        Assert.assertEquals(capture.getContext(), context);
        Assert.assertEquals(capture.getDescription(), description);
    }

    @Test
    public void testAllSetters() {
        Date newCreated = new Date();
        BigDecimal newAmount = new BigDecimal(30.00);
        PaymentContext newContext = new PaymentContext();
        String newDescription = "this is a new capture test";

        capture.setCreated(newCreated);
        capture.setAmount(newAmount);
        capture.setContext(newContext);
        capture.setDescription(newDescription);

        Assert.assertEquals(capture.getCreated(), newCreated);
        Assert.assertEquals(capture.getAmount(), newAmount);
        Assert.assertEquals(capture.getContext(), newContext);
        Assert.assertEquals(capture.getDescription(), newDescription);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(capture);
        String actualResult = capture.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
