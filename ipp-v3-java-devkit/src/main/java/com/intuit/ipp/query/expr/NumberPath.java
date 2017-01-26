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
 * Class to generate the query string for number value
 * 
 */
public class NumberPath extends Path<Number> {

	/**
	 * Constructor NumberPath
	 * 
	 * @param path
	 *            the path
	 * @param entity
	 *            the entity
	 */
	public NumberPath(String path, String entity) {
		super(path, entity);
	}

	/**
	 * Method to construct the equals expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> eq(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> eq(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for integer
	 * 
	 * @param value the integer
	 * @return Expression
	 */
	public Expression<Integer> eq(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> eq(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> eq(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the equals expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> eq(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.eq, valueString);
	}

	/**
	 * Method to construct the not equals expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> neq(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> neq(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for int
	 * 
	 * @param value the int
	 * @return Expression
	 */
	public Expression<Integer> neq(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> neq(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> neq(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the not equals expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> neq(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.neq, valueString);
	}

	/**
	 * Method to construct the less than expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> lt(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> lt(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for int
	 * 
	 * @param value the int
	 * @return Expression
	 */
	public Expression<Integer> lt(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> lt(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> lt(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> lt(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.lt, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> lte(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> lte(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for int
	 * 
	 * @param value the int
	 * @return Expression
	 */
	public Expression<Integer> lte(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> lte(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> lte(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the less than or equals expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> lte(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.lte, valueString);
	}

	/**
	 * Method to construct the greater than expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> gt(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> gt(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for int
	 * 
	 * @param value the int
	 * @return Expression
	 */
	public Expression<Integer> gt(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> gt(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> gt(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> gt(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.gt, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for byte
	 * 
	 * @param value the byte
	 * @return Expression
	 */
	public Expression<Byte> gte(byte value) {
		String valueString = "'" + value + "'";
		return new Expression<Byte>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for short
	 * 
	 * @param value the short
	 * @return Expression
	 */
	public Expression<Short> gte(short value) {
		String valueString = "'" + value + "'";
		return new Expression<Short>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for int
	 * 
	 * @param value the int
	 * @return Expression
	 */
	public Expression<Integer> gte(int value) {
		String valueString = "'" + value + "'";
		return new Expression<Integer>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for long
	 * 
	 * @param value the long
	 * @return Expression
	 */
	public Expression<Long> gte(long value) {
		String valueString = "'" + value + "'";
		return new Expression<Long>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for float
	 * 
	 * @param value the float
	 * @return Expression
	 */
	public Expression<Float> gte(float value) {
		String valueString = "'" + value + "'";
		return new Expression<Float>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the greater than or equals expression for double
	 * 
	 * @param value the double
	 * @return Expression
	 */
	public Expression<Double> gte(double value) {
		String valueString = "'" + value + "'";
		return new Expression<Double>(this, Operation.gte, valueString);
	}

	/**
	 * Method to construct the in expression for byte
	 * 
	 * @param value the byte array
	 * @return Expression
	 */
	public Expression<Byte> in(Byte[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Byte v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Byte>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the in expression for short
	 * 
	 * @param value the short array
	 * @return Expression
	 */
	public Expression<Short> in(Short[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Short v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Short>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the in expression for integer
	 * 
	 * @param value the integer array
	 * @return Expression
	 */
	public Expression<Integer> in(Integer[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Integer v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Integer>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the in expression for long
	 * 
	 * @param value the long array
	 * @return Expression
	 */
	public Expression<Long> in(Long[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Long v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Long>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the in expression for float
	 * 
	 * @param value the float array
	 * @return Expression
	 */
	public Expression<Float> in(Float[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Float v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Float>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the in expression for double
	 * 
	 * @param value the double array
	 * @return Expression
	 */
	public Expression<Double> in(Double[] value) {
		String listNumberString = "";
		Boolean firstNumber = true;
		for (Double v : value) {
			if (firstNumber) {
				listNumberString = listNumberString.concat("('").concat(v.toString()).concat("'");
				firstNumber = false;
			} else {
				listNumberString = listNumberString.concat(", '").concat(v.toString()).concat("'");
			}
		}
		listNumberString = listNumberString.concat(")");
		return new Expression<Double>(this, Operation.in, listNumberString);
	}

	/**
	 * Method to construct the between expression for byte
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Byte> between(Byte startValue, Byte endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Byte>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for short
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Short> between(Short startValue, Short endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Short>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for integer
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Integer> between(Integer startValue, Integer endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Integer>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for long
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Long> between(Long startValue, Long endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Long>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for float
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Float> between(Float startValue, Float endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Float>(this, Operation.between, valueString);
	}

	/**
	 * Method to construct the between expression for double
	 * 
	 * @param startValue the start value
	 * @param endValue the end value
	 * @return Expression
	 */
	public Expression<Double> between(Double startValue, Double endValue) {
		String valueString = "'" + startValue + "' AND '" + endValue + "'";
		return new Expression<Double>(this, Operation.between, valueString);
	}
}
