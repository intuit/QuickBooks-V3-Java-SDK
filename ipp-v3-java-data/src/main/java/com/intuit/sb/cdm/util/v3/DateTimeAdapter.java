/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
