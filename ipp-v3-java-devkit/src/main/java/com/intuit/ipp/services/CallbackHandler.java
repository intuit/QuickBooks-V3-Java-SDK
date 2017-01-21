package com.intuit.ipp.services;

/**
 * Interface to have the callback method for asynchronous calls
 *
 */
public interface CallbackHandler {

	/**
	 * Method to be used by the callback implementors for asynchronous calls
	 * 
	 * @param callbackMessage
	 */
	void execute(CallbackMessage callbackMessage);
	
}
