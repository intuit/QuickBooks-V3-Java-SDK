/* CONFIDENTIAL -- Copyright 2009, 2017 Intuit Inc. This material contains certain  *
 * trade secrets and confidential and proprietary information of Intuit Inc.  *
 * Use, reproduction, disclosure and distribution by any means are prohibited,*
 * except pursuant to a written license from Intuit Inc. Use of copyright     *
 * notice is precautionary and does not imply publication or disclosure.      *
 */
package com.intuit.sb.cdm.util.v3;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB to bind xs:timestamp type to java.util.Timestamp rather than
 * the default XMLGregorianCalendar.
 * 
 * @author vkrutenev
 */
public class TimestampAdapter extends XmlAdapter<String, Date>
{
    /**
     * Unmarshal a Timestamp.
     * 
     * @param value String from which to unmarshal.
     * @return the unmarshalled Date object
     */
    public Date unmarshal( String value )
    {
        return Timestamp.valueOf( value );
    }
    
    /**
     * Marshal a Date to a String for use by JAXB.
     * 
     * @param value Date object to marshal.
     * @return String form of date.
     */
    public String marshal( Date value )
    {
        Timestamp timeStampDate = new Timestamp( value.getTime() );
        return timeStampDate.toString();
    }
}
