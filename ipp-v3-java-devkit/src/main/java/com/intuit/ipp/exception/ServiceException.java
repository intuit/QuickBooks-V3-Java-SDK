package com.intuit.ipp.exception;

import java.util.List;

import com.intuit.ipp.data.Error;

/**
 * Exception class to handle service specific exceptions
 * 
 */
public class ServiceException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1342882317978713117L;

	/**
	 * Constructor ServiceException
	 * 
	 * @param error the list of errors
	 */
	public ServiceException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param errorMessage the error message
	 */
	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param throwable the throwable
	 */
	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ServiceException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ServiceException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
