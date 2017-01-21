package com.intuit.ipp.query.expr;

import com.intuit.ipp.query.Operation;
import com.intuit.ipp.query.Path;

/**
 * Class to have expression POJO
 * 
 */
public class Expression<T> {

	/**
	 * variable
	 */
	private Path<?> path = null;

	/**
	 * variable
	 */
	private boolean negated = false;

	/**
	 * variable
	 */
	private Operation operation = null;

	/**
	 * variable
	 */
	private String value = null;

	/**
	 * Constructor Expression
	 * 
	 * @param path
	 *            the path
	 * @param oper
	 *            the operation
	 * @param value
	 *            the value
	 */
	public Expression(Path<?> path, Operation oper, String value) {
		this.path = path;
		this.operation = oper;
		this.value = value;
	}

	/**
	 * Gets path
	 *  
	 * @return path
	 */
	public Path<?> getPath() {
		return path;
	}

	/**
	 * Sets path
	 * 
	 * @param path
	 */
	public void setPath(Path<?> path) {
		this.path = path;
	}

	/**
	 * Gets operation
	 *  
	 * @return operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Sets operation
	 * 
	 * @param operation
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * Gets
	 *  
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets value
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * the toString method
	 */
	public String toString() {
		String expressionString;
		if (isNegated()) {
			expressionString = "NOT " + getPath() + " " + getOperation().toString() + " " + getValue();
		} else {
			expressionString = getPath() + " " + getOperation().toString() + " " + getValue();
		}

		return expressionString;
	}

	/**
	 * Method to negate
	 * 
	 * @return Expression
	 */
	public Expression<?> negate() {
		this.setNegated(true);
		return this;
	}

	/**
	 * Gets negated
	 *  
	 * @return negated
	 */
	public boolean isNegated() {
		return negated;
	}

	/**
	 * Sets negated
	 * 
	 * @param negated
	 */
	public void setNegated(boolean negated) {
		this.negated = negated;
	}

}
