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
