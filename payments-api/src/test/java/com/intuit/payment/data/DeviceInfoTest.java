package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author jackykc
 */
public class DeviceInfoTest {

    private String id;
    private String type;
    private String longitude;
    private String latitude;
    private Boolean encrypted;
    private String phoneNumber;
    private String macAddress;
    private String ipAddress;

    private DeviceInfo deviceInfo;

    @BeforeTest
    public void init() {
        id = "id";
        type = "type";
        longitude = "longitude";
        latitude = "latitude";
        encrypted = false;
        phoneNumber = "5871231234";
        macAddress = "00-11-11-11-11-11";
        ipAddress = "192.168.0.0";
    }

    @BeforeMethod
    public void setUp() {
        deviceInfo = new DeviceInfo.Builder()
                .id(id)
                .type(type)
                .longitude(longitude)
                .latitude(latitude)
                .encrypted(encrypted)
                .phoneNumber(phoneNumber)
                .macAddress(macAddress)
                .ipAddress(ipAddress)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(deviceInfo.getId(), id);
        Assert.assertEquals(deviceInfo.getType(), type);
        Assert.assertEquals(deviceInfo.getLongitude(), longitude);
        Assert.assertEquals(deviceInfo.getLatitude(), latitude);
        Assert.assertEquals(deviceInfo.getEncrypted(), encrypted);
        Assert.assertEquals(deviceInfo.getPhoneNumber(), phoneNumber);
        Assert.assertEquals(deviceInfo.getMacAddress(), macAddress);
        Assert.assertEquals(deviceInfo.getIpAddress(), ipAddress);
    }

    @Test
    public void testAllSetters() {
        String newId = "new id";
        String newType = "new type";
        String newLongitude = "new longitude";
        String newLatitude = "new latitude";
        Boolean newEncrypted = true;
        String newPhoneNumber = "7801231234";
        String newMacAddress = "00-12-12-12-12-12";
        String newIpAddress = "255.168.0.0";

        deviceInfo.setId(newId);
        deviceInfo.setType(newType);
        deviceInfo.setLongitude(newLongitude);
        deviceInfo.setLatitude(newLatitude);
        deviceInfo.setEncrypted(newEncrypted);
        deviceInfo.setPhoneNumber(newPhoneNumber);
        deviceInfo.setMacAddress(newMacAddress);
        deviceInfo.setIpAddress(newIpAddress);

        Assert.assertEquals(deviceInfo.getId(), newId);
        Assert.assertEquals(deviceInfo.getType(), newType);
        Assert.assertEquals(deviceInfo.getLongitude(), newLongitude);
        Assert.assertEquals(deviceInfo.getLatitude(), newLatitude);
        Assert.assertEquals(deviceInfo.getEncrypted(), newEncrypted);
        Assert.assertEquals(deviceInfo.getPhoneNumber(), newPhoneNumber);
        Assert.assertEquals(deviceInfo.getMacAddress(), newMacAddress);
        Assert.assertEquals(deviceInfo.getIpAddress(), newIpAddress);
    }

    @Test
    public void testToString() {
        // Since we cant mock ReflectionToStringBuilder without powermock
        String expectedResult = ReflectionToStringBuilder.toString(deviceInfo);
        String actualResult = deviceInfo.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
