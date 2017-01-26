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
package com.intuit.ipp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Util class to have methods to get the Date instance
 *
 */
public final class DateUtils {

    /**
     * Constructor to have private modifier as it has only static methods
     */
    private DateUtils() {
    }

    /**
     * variable DATE_YYYYMMDDTHHMMSS_SSSZ
     */
    public static final String DATE_yyyyMMddTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * variable DATE_yyyMMddTHHmmssZ (Date format without milliseconds)
     */
    public static final String DATE_yyyMMddTHHmmssZ = "yyyy-MM-dd'T'HH:mm:ssZ";


    /**
     * variable DATE_yyyyMMddTHHmmssSSSZone
     */
    public static final String DATE_yyyyMMddTHHmmssSSSZone = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * variable DATE_yyyyMMddTHHmmssSSSZone (Date format with milliseconds)
     */
    public static final String DATE_yyyMMddTHHmmssZone = "yyyy-MM-dd'T'HH:mm:ss'Z'";



    /**
     * Method to get the date utils instance
     *
     * @return DateUtils
     */
    public static DateUtils getInstance() {
        return new DateUtils();
    }

    /**
     * Method to get the Calendar instance for the given Date format
     *
     * @param date the date
     * @return the Date instance
     * @throws java.text.ParseException
     * @throws ParseException
     */

    /**
     * Note: The below method uses date formats that are supported by Java SE 1.6 since we still
     * need to support it. Moving forward when we port to Java SE 1.7 please update the code and
     * use more generic date formats to make the code more robust.
     * Java SE 1.6: http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
     * Java SE 1.7: http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
     *
    */
    public static Date getDateFromString(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZ);

        if (date.charAt(date.length() - 3) == ':' && !date.endsWith("Z")) {
            date = date.substring(0, date.lastIndexOf(':')) + date.substring(date.lastIndexOf(':') + 1, date.length());
            if (!date.contains(".")) {
                formatter = new SimpleDateFormat(DATE_yyyMMddTHHmmssZ);
            }

        } else if (date.charAt(date.length() - 3) != ':' && !date.endsWith("Z")) {
            if (!date.contains(".")) {
                formatter = new SimpleDateFormat(DATE_yyyMMddTHHmmssZ);
            }

        } else if (date.endsWith("Z") && date.contains(".")) {
            formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZone);
        } else if (date.endsWith("Z") && !date.contains(".")) {
            formatter = new SimpleDateFormat(DATE_yyyMMddTHHmmssZone);
        }
        return formatter.parse(date);
    }
    /**
     * Method to get the current date time Calendar instance
     *
     * @return the Date instance
     * @throws ParseException
     */
    public static Date getCurrentDateTime() throws ParseException {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZ);
        String dateNow = formatter.format(currentDate.getTime());
        return getDateFromString(dateNow);
    }

    /**
     * Method to get the Date instance for the given days to be added to the current date
     *
     * @param noOfDays
     * @return the Date instance
     * @throws ParseException
     */
    public static Date getDateWithNextDays(int noOfDays) throws ParseException {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, noOfDays);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZ);
        String dateNow = formatter.format(currentDate.getTime());
        return getDateFromString(dateNow);
    }

    /**
     * Method to get the Date instance for the given days to be subtracted to the current date
     *
     * @param noOfDays
     * @return the Date instance
     * @throws ParseException
     */
    public static Date getDateWithPrevDays(int noOfDays) throws ParseException {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -noOfDays);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZ);
        String dateNow = formatter.format(currentDate.getTime());
        return getDateFromString(dateNow);
    }

    /**
     * Method to convert the given Date to String format
     *
     * @param date the java.util.Date
     * @return the date in string
     * @throws ParseException
     */
    public static String getStringFromDateTime(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_yyyyMMddTHHmmssSSSZone);
        return formatter.format(date);
    }
}
