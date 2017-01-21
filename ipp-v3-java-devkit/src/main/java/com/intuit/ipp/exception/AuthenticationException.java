package com.intuit.ipp.exception;

import java.util.List;
import com.intuit.ipp.data.Error;

/**
 * Exception class to handle data authentication specific exceptions
 * 
 */
public class AuthenticationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 8955466107559861519L;

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param error the list of errors
	 */
	public AuthenticationException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param errorMessage the error message
	 */
	public AuthenticationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param throwable the throwable
	 */
	public AuthenticationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor AuthenticationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public AuthenticationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

}
