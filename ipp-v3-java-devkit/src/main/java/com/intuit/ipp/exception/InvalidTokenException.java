package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle Authorization or Authentication Error in HTTP Status code
 * 
 */
public class InvalidTokenException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2219693778107681602L;

	/**
	 * Constructor InvalidTokenException
	 * 
	 * @param error the list of errors
	 */
	public InvalidTokenException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor InvalidTokenException
	 * 
	 * @param errorMessage the error message
	 */
	public InvalidTokenException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor InvalidTokenException
	 * 
	 * @param throwable the throwable
	 */
	public InvalidTokenException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor InvalidTokenException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public InvalidTokenException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
