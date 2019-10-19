package com.intuit.payment.data;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author k-arjun
 */
public class AddressTest {
    private String streetAddress;
    private String city;
    private String region;
    private String country;
    private String postalCode;

    @BeforeTest
    public void init() {
        streetAddress = "123 Test Street";
        city = "city";
        region = "region";
        country = "country";
        postalCode = "12345";
    }

    @Test
    public void testAllGetters() {
        Address address = new Address.Builder()
                .streetAddress(streetAddress)
                .city(city)
                .region(region)
                .country(country)
                .postalCode(postalCode)
                .build();

        Assert.assertEquals(address.getStreetAddress(), streetAddress);
        Assert.assertEquals(address.getCity(), city);
        Assert.assertEquals(address.getRegion(), region);
        Assert.assertEquals(address.getCountry(), country);
        Assert.assertEquals(address.getPostalCode(), postalCode);
    }
}