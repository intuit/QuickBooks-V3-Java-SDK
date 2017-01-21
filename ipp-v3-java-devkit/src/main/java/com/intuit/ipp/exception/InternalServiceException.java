package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle Internal Service error in HTTP Status code
 * 
 */
public class InternalServiceException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 656005572669015852L;

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param error the list of errors
	 */
	public InternalServiceException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param errorMessage the error message
	 */
	public InternalServiceException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param throwable the throwable
	 */
	public InternalServiceException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor for InternalServiceException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public InternalServiceException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
