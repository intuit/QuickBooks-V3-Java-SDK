package com.intuit.ipp.util;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateUtilsTest {

	@Test
	public void testGetDateFromString_withMillisecondsZone() throws Exception {
		String str = "2013-01-17T01:36:25.606-08:00";
		Date c = DateUtils.getDateFromString(str);
		Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
	}
	
	@Test
	public void testGetDateFromString_withMillisecondsZone2() throws Exception {
		String str = "2013-01-17T01:36:25.606-0800";
		Date c = DateUtils.getDateFromString(str);
		Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
	}

    @Test
    public void testGetDateFromString_withoutMillisecondsZone1() throws Exception {
        String str = "2013-01-17T01:36:25-08:00";
        Date c = DateUtils.getDateFromString(str);
        Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
    }

    @Test
    public void testGetDateFromString_withoutMillisecondsZone2() throws Exception {
        String str = "2013-01-17T01:36:25-0800";
        Date c = DateUtils.getDateFromString(str);
        Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
    }

	@Test
	public void testGetDateFromString_withMillisecondsZ() throws Exception {
		String str = "2013-02-07T09:40:30.282Z";
		Date c = DateUtils.getDateFromString(str);
		Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
	}

    @Test
    public void testGetDateFromString_withoutMillisecondsZ() throws Exception {
        String str = "2013-02-07T09:40:30Z";
        Date c = DateUtils.getDateFromString(str);
        Assert.assertTrue(c.before(DateUtils.getCurrentDateTime()));
    }

        @Test
	public void testGetStringFromDate() throws Exception {
		String str = "2013-02-07T09:40:30.282Z";
		Date d = DateUtils.getDateFromString(str);
		String s = DateUtils.getStringFromDateTime(d);
		Assert.assertEquals(s, str);
	}
	
	@Test
	public void testGetCurrentDateTime() throws Exception {
		Date c = DateUtils.getCurrentDateTime();
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testGetCalendarWithNextDays() throws Exception {
		Date cNew = DateUtils.getDateWithNextDays(2);
		Date cNow = DateUtils.getCurrentDateTime();
		Assert.assertTrue(cNew.after(cNow));
	}
	
	@Test
	public void testGetCalendarWithPrevDays() throws Exception {
		Date cNew = DateUtils.getDateWithPrevDays(2);
		Date cNow = DateUtils.getCurrentDateTime();
		Assert.assertTrue(cNew.before(cNow));
	}
	
	@Test
	public void testDateUtils() {
		DateUtils dateUtils = DateUtils.getInstance();
		Assert.assertNotNull(dateUtils);
	}
}
