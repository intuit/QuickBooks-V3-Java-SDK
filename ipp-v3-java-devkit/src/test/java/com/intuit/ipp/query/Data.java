package com.intuit.ipp.query;

import java.util.Calendar;
import java.util.Date;

import com.intuit.ipp.core.IEntity;

public class Data implements IEntity{
	private String stringData;
	private int intData;
	private byte byteData;
	private short shortData;
	private long longData;
	private float floatData;
	private double doubleData;
	private boolean booleanData;
	private Calendar calendarData;
	private SubData subData;
	private Date dateData;
	private Enum<?> enumData;
	
	public String getStringData() {
		return stringData;
	}
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
	public int getIntData() {
		return intData;
	}
	public void setIntData(int intData) {
		this.intData = intData;
	}
	public boolean isBooleanData() {
		return booleanData;
	}
	public void setBooleanData(boolean booleanData) {
		this.booleanData = booleanData;
	}
	public Calendar getCalendarData() {
		return calendarData;
	}
	public void setCalendarData(Calendar calendarData) {
		this.calendarData = calendarData;
	}
	public byte getByteData() {
		return byteData;
	}
	public void setByteData(byte byteData) {
		this.byteData = byteData;
	}
	public short getShortData() {
		return shortData;
	}
	public void setShortData(short shortData) {
		this.shortData = shortData;
	}
	public long getLongData() {
		return longData;
	}
	public void setLongData(long longData) {
		this.longData = longData;
	}
	public float getFloatData() {
		return floatData;
	}
	public void setFloatData(float floatData) {
		this.floatData = floatData;
	}
	public double getDoubleData() {
		return doubleData;
	}
	public void setDoubleData(double doubleData) {
		this.doubleData = doubleData;
	}
	public SubData getSubData() {
		return subData;
	}
	public void setSubData(SubData subData) {
		this.subData = subData;
	}
	public Date getDateData() {
		return dateData;
	}
	public void setDateData(Date dateData) {
		this.dateData = dateData;
	}
	public Enum<?> getEnumData() {
		return enumData;
	}
	public void setEnumData(Enum<?> enumData) {
		this.enumData = enumData;
	}
}
