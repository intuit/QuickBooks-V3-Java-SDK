package com.intuit.ipp.interceptors;

import com.intuit.ipp.exception.FMSException;

/**
 * Interface to have interceptor methods
 *
 */
public interface Interceptor {
	
	/**
	 * Method to handle corresponding interceptor's implementation
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	void execute(IntuitMessage intuitMessage) throws FMSException;
	
}
