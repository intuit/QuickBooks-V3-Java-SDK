/**
 * 
 * @author Aditi
 *
 *This interface will help in adding appropriate authorisation header in the 
 *HTTP request
 */
package com.intuit.ipp.security;

import java.net.HttpURLConnection;
import org.apache.http.client.methods.HttpRequestBase;

import com.intuit.ipp.exception.FMSException;

/**
 * Interface for Authorizer
 *
 */
public interface IAuthorizer {
	/**
	 * This method will authorise an http request using the selected authorisation mechanism
	 * 
	 * @param httpRequest
	 * @throws FMSException
	 */
	void authorize(HttpRequestBase httpRequest) throws FMSException;
	/**
	 * Authorize a http url connection using Signpost
	 * @param httpUrlConnection
	 * @throws FMSException
	 */
	void authorize(HttpURLConnection httpUrlConnection) throws FMSException;
}
