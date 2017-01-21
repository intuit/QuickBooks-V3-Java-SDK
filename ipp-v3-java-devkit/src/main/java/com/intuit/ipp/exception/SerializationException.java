package com.intuit.ipp.exception;

/**
 * Exception class to handle data serialization specific exceptions
 * 
 */
public class SerializationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor SerializationException
	 * 
	 * @param errorMessage the error message
	 */
	public SerializationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor SerializationException
	 * 
	 * @param throwable the throwable
	 */
	public SerializationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor SerializationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public SerializationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
