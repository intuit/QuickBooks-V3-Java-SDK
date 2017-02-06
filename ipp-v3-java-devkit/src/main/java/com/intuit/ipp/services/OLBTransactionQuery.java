package com.intuit.ipp.services;

/**
 * Class to prepare the query params for OLB Transaction.
 *
 */
/**
 * @author PVIJAYAKUMAR
 *
 */
/**
 * @author PVIJAYAKUMAR
 *
 */
public class OLBTransactionQuery {
	

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the numberofdays
	 */
	public String getNumberofdays() {
		return numberofdays;
	}

	/**
	 * @param numberofdays the numberofdays to set
	 */
	public void setNumberofdays(String numberofdays) {
		this.numberofdays = numberofdays;
	}

	/**
	 * @return the startposition
	 */
	public String getStartposition() {
		return startposition;
	}

	/**
	 * @param startposition the startposition to set
	 */
	public void setStartposition(String startposition) {
		this.startposition = startposition;
	}

	/**
	 * @return the maxresults
	 */
	public String getMaxresults() {
		return maxresults;
	}

	/**
	 * @param maxresults the maxresults to set
	 */
	public void setMaxresults(String maxresults) {
		this.maxresults = maxresults;
	}

	/**
	 * comma separated list ig account IDs
	 */
	private String accountId;

	/**
	 * number of days of account information required since the last posting date
	 */
	private String numberofdays;

	/**
	 * starting row number for pagination
	 */
	private String startposition;

	/**
	 * number of rows that are to be returned for the query
	 */
	private String maxresults;

}

