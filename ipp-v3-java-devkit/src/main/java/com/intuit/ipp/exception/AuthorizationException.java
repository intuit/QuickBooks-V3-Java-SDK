package com.intuit.ipp.exception;

import java.util.List;
import com.intuit.ipp.data.Error;

/**
 * Exception class to handle authorization specific exceptions
 * 
 */
public class AuthorizationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -5055305291301191852L;

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param error the list of errors
	 */
	public AuthorizationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param errorMessage the error message
	 */
	public AuthorizationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param throwable the throwable
	 */
	public AuthorizationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor AuthorizationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AuthorizationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
