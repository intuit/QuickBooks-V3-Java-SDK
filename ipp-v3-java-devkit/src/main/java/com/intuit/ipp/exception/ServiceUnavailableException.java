package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle Service Unavailable Error in HTTP Status code
 * 
 */
public class ServiceUnavailableException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 2488689372746746885L;

	/**
	 * Constructor ServiceUnavailableException
	 * 
	 * @param error the list of errors
	 */
	public ServiceUnavailableException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor ServiceUnavailableException
	 * 
	 * @param errorMessage the error message
	 */
	public ServiceUnavailableException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ServiceUnavailableException
	 * 
	 * @param throwable the throwable
	 */
	public ServiceUnavailableException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ServiceUnavailableException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ServiceUnavailableException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
