package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author pwaral
 */
public class LodgingTest {


    private String folioID;
    private String chargeType;
    private Date checkInDate;
    private Date checkOutDate;
    private String lengthOfStay;
    private BigDecimal roomRate;
    private String[] extraCharges;
    private String specialProgram;
    private BigDecimal totalAuthAmount;

    private Lodging lodging;


    @BeforeTest
    public void init() {
        folioID = "456";
        chargeType = "charge type";
        checkInDate = new Date(10/29/2019);
        checkOutDate = new Date(10/302019);
        lengthOfStay = "15";
        roomRate = new BigDecimal(245.65);
        extraCharges = new String[]{"34", "45", "55"};
        specialProgram = "monthly";
        totalAuthAmount = new BigDecimal(56.34);
    }

    @BeforeMethod
    public void setUp() {
        lodging = new Lodging.Builder()
                .folioID(folioID)
                .chargeType(chargeType)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .lengthOfStay(lengthOfStay)
                .roomRate(roomRate)
                .extraCharges(extraCharges)
                .specialProgram(specialProgram)
                .totalAuthAmount(totalAuthAmount)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(lodging.getFolioID(), folioID);
        Assert.assertEquals(lodging.getChargeType(), chargeType);
        Assert.assertEquals(lodging.getCheckInDate(), checkInDate);
        Assert.assertEquals(lodging.getLengthOfStay(), lengthOfStay);
        Assert.assertEquals(lodging.getRoomRate(), roomRate);
        Assert.assertEquals(lodging.getExtraCharges(), extraCharges);
        Assert.assertEquals(lodging.getSpecialProgram(), specialProgram);
        Assert.assertEquals(lodging.getTotalAuthAmount(), totalAuthAmount);
    }

    @Test
    public void testAllSetters() {
         String updatedFolioID = "123";
         String udpatedChargeType = "new charge type";
         Date udpatedCheckInDate = new Date(10/28/2019);
         Date udpatedCheckOutDate = new Date(11/27/2019);
         String udpatedLengthOfStay = "12";
         BigDecimal udpatedRoomRate = new BigDecimal(34.12);
         String[] udpatedExtraCharges = new String[]{"12", "76","78"};
         String udpatedSpecialProgram = "annual";
         BigDecimal udpatedTotalAuthAmount = new BigDecimal(1000.23);

        lodging.setFolioID(updatedFolioID);
        lodging.setChargeType(udpatedChargeType);
        lodging.setCheckInDate(udpatedCheckInDate);
        lodging.setCheckOutDate(udpatedCheckOutDate);
        lodging.setLengthOfStay(udpatedLengthOfStay);
        lodging.setRoomRate(udpatedRoomRate);
        lodging.setExtraCharges(udpatedExtraCharges);
        lodging.setSpecialProgram(udpatedSpecialProgram);
        lodging.setTotalAuthAmount(udpatedTotalAuthAmount);



        Assert.assertEquals(lodging.getFolioID(), updatedFolioID);
        Assert.assertEquals(lodging.getChargeType(), udpatedChargeType);
        Assert.assertEquals(lodging.getCheckInDate(), udpatedCheckInDate);
        Assert.assertEquals(lodging.getCheckOutDate(), udpatedCheckOutDate);
        Assert.assertEquals(lodging.getLengthOfStay(), udpatedLengthOfStay);
        Assert.assertEquals(lodging.getRoomRate(), udpatedRoomRate);
        Assert.assertEquals(lodging.getExtraCharges(), udpatedExtraCharges);
        Assert.assertEquals(lodging.getSpecialProgram(),udpatedSpecialProgram);
        Assert.assertEquals(lodging.getTotalAuthAmount(), udpatedTotalAuthAmount);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(lodging);
        String actualResult = lodging.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
