package com.intuit.ipp.exception;

import java.util.List;
import com.intuit.ipp.data.Error;

/**
 * Exception class to handle bad request in HTTP Status code
 * 
 */
public class BadRequestException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1669964679974595082L;

	/**
	 * Constructor BasRequestException
	 * 
	 * @param error the list of errors
	 */
	public BadRequestException(List<Error> error) {
		super(error);
	}

	/**
	 * Constructor BasRequestException
	 * 
	 * @param errorMessage the error message
	 */
	public BadRequestException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor BasRequestException
	 * 
	 * @param throwable the throwable
	 */
	public BadRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor BasRequestException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public BadRequestException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
