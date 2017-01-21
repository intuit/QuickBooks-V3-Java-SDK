package com.intuit.ipp.interceptors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.exception.FMSException;

/**
 * Class to provide the provision to add interceptors in the order those have to be executed. 
 * 
 */
public class PlatformInterceptorProvider {



    /**
	 * variable requestInterceptors is used for keeping the request interceptors
	 */

	private List<Interceptor> requestInterceptors = new ArrayList<Interceptor>();

	/**
	 * variable responseInterceptors is used for keeping the response interceptors
	 */
	private List<Interceptor> responseInterceptors = new ArrayList<Interceptor>();

	/**
	 * Constructor IntuitInterceptorProvider
	 */
	public PlatformInterceptorProvider() {

		requestInterceptors.add(new PrepareRequestInterceptor());
		requestInterceptors.add(new HTTPClientConnectionInterceptor());

		responseInterceptors.add(new DecompressionInterceptor());
		responseInterceptors.add(new DeserializeInterceptor());
	}

	/**
	 * Method to execute the interceptors (request and response) which are added
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	public void executeInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		executeRequestInterceptors(intuitMessage);
		executeResponseInterceptors(intuitMessage);
	}

	/**
	 * Method to execute only request interceptors which are added to requestInterceptors list
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	private void executeRequestInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		Iterator<Interceptor> itr = requestInterceptors.iterator();
		while (itr.hasNext()) {
			Interceptor interceptor = itr.next();
			interceptor.execute(intuitMessage);
		}
	}

	/**
	 * Method to execute only response interceptors which are added to the responseInterceptors list
	 * 
	 * @param intuitMessage the intuit message
	 * @throws FMSException the FMSException
	 */
	private void executeResponseInterceptors(final IntuitMessage intuitMessage) throws FMSException {
		Iterator<Interceptor> itr = responseInterceptors.iterator();
		while (itr.hasNext()) {
			Interceptor interceptor = itr.next();
			interceptor.execute(intuitMessage);
		}
	}

}
