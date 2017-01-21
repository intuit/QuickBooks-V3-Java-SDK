package com.intuit.ipp.exception;

/**
 * Exception class to handle data configuration specific exceptions
 * 
 */
public class ConfigurationException extends FMSException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8415364890369303956L;

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param errorMessage the error message
	 */
	public ConfigurationException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param throwable the throwable
	 */
	public ConfigurationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructor ConfigurationException
	 * 
	 * @param errorMessage the error message
	 * @param throwable the throwable
	 */
	public ConfigurationException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}
}
