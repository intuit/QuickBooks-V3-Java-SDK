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
 * Class to generate the query string for boolean value
 * 
 */
public class BooleanPath extends Path<String> {

	/**
	 * Constructor BooleanPath
	 * 
	 * @param path
	 *            the path
	 * @param entity
	 *            the entity
	 */
	public BooleanPath(String path, String entity) {
		super(path, entity);
	}

	/**
	 * Method to construct the equals expression for boolean
	 * @param value the boolean value
	 * @return Expression
	 */
	public Expression<Boolean> eq(Boolean value) {
		String valueString = value.toString();
		return new Expression<Boolean>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the not equals expression for boolean
	 * @param value the boolean value
	 * @return Expression
	 */
	public Expression<Boolean> neq(Boolean value) {
		String valueString = value.toString();
		return new Expression<Boolean>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the in expression for boolean
	 * @param value the boolean value
	 * @return Expression
	 */
	public Expression<Boolean> in(Boolean[] value) {
		String listBooleanString = "";
		Boolean firstNumber = true;
		for (Boolean v : value) {
			if (firstNumber) {
				listBooleanString = listBooleanString.concat("(").concat(v.toString());
				firstNumber = false;
			} else {
				listBooleanString = listBooleanString.concat(", ").concat(v.toString());
			}
		}
		listBooleanString = listBooleanString.concat(")");
		return new Expression<Boolean>(this, Operation.in, listBooleanString);
	}
}
