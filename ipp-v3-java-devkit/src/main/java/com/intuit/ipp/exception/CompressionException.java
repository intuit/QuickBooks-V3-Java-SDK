package com.intuit.ipp.exception;

/**
 * Exception class to handle data compression specific exceptions
 * 
 */
public class CompressionException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor CompressionException
	 * 
	 * @param errorMessage the error message
	 */
	public CompressionException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor CompressionException
	 * 
	 * @param throwable the throwable
	 */
	public CompressionException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor CompressionException
	 * 
	 * @param errorMessage
	 *            the error message
	 * @param e
	 *            the throwable object
	 */
	public CompressionException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
