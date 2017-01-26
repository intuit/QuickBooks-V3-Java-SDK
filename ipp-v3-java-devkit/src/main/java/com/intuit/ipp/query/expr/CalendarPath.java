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
package com.intuit.ipp.query.expr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.intuit.ipp.query.Operation;
import com.intuit.ipp.query.Path;

/**
 * Class to generate the query string for calendar or date value
 *
 */
public class CalendarPath extends Path<Object> {

	/**
	 * Constructor CalendarPath
	 * 
	 * @param path the path
	 * @param entity the entity
	 */
	public CalendarPath(String path, String entity) {
		super(path, entity);
	}

	/**
	 * Method to get simple date format
	 * 
	 * @return SimpleDateFormat
	 */
	private SimpleDateFormat getDateTimeFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter;
	}

	/**
	 * Method to get simple date format
	 * 
	 * @return SimpleDateFormat
	 */
	private SimpleDateFormat getDateFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("yyyy-MM-dd");
		return formatter;
	}

	/**
	 * Method to get string value of date from Calendar
	 * 
	 * @return String the date
	 */
	private String getCalendarAsString(Calendar cal) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		Date date = cal.getTime();
		return formatter.format(date).concat("Z");
	}

	/**
	 * Method to construct the equals expression for Calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> eq(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> eq(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> eq(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the not equals expression for Calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> neq(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> neq(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> neq(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the less than expression for calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> lt(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> lt(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> lt(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> lte(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> lte(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> lte(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the greater than expression for calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> gt(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> gt(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> gt(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for calendar
	 * 
	 * @param value the calendar value
	 * @return Expression
	 */
	public Expression<Calendar> gte(Calendar value) {
		String valueString = "'" + getCalendarAsString(value) + "'";
		return new Expression<Calendar>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.util.Date> gte(java.util.Date value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(value).concat("Z") + "'";
		return new Expression<java.util.Date>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for date
	 * 
	 * @param value the date value
	 * @return Expression
	 */
	public Expression<java.sql.Date> gte(java.sql.Date value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(value) + "'";
		return new Expression<java.sql.Date>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the in expression for calendar
	 * 
	 * @param value the calendar array
	 * @return Expression
	 */
	public Expression<Calendar> in(Calendar[] value) {
		String valueString = "";
		Boolean firstCalendar = true;
		for (Calendar v : value) {
			if (firstCalendar) {
				valueString = valueString.concat("('").concat(getCalendarAsString(v)).concat("'");
				firstCalendar = false;
			} else {
				valueString = valueString.concat(", '").concat(getCalendarAsString(v)).concat("'");
			}
		}

		valueString = valueString.concat(")");
		return new Expression<Calendar>(this, Operation.in, valueString);
	}

	/**
	 * Method to construct the in expression for date
	 * 
	 * @param value the date array
	 * @return Expression
	 */
	public Expression<java.util.Date> in(java.util.Date[] value) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "";
		Boolean firstCalendar = true;
		for (Date v : value) {
			if (firstCalendar) {
				valueString = valueString.concat("('").concat(formatter.format(v).concat("Z")).concat("'");
				firstCalendar = false;
			} else {
				valueString = valueString.concat(", '").concat(formatter.format(v).concat("Z")).concat("'");
			}
		}

		valueString = valueString.concat(")");
		return new Expression<java.util.Date>(this, Operation.in, valueString);
	}

	/**
	 * Method to construct the in expression for date
	 * 
	 * @param value the date array
	 * @return Expression
	 */
	public Expression<java.sql.Date> in(java.sql.Date[] value) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "";
		Boolean firstCalendar = true;
		for (Date v : value) {
			if (firstCalendar) {
				valueString = valueString.concat("('").concat(formatter.format(v)).concat("'");
				firstCalendar = false;
			} else {
				valueString = valueString.concat(", '").concat(formatter.format(v)).concat("'");
			}
		}

		valueString = valueString.concat(")");
		return new Expression<java.sql.Date>(this, Operation.in, valueString);
	}

	/**
	 * Method to construct the between expression for calendar
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Calendar> between(Calendar startValue, Calendar endValue) {
		String valueString = "'" + getCalendarAsString(startValue).concat("Z") + "' AND '" + getCalendarAsString(endValue) + "'";
		return new Expression<Calendar>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for date
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<java.util.Date> between(java.util.Date startValue, java.util.Date endValue) {
		SimpleDateFormat formatter = getDateTimeFormatter();
		String valueString = "'" + formatter.format(startValue).concat("Z") + "' AND '" + formatter.format(endValue) + "'";
		return new Expression<java.util.Date>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for date
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<java.sql.Date> between(java.sql.Date startValue, java.sql.Date endValue) {
		SimpleDateFormat formatter = getDateFormatter();
		String valueString = "'" + formatter.format(startValue).concat("Z") + "' AND '" + formatter.format(endValue) + "'";
		return new Expression<java.sql.Date>(this, Operation.between, valueString);
	}
}
