package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle Invalid Request Error in HTTP Status code
 * 
 */
public class InvalidRequestException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2152752316496012401L;

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param error the list of errors
	 */
	public InvalidRequestException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param errorMessage the error message
	 */
	public InvalidRequestException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param throwable the throwable
	 */
	public InvalidRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor InvalidRequestException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public InvalidRequestException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
