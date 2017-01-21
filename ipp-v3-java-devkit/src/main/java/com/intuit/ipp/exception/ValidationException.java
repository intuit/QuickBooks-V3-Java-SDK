package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle validation specific exceptions
 * 
 */
public class ValidationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2852044978205674856L;

	/**
	 * Constructor ValidationException
	 * 
	 * @param error the error message
	 */
	public ValidationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param errorMessage the error message
	 */
	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param throwable the throwable
	 */
	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ValidationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ValidationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
