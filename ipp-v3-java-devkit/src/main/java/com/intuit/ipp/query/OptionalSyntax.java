package com.intuit.ipp.query;

import com.intuit.ipp.query.expr.Expression;
import com.intuit.ipp.util.Logger;

/**
 * Class for optional syntax for the query message
 *
 */
public class OptionalSyntax {

	/**
	 * variable logger
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable message
	 */
	private QueryMessage message = null;

	/**
	 * Constructor OptionalSyntax
	 * 
	 * @param mess the query message
	 */
	public OptionalSyntax(QueryMessage mess) {
		this.message = mess;
	}

	/**
	 * Method to get the optional syntax for where operator
	 * 
	 * @param expression the expression
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax where(Expression<?>... expression) {
		for (Expression<?> exp : expression) {
			getMessage().getOptional().add(exp.toString());
			LOG.debug("expression: " + exp);
		}
		QueryMessage mess = getMessage();
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the optional syntax for order by operator
	 * 
	 * @param Path the path
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax orderBy(Path<?>... path) {
		String fieldList = "";
		boolean firstExpression = true;
		for (Path<?> exp : path) {
			if (firstExpression) {
				fieldList = fieldList.concat(exp.toString());
				firstExpression = false;
			} else {
				fieldList = fieldList.concat(", ").concat(exp.toString());
			}

			LOG.debug("expression: " + exp);
		}
		QueryMessage mess = getMessage();
		mess.setOrderByClause(fieldList);
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the optional syntax for order by asc operator
	 * 
	 * @param Path the path
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax orderByAscending(Path<?>... path) {
		String fieldList = "";
		boolean firstExpression = true;
		for (Path<?> exp : path) {
			if (firstExpression) {
				fieldList = fieldList.concat(exp.toString());
				firstExpression = false;
			} else {
				fieldList = fieldList.concat(", ").concat(exp.toString());
			}

			LOG.debug("expression: " + exp);
		}

		fieldList = fieldList.concat(" ASC");
		QueryMessage mess = getMessage();
		mess.setOrderByClause(fieldList);
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the optional syntax for order by desc operator
	 * 
	 * @param Path the path
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax orderByDescending(Path<?>... path) {
		String fieldList = "";
		boolean firstExpression = true;
		for (Path<?> exp : path) {
			if (firstExpression) {
				fieldList = fieldList.concat(exp.toString());
				firstExpression = false;
			} else {
				fieldList = fieldList.concat(", ").concat(exp.toString());
			}

			LOG.debug("expression: " + exp);
		}

		fieldList = fieldList.concat(" DESC");
		QueryMessage mess = getMessage();
		mess.setOrderByClause(fieldList);
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the optional syntax for skip operator
	 * 
	 * @param num the number
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax skip(int num) {
		getMessage().setStartposition(num);
		QueryMessage mess = getMessage();
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the optional syntax for take operator
	 * 
	 * @param num the number
	 * @return OptionalSyntax the optional syntax
	 */
	public OptionalSyntax take(int num) {
		getMessage().setMaxresults(num);
		QueryMessage mess = getMessage();
		return new OptionalSyntax(mess);
	}

	/**
	 * Method to get the query message
	 * 
	 * @return QueryMessage teh query message
	 */
	public QueryMessage getMessage() {
		return message;
	}

	/**
	 * Method to set the query message
	 * 
	 * @param message the query message
	 */
	public void setMessage(QueryMessage message) {
		this.message = message;
	}

	/**
	 * Method to get the string value for the query message
	 * 
	 * @return string the query message
	 */
	public String generate() {
		return toString();
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String toString() {
		return getMessage().toString();
	}
}
