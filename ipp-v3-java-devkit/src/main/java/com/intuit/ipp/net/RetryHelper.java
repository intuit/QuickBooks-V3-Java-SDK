package com.intuit.ipp.net;

import com.intuit.ipp.exception.ConfigurationException;

/**
 * Class to help the retry mechanism
 *
 */
public final class RetryHelper {

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private RetryHelper() {
	}
	
	/**
	 * Checks whether the given argument value is -ve or not
	 * 
	 * @param argumentValue the value
	 * @param argumentName the name
	 * @throws NegativeValueException
	 */
	public static void argumentNotNegativeValue(long argumentValue, String argumentName) throws ConfigurationException {
		if (argumentValue < 0) {
			throw new ConfigurationException("The value for argument name [" + argumentName + "] is negative [" 
					+ argumentValue + "]. Please check the configuration.");
		}
	}
}
