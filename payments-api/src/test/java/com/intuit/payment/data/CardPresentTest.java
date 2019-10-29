package com.intuit.payment.data;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CardPresentTest {
    private String track1 = null;
    private String track2 = null;
    private String ksn = null;
    private String pinBlock = null;

    @BeforeTest
    public void init() {
        track1 = "Track 1";
        track2 = "Track 2";
        ksn = "A08B000C0000002000E6";
        pinBlock = "2269CB453170331F";

    }

    @Test
    public void testAllGetters() {
        CardPresent cardPresent = new CardPresent.Builder()
                .track1(track1)
                .track2(track2)
                .ksn(ksn)
                .pinBlock(pinBlock)
                .build();

        Assert.assertEquals(cardPresent.getKsn(), ksn);
        Assert.assertEquals(cardPresent.getPinBlock(), pinBlock);
        Assert.assertEquals(cardPresent.getTrack1(), track1);
        Assert.assertEquals(cardPresent.getTrack2(), track2);
    }
}
