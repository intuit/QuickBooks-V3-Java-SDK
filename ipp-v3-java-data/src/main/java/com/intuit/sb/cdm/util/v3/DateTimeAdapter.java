/* CONFIDENTIAL -- Copyright 2009 Intuit Inc. This material contains certain  *
 * trade secrets and confidential and proprietary information of Intuit Inc.  *
 * Use, reproduction, disclosure and distribution by any means are prohibited,*
 * except pursuant to a written license from Intuit Inc. Use of copyright     *
 * notice is precautionary and does not imply publication or disclosure.      *
 */
package com.intuit.sb.cdm.util.v3;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB to bind xs:dateTime type to java.util.Date rather than the
 * default XMLGregorianCalendar.
 * 
 * @author vkrutenev
 */
public class DateTimeAdapter extends XmlAdapter<String, Date>
{
    /**
     * Unmarshal a Date and Time.
     * 
     * @param value String from which to unmarshal.
     * @return the unmarshalled Date object
     */
    public Date unmarshal( String value )
    {
        return DatatypeConverter.parseDateTime( value ).getTime();
    }
    
    /**
     * Marshal a Date to a String for use by JAXB.
     * 
     * @param value Date object to marshal.
     * @return String form of date.
     */
    public String marshal( Date value )
    {
        String val = "";
    	if (value != null) {
        	Calendar cal = new GregorianCalendar();
        	cal.setTime( value );
        	return DatatypeConverter.printDateTime( cal );
        }
		
		System.out.println("Date value: " + value);
        return val;
    }
}
