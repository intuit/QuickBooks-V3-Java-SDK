package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author k-arjun, enzozafra
 */
public class AddressTest {
    private String streetAddress;
    private String city;
    private String region;
    private String country;
    private String postalCode;
    private Address address;

    @BeforeTest
    public void init() {
        streetAddress = "123 Test Street";
        city = "city";
        region = "region";
        country = "country";
        postalCode = "12345";
    }

    @BeforeMethod
    public void setUp() {
        address = new Address.Builder()
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .country(country)
                .postalCode(postalCode)
                .build();

    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(address.getStreetAddress(), streetAddress);
        Assert.assertEquals(address.getCity(), city);
        Assert.assertEquals(address.getRegion(), region);
        Assert.assertEquals(address.getCountry(), country);
        Assert.assertEquals(address.getPostalCode(), postalCode);
    }

    @Test
    public void testAllSetters() {
        String newStreetAddress = "321 New Street Address";
        String newCity = "New City";
        String newRegion = "New Region";
        String newCountry = "New Country";
        String newPostalCode = "54321";

        address.setStreetAddress(newStreetAddress);
        address.setCity(newCity);
        address.setRegion(newRegion);
        address.setCountry(newCountry);
        address.setPostalCode(newPostalCode);

        Assert.assertEquals(address.getStreetAddress(), newStreetAddress);
        Assert.assertEquals(address.getCity(), newCity);
        Assert.assertEquals(address.getRegion(), newRegion);
        Assert.assertEquals(address.getCountry(), newCountry);
        Assert.assertEquals(address.getPostalCode(), newPostalCode);
    }

    @Test
    public void testToString() {
        // Since we cant mock ReflectionToStringBuilder without powermock, just check if it includes below
        String expectedResult = ReflectionToStringBuilder.toString(address);
        String actualResult = address.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}