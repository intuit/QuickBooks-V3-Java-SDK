package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.TaxService;
/**
 * Class to have request and response element objects which are being used in interceptors flow
 * 
 */
public class IntuitMessage {

	/**
	 * variable platformService
	 */
	private boolean platformService = false;
	
	/**
	 * variable requestElements
	 */
	private RequestElements requestElements = new RequestElements();

	/**
	 * variable responseElements
	 */
	private ResponseElements responseElements = new ResponseElements();
	private TaxService taxService = new TaxService();

	/**
	 * Gets requestElements
	 * 
	 * @return requestElements
	 */
	public RequestElements getRequestElements() {
		return requestElements;
	}

	/**
	 * Gets responseElements
	 * 
	 * @return responseElements
	 */
	public ResponseElements getResponseElements() {
		return responseElements;
	}

	public boolean isPlatformService() {
		return platformService;
	}

	public void setPlatformService(boolean platformService) {
		this.platformService = platformService;
	}
	public TaxService getTaxService() {
		return taxService;
	}

}
