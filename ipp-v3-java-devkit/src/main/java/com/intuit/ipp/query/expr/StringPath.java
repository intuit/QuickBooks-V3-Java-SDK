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

import com.intuit.ipp.query.Operation;
import com.intuit.ipp.query.Path;

/**
 * Class to generate the query string for string value
 *
 */
public class StringPath extends Path<String> {

	/**
	 * Constructor StringPath
	 * 
	 * @param path the path
	 * @param entity the entity
	 */
	public StringPath(String path, String entity) {
		super(path, entity);
	}

	/**
	 * Method to construct the equals expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> eq(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the not equals expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> neq(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the less than expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> lt(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> lte(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the greater than expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> gt(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for string
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> gte(String value) {
		String valueString = "'" + value + "'";
		return new Expression<String>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the in expression for string
	 * 
	 * @param value the string array
	 * @return Expression
	 */
	public Expression<String> in(String[] value) {
		String listString = "";
		Boolean firstString = true;
		for (String v : value) {
			if (firstString) {
				listString = listString.concat("('").concat(v).concat("'");
				firstString = false;
			} else {
				listString = listString.concat(", '").concat(v).concat("'");
			}
		}
		listString = listString.concat(")");
		return new Expression<String>(this, Operation.in, listString);
	}

	/**
	 * Method to construct the like expression for string starts with
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> startsWith(String value) {
		String valueString = "'" + value + "%'";
		return new Expression<String>(this, Operation.like, valueString);
	}
	
	/**
	 * Method to construct the like expression for string ends with
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> endsWith(String value) {
		String valueString = "'%" + value + "'";
		return new Expression<String>(this, Operation.like, valueString);
	}
	
	/**
	 * Method to construct the like expression for string contains
	 * 
	 * @param value the string value
	 * @return Expression
	 */
	public Expression<String> contains(String value) {
		String valueString = "'%" + value + "%'";
		return new Expression<String>(this, Operation.like, valueString);
	}

	/**
	 * Method to construct the between expression for string
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<String> between(String startValue, String endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<String>(this, Operation.between, valueString);
	}
}
