package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author rnair
 */
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
        tax = new BigDecimal(567.56);
        deviceInfo = new DeviceInfo();
        recurring = false;
        mobile = "mobile";
        isEcommerce = "false";
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
        Assert.assertEquals(paymentContext.getIsEcommerce(), isEcommerce);
        Assert.assertEquals(paymentContext.getMobile(), mobile);
        Assert.assertEquals(paymentContext.getLodging(), lodging);
        Assert.assertEquals(paymentContext.getRestaurant(), restaurant);
    }

    @Test
    public void testAllSetters() {
        BigDecimal updatedTax = new BigDecimal(4545.67);
        DeviceInfo updatedDeviceInfo = new DeviceInfo();
        Boolean udpatedRecurring = true;
        String udpatedMobile = "updated mobile";
        String udpatedIsECommerce = "true";
        Lodging udpatedLodging = new Lodging();
        Restaurant udpatedRestaurant = new Restaurant();

        paymentContext.setTax(updatedTax);
        paymentContext.setDeviceInfo(updatedDeviceInfo);
        paymentContext.setRecurring(udpatedRecurring);
        paymentContext.setMobile(udpatedMobile);
        paymentContext.setIsEcommerce(udpatedIsECommerce);
        paymentContext.setLodging(udpatedLodging);
        paymentContext.setRestaurant(udpatedRestaurant);

        Assert.assertEquals(paymentContext.getTax(), updatedTax);
        Assert.assertEquals(paymentContext.getDeviceInfo(), updatedDeviceInfo);
        Assert.assertEquals(paymentContext.getRecurring(), udpatedRecurring);
        Assert.assertEquals(paymentContext.getIsEcommerce(), udpatedIsECommerce);
        Assert.assertEquals(paymentContext.getMobile(), udpatedMobile);
        Assert.assertEquals(paymentContext.getLodging(), udpatedLodging);
        Assert.assertEquals(paymentContext.getRestaurant(), udpatedRestaurant);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(paymentContext);
        String actualResult = paymentContext.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}



