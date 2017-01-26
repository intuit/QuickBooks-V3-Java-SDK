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
 * Class to generate the query string for enum value
 *
 */
public class EnumPath extends Path<Enum<?>> {

	/**
	 * Constructor EnumPath
	 * 
	 * @param path the path
	 * @param entity the entity
	 */
	public EnumPath(String path, String entity) {
		super(path, entity);
	}

	/**
	 * Method to construct the equals expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> eq(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the not equals expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> neq(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the less than expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> lt(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> lte(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the greater than expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> gt(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for enum
	 * 
	 * @param value the enum
	 * @return Expression
	 */
	public Expression<Enum<?>> gte(Enum<?> value) {
		String valueString = "'" + value + "'";
		return new Expression<Enum<?>>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the in expression for enum
	 * 
	 * @param value the enum array
	 * @return Expression
	 */
	public Expression<Enum<?>> in(Enum<?>[] value) {
		String listString = "";
		Boolean firstString = true;
		for (Enum<?> v : value) {
			if (firstString) {
				listString = listString.concat("('").concat(v.toString()).concat("'");
				firstString = false;
			} else {
				listString = listString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listString = listString.concat(")");
		return new Expression<Enum<?>>(this, Operation.in, listString);
	}
}
