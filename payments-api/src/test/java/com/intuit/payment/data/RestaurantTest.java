package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author wontonswaggie
 */
public class RestaurantTest {
    private String serverID;
    private BigDecimal foodAmount;
    private BigDecimal beverageAmount;
    private BigDecimal taxAmount;
    private BigDecimal tipAmount;
    private Restaurant restaurant;

    @BeforeTest
    public void init() {
        serverID = "1234";
        foodAmount = new BigDecimal(100);
        beverageAmount = new BigDecimal(50);
        taxAmount = new BigDecimal(0.08);
        tipAmount = new BigDecimal(3);
    }

    @BeforeMethod
    public void setUp() {
        restaurant = new Restaurant.Builder()
                .serverID(serverID)
                .foodAmount(foodAmount)
                .beverageAmount(beverageAmount)
                .taxAmount(taxAmount)
                .tipAmount(tipAmount)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(restaurant.getServerID(), serverID);
        Assert.assertEquals(restaurant.getFoodAmount(), foodAmount);
        Assert.assertEquals(restaurant.getBeverageAmount(), beverageAmount);
        Assert.assertEquals(restaurant.getTaxAmount(), taxAmount);
        Assert.assertEquals(restaurant.getTipAmount(), tipAmount);
    }

    @Test
    public void testAllSetters() {
        String newServerID = "5678";
        BigDecimal newFoodAmount = new BigDecimal(200);
        BigDecimal newBeverageAmount = new BigDecimal(250);
        BigDecimal newTaxAmount = new BigDecimal(2.08);
        BigDecimal newTipAmount = new BigDecimal(23);

        restaurant.setServerID(newServerID);
        restaurant.setFoodAmount(newFoodAmount);
        restaurant.setBeverageAmount(newBeverageAmount);
        restaurant.setTaxAmount(newTaxAmount);
        restaurant.setTipAmount(newTipAmount);

        Assert.assertEquals(restaurant.getServerID(), newServerID);
        Assert.assertEquals(restaurant.getFoodAmount(), newFoodAmount);
        Assert.assertEquals(restaurant.getBeverageAmount(), newBeverageAmount);
        Assert.assertEquals(restaurant.getTaxAmount(), newTaxAmount);
        Assert.assertEquals(restaurant.getTipAmount(), newTipAmount);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(restaurant);
        String actualResult = restaurant.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
