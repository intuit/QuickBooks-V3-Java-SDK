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
package com.intuit.sb.cdm.util.v3;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Adapter for JAXB to bind xs:date type to java.util.Date rather than the
 * default XMLGregorianCalendar.
 */
public class DateAdapter extends XmlAdapter<String, Date> {
	private static final int lengthOfDateFmtYYYY_MM_DD = 10;
	private static final String INVALIDDATE = "9999-12-31";
	private static final String datePattern = "(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])";
	private static final DateTimeFormatter formatter = ISODateTimeFormat.date();
	
	/**
	 * Unmarshal a Date.
	 * 
	 * @param value String from which to unmarshal.
	 * @return the unmarshalled Date object
	 */
	public Date unmarshal(String value) {
		
		if (value != null) {
			
			if (value.length() >= lengthOfDateFmtYYYY_MM_DD) {
				//Extract just the date from the string YYYY-MM-DD
				value = value.substring(0, lengthOfDateFmtYYYY_MM_DD);
				boolean isMatch = value.matches(datePattern);
				if (isMatch) {
					return DatatypeConverter.parseDate(value).getTime();
				} else {
					return DatatypeConverter.parseDate(INVALIDDATE).getTime();
				}
			} else {
				return DatatypeConverter.parseDate(INVALIDDATE).getTime();
			}
		} else {
			return null;
		}
	}

	/**
	 * Marshal a Date to a String for use by JAXB.
	 * 
	 * returns date in yyyy-mm-dd format
	 * 
	 * @param value Date object to marshal.
	 * @return String form of date.
	 * 
	 */
	public String marshal(Date value) {
		
		if(value == null){
			return null;
		}
		
		return formatter.print(new DateTime(value));

	}	
	

}
