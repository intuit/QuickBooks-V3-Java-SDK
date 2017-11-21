/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.query;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;
import static com.intuit.ipp.query.GenerateQuery.selectCount;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ipp.data.EntityStatusEnum;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.query.Data;

public class QueryTest {

	private static final org.slf4j.Logger LOG = Logger.getLogger();

	private Calendar calendar;

	private String dateString;
	
	@BeforeTest
	public void setCalendar() {
		calendar = Calendar.getInstance();
		calendar.set(2012, 8, 7, 1, 30, 30);
		dateString = getCalendarAsString(calendar);
	}

	@Test
	public void testQuery_datatypes() {
		Data data = GenerateQuery.createQueryEntity(new Data());
		String query = select($(data.getStringData()), $(data.getIntData()), $(data.getByteData()), $(data.getShortData()), $(data.getLongData()),
				$(data.getFloatData()), $(data.getDoubleData()), $(data.getCalendarData()), $(data.isBooleanData()), $(data.getDateData()),
				$(data.getEnumData())).where($(data.getStringData()).eq("StringValue"), $(data.getIntData()).eq(10),
				$(data.getByteData()).eq((byte) 10), $(data.getShortData()).eq((short) 10), $(data.getLongData()).eq((long) 10),
				$(data.getFloatData()).eq((float) 10), $(data.getDoubleData()).eq((double) 10), $(data.getCalendarData()).eq(calendar),
				$(data.isBooleanData()).eq(true), $(data.getDateData()).eq(calendar.getTime()), $(data.getEnumData()).eq(EntityStatusEnum.PENDING))
				.generate();
		String expectedQuery = "SELECT StringData, IntData, ByteData, ShortData, LongData, FloatData, DoubleData, CalendarData, BooleanData, DateData, EnumData FROM Data WHERE StringData = 'StringValue' AND IntData = '10' AND ByteData = '10' AND ShortData = '10' AND LongData = '10' AND FloatData = '10.0' AND DoubleData = '10.0' AND CalendarData = '" + dateString + "' AND BooleanData = true AND DateData = '" + dateString + "' AND EnumData = 'Pending'";
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_eq() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).eq("StringValue"), $(data.getIntData()).eq(10),
				$(data.getByteData()).eq((byte) 10), $(data.getShortData()).eq((short) 10), $(data.getLongData()).eq((long) 10),
				$(data.getFloatData()).eq((float) 10), $(data.getDoubleData()).eq((double) 10), $(data.getCalendarData()).eq(calendar),
				$(data.isBooleanData()).eq(true), $(data.getDateData()).eq(calendar.getTime()), $(data.getEnumData()).eq(EntityStatusEnum.PENDING))
				.generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData = 'StringValue' AND IntData = '10' AND ByteData = '10' AND ShortData = '10' AND LongData = '10' AND FloatData = '10.0' AND DoubleData = '10.0' AND CalendarData = '" + dateString + "' AND BooleanData = true AND DateData = '" + dateString + "' AND EnumData = 'Pending'";

		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_neq() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data))
				.where($(data.getStringData()).neq("StringValue"), $(data.getIntData()).neq(10), $(data.getByteData()).neq((byte) 10),
						$(data.getShortData()).neq((short) 10), $(data.getLongData()).neq((long) 10), $(data.getFloatData()).neq((float) 10),
						$(data.getDoubleData()).neq((double) 10), $(data.getCalendarData()).neq(calendar), $(data.isBooleanData()).neq(true),
						$(data.getDateData()).neq(calendar.getTime()), $(data.getEnumData()).neq(EntityStatusEnum.PENDING)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData != 'StringValue' AND IntData != '10' AND ByteData != '10' AND ShortData != '10' AND LongData != '10' AND FloatData != '10.0' AND DoubleData != '10.0' AND CalendarData != '" + dateString + "' AND BooleanData != true AND DateData != '" + dateString + "' AND EnumData != 'Pending'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_lt() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).lt("StringValue"), $(data.getIntData()).lt(10),
				$(data.getByteData()).lt((byte) 10), $(data.getShortData()).lt((short) 10), $(data.getLongData()).lt((long) 10),
				$(data.getFloatData()).lt((float) 10), $(data.getDoubleData()).lt((double) 10), $(data.getCalendarData()).lt(calendar),
				$(data.getDateData()).lt(calendar.getTime()), $(data.getEnumData()).lt(EntityStatusEnum.PENDING)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData < 'StringValue' AND IntData < '10' AND ByteData < '10' AND ShortData < '10' AND LongData < '10' AND FloatData < '10.0' AND DoubleData < '10.0' AND CalendarData < '" + dateString + "' AND DateData < '" + dateString + "' AND EnumData < 'Pending'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_lte() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).lte("StringValue"), $(data.getIntData()).lte(10),
				$(data.getByteData()).lte((byte) 10), $(data.getShortData()).lte((short) 10), $(data.getLongData()).lte((long) 10),
				$(data.getFloatData()).lte((float) 10), $(data.getDoubleData()).lte((double) 10), $(data.getCalendarData()).lte(calendar),
				$(data.getDateData()).lte(calendar.getTime()), $(data.getEnumData()).lte(EntityStatusEnum.PENDING)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData <= 'StringValue' AND IntData <= '10' AND ByteData <= '10' AND ShortData <= '10' AND LongData <= '10' AND FloatData <= '10.0' AND DoubleData <= '10.0' AND CalendarData <= '" + dateString + "' AND DateData <= '" + dateString + "' AND EnumData <= 'Pending'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_gt() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).gt("StringValue"), $(data.getIntData()).gt(10),
				$(data.getByteData()).gt((byte) 10), $(data.getShortData()).gt((short) 10), $(data.getLongData()).gt((long) 10),
				$(data.getFloatData()).gt((float) 10), $(data.getDoubleData()).gt((double) 10), $(data.getCalendarData()).gt(calendar),
				$(data.getDateData()).gt(calendar.getTime()), $(data.getEnumData()).gt(EntityStatusEnum.PENDING)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData > 'StringValue' AND IntData > '10' AND ByteData > '10' AND ShortData > '10' AND LongData > '10' AND FloatData > '10.0' AND DoubleData > '10.0' AND CalendarData > '" + dateString + "' AND DateData > '" + dateString + "' AND EnumData > 'Pending'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_gte() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).gte("StringValue"), $(data.getIntData()).gte(10),
				$(data.getByteData()).gte((byte) 10), $(data.getShortData()).gte((short) 10), $(data.getLongData()).gte((long) 10),
				$(data.getFloatData()).gte((float) 10), $(data.getDoubleData()).gte((double) 10), $(data.getCalendarData()).gte(calendar),
				$(data.getDateData()).gte(calendar.getTime()), $(data.getEnumData()).gte(EntityStatusEnum.PENDING)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData >= 'StringValue' AND IntData >= '10' AND ByteData >= '10' AND ShortData >= '10' AND LongData >= '10' AND FloatData >= '10.0' AND DoubleData >= '10.0' AND CalendarData >= '" + dateString + "' AND DateData >= '" + dateString + "' AND EnumData >= 'Pending'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_in() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).in(new String[] { "StringValue1", "StringValue2" }),
				$(data.getIntData()).in(new Integer[] { 10, 20 }), $(data.getByteData()).in(new Byte[] { 10, 20 }),
				$(data.getShortData()).in(new Short[] { 10, 20 }), $(data.getLongData()).in(new Long[] { (long) 10.0, (long) 20.0 }),
				$(data.getFloatData()).in(new Float[] { 10.0f, 20.0f }), $(data.getDoubleData()).in(new Double[] { 10.0, 20.0 }),
				$(data.isBooleanData()).in(new Boolean[] { true, false }), $(data.getCalendarData()).in(new Calendar[] { calendar, calendar }),
				$(data.getDateData()).in(new Date[] { calendar.getTime(), calendar.getTime() }),
				$(data.getEnumData()).in(new EntityStatusEnum[] { EntityStatusEnum.PENDING, EntityStatusEnum.DELETED })).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData IN ('StringValue1', 'StringValue2') AND IntData IN ('10', '20') AND ByteData IN ('10', '20') AND ShortData IN ('10', '20') AND LongData IN ('10', '20') AND FloatData IN ('10.0', '20.0') AND DoubleData IN ('10.0', '20.0') AND BooleanData IN (true, false) AND CalendarData IN ('" + dateString + "', '" + dateString + "') AND DateData IN ('" + dateString + "', '" + dateString + "') AND EnumData IN ('Pending', 'Deleted')";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_between() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).between("StringValue1", "StringValue2"), $(data.getIntData()).between(10, 20),
				$(data.getByteData()).between((byte) 10, (byte) 20), $(data.getShortData()).between((short) 10, (short) 20),
				$(data.getLongData()).between((long) 10, (long) 20), $(data.getFloatData()).between((float) 10, (float) 20),
				$(data.getDoubleData()).between((double) 10, (double) 20), $(data.getCalendarData()).between(calendar, calendar),
				$(data.getDateData()).between(calendar.getTime(), calendar.getTime())).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData BETWEEN 'StringValue1' AND 'StringValue2' AND IntData BETWEEN '10' AND '20' AND ByteData BETWEEN '10' AND '20' AND ShortData BETWEEN '10' AND '20' AND LongData BETWEEN '10' AND '20' AND FloatData BETWEEN '10.0' AND '20.0' AND DoubleData BETWEEN '10.0' AND '20.0' AND CalendarData BETWEEN '" + dateString + "Z' AND '" + dateString + "' AND DateData BETWEEN '" + dateString + "' AND '" + dateString.substring(0, dateString.length() - 1) + "'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_like() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getStringData()).startsWith("StringValue")).generate();
		String expectedQuery = "SELECT * FROM Data WHERE StringData LIKE 'StringValue%'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);

		query = select($(data)).where($(data.getStringData()).endsWith("StringValue")).generate();
		expectedQuery = "SELECT * FROM Data WHERE StringData LIKE '%StringValue'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);

		query = select($(data)).where($(data.getStringData()).contains("StringValue")).generate();
		expectedQuery = "SELECT * FROM Data WHERE StringData LIKE '%StringValue%'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_select() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data.getSubData())).generate();
		String expectedQuery = "SELECT SubData.* FROM Data";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_orderby() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).orderByAscending($(data.getStringData()), $(data.getIntData())).generate();
		String expectedQuery = "SELECT * FROM Data ORDERBY StringData, IntData ASC";
		Assert.assertEquals(expectedQuery, query);
		query = select($(data)).orderByDescending($(data.getStringData()), $(data.getIntData())).generate();
		expectedQuery = "SELECT * FROM Data ORDERBY StringData, IntData DESC";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
		query = select($(data)).orderBy($(data.getStringData()), $(data.getIntData())).generate();
		expectedQuery = "SELECT * FROM Data ORDERBY StringData, IntData";
		Assert.assertEquals(expectedQuery, query);

	}

	@Test
	public void testQuery_not() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).where($(data.getIntData()).eq(10).negate()).generate();
		String expectedQuery = "SELECT * FROM Data WHERE NOT IntData = '10'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_pagination() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = select($(data)).skip(10).generate();
		String expectedQuery = "SELECT * FROM Data STARTPOSITION 11";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);

		query = select($(data)).take(10).generate();
		expectedQuery = "SELECT * FROM Data MAXRESULTS 10";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);

		query = select($(data)).skip(10).take(10).generate();
		expectedQuery = "SELECT * FROM Data STARTPOSITION 11 MAXRESULTS 10";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_count() {
		Data data = GenerateQuery.createQueryEntity(Data.class);
		String query = selectCount(data).generate();
		String expectedQuery = "SELECT count(*) FROM Data";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_calendar() {
		Data data = GenerateQuery.createQueryEntity(Data.class);

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(2012, 8, 1, 0, 0, 0);
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		String query = select($(data)).where($(data.getCalendarData()).eq(date), $(data.getCalendarData()).neq(date),
				$(data.getCalendarData()).gt(date), $(data.getCalendarData()).gte(date),
				$(data.getCalendarData()).in(new java.sql.Date[] { date, date }), $(data.getCalendarData()).lt(date),
				$(data.getCalendarData()).lte(date), $(data.getCalendarData()).between(date, date)).generate();
		String expectedQuery = "SELECT * FROM Data WHERE CalendarData = '" + date + "' AND CalendarData != '" + date + "' AND CalendarData > '" + date + "' AND CalendarData >= '" + date + "' AND CalendarData IN ('" + date + "', '" + date + "') AND CalendarData < '" + date + "' AND CalendarData <= '" + date + "' AND CalendarData BETWEEN '" + date + "Z' AND '" + date + "'";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	@Test
	public void testQuery_line() {
		Invoice data = GenerateQuery.createQueryEntity(Invoice.class);
		String query = select($(data.getLine())).generate();
		String expectedQuery = "SELECT Line.* FROM Invoice";
		LOG.debug(query);
		Assert.assertEquals(expectedQuery, query);
	}

	private String getCalendarAsString(Calendar cal) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		Date date = cal.getTime();
		return formatter.format(date).concat("Z");
	}

	private SimpleDateFormat getDateTimeFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter;
	}

}