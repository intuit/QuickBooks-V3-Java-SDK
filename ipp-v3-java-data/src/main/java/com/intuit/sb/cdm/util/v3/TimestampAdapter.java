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
