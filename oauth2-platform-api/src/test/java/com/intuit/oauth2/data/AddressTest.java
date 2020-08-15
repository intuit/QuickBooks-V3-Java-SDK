package com.intuit.oauth2.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author enzozafra
 */
public class AddressTest {
    private Address address;

    private String streetAddress;
    private String locality;
    private String region;
    private String postalCode;
    private String country;

    @BeforeTest
    public void init() {
        streetAddress = "123 Test Street";
        locality = "locality";
        region = "region";
        country = "country";
        postalCode = "12345";
    }

    @BeforeMethod
    public void setUp() {
        address = new Address();
        address.setStreetAddress(streetAddress);
        address.setLocality(locality);
        address.setRegion(region);
        address.setCountry(country);
        address.setPostalCode(postalCode);
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(address.getStreetAddress(), streetAddress);
        Assert.assertEquals(address.getLocality(), locality);
        Assert.assertEquals(address.getRegion(), region);
        Assert.assertEquals(address.getCountry(), country);
        Assert.assertEquals(address.getPostalCode(), postalCode);
    }

    @Test
    public void testAllSetters() {
        String newStreetAddress = "321 New Street Address";
        String newLocality = "New Locality";
        String newRegion = "New Region";
        String newCountry = "New Country";
        String newPostalCode = "54321";

        address.setStreetAddress(newStreetAddress);
        address.setLocality(newLocality);
        address.setRegion(newRegion);
        address.setCountry(newCountry);
        address.setPostalCode(newPostalCode);

        Assert.assertEquals(address.getStreetAddress(), newStreetAddress);
        Assert.assertEquals(address.getLocality(), newLocality);
        Assert.assertEquals(address.getRegion(), newRegion);
        Assert.assertEquals(address.getCountry(), newCountry);
        Assert.assertEquals(address.getPostalCode(), newPostalCode);
    }
}