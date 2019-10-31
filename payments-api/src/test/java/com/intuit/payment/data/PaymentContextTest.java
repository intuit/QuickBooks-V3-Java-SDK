package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class PaymentContextTest {
    private BigDecimal tax;
    private DeviceInfo deviceInfo;
    private Boolean recurring;
    private String mobile;
    private String isEcommerce;
    private Lodging lodging;
    private Restaurant restaurant;
    private PaymentContext paymentContext;

    @BeforeTest
    public void init() {
        tax = new BigDecimal(20.00);
        deviceInfo = new DeviceInfo();
        recurring = true;
        mobile = "android";
        isEcommerce = "ecommerce";
        lodging = new Lodging();
        restaurant = new Restaurant();
    }

    @BeforeMethod
    public void setUp() {
        paymentContext = new PaymentContext.Builder()
                        .tax(tax)
                        .deviceInfo(deviceInfo)
                        .recurring(recurring)
                        .mobile(mobile)
                        .isEcommerce(isEcommerce)
                        .lodging(lodging)
                        .restaurant(restaurant)
                        .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(paymentContext.getTax(), tax);
        Assert.assertEquals(paymentContext.getDeviceInfo(), deviceInfo);
        Assert.assertEquals(paymentContext.getRecurring(), recurring);
        Assert.assertEquals(paymentContext.getMobile(), mobile);
        Assert.assertEquals(paymentContext.getIsEcommerce(), isEcommerce);
        Assert.assertEquals(paymentContext.getLodging(), lodging);
        Assert.assertEquals(paymentContext.getRestaurant(), restaurant);
    }

    @Test
    public void testAllSetters() {
        BigDecimal newTax = new BigDecimal(40.00);
        DeviceInfo newDeviceInfo = new DeviceInfo();
        Boolean newRecurring = true;
        String newMobile = "ios";
        String newIsEcommerce = "new ecommerce";
        Lodging newLodging = new Lodging();
        Restaurant newRestaurant = new Restaurant();

        paymentContext.setTax(newTax);
        paymentContext.setDeviceInfo(newDeviceInfo);
        paymentContext.setRecurring(newRecurring);
        paymentContext.setMobile(newMobile);
        paymentContext.setIsEcommerce(newIsEcommerce);
        paymentContext.setLodging(newLodging);
        paymentContext.setRestaurant(newRestaurant);

        Assert.assertEquals(paymentContext.getTax(), newTax);
        Assert.assertEquals(paymentContext.getDeviceInfo(), newDeviceInfo);
        Assert.assertEquals(paymentContext.getRecurring(), newRecurring);
        Assert.assertEquals(paymentContext.getMobile(), newMobile);
        Assert.assertEquals(paymentContext.getIsEcommerce(), newIsEcommerce);
        Assert.assertEquals(paymentContext.getLodging(), newLodging);
        Assert.assertEquals(paymentContext.getRestaurant(), newRestaurant);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(paymentContext);
        String actualResult = paymentContext.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
