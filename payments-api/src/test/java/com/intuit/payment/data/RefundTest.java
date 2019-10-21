package com.intuit.payment.data;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class RefundTest {

    private String id = null;
    private Date created = null;
    private Refund.RefundStatus status = null;
    private BigDecimal amount = null;
    private PaymentContext context = null;
    private String description = null;
    private String type = null;

    @BeforeTest
    public void init() {
        id = "123";
        created = new Date("12-jan-2019");
        status = Refund.RefundStatus.DECLINED;
        amount = new BigDecimal("1234567");
        context = new PaymentContext();
        description = description;
        type = type;

    }

    @Test
    public void testAllGetters() {
        Refund refund = new Refund.Builder()
                .id(id)
                .created(created)
                .status(status)
                .amount(amount)
                .context(context).description(description).type(type)
                .build();

        Assert.assertEquals(refund.getAmount(), amount);
        Assert.assertEquals(refund.getContext(), context);
        Assert.assertEquals(refund.getCreated(), created);
        Assert.assertEquals(refund.getDescription(), description);
        Assert.assertEquals(refund.getId(), id);
        Assert.assertEquals(refund.getStatus(), status);
        Assert.assertEquals(refund.getType(), type);

    }
}
