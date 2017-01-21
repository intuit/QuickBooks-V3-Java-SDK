package com.intuit.ipp.query;

/**
 * list all the operations
 * 
 * @author lokeshg
 * 
 */
public enum Operation {
	/**
	 * Operation equal
	 */
	eq("="), 
	
	/**
	 * Operation not equal
	 */
	neq("!="), 
	
	/**
	 * Operation less than
	 */
	lt("<"), 
	
	/**
	 * Operation less than or equal
	 */
	lte("<="), 
	
	/**
	 * Operation greater than
	 */
	gt(">"), 
	
	/**
	 * Operation greater than or equal
	 */
	gte(">="), 
	
	/**
	 * Operation in
	 */
	in("IN"), 
	
	/**
	 * Operation like
	 */
	like("LIKE"), 
	
	/**
	 * Operation between
	 */
	between("BETWEEN");
	
	/**
	 * variable operator
	 */
	private String operator = null;
	
	/**
	 * Constructor
	 * 
	 * @param operator the operator
	 */
	Operation(String operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return this.operator;
	}
		
}
