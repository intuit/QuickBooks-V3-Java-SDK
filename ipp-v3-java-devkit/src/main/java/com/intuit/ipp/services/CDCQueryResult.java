package com.intuit.ipp.services;

import java.util.Map;

import com.intuit.ipp.data.Fault;

/**
 * Class to hold the cdc query response details
 *
 */
public class CDCQueryResult {

	/**
	 * variable queryResults
	 */
	private Map<String, QueryResult> queryResults;
	
	/**
	 * variable falut
	 */
	private Fault falut;
	
	/**
	 * variable size
	 */
	private Integer size;

	/**
	 * Gets queryResults
	 * 
	 * @return queryResults
	 */
	public Map<String, QueryResult> getQueryResults() {
		return queryResults;
	}

	/**
	 * Sets queryResults
	 * @param queryResults
	 */
	public void setQueryResults(Map<String, QueryResult> queryResults) {
		this.queryResults = queryResults;
	}

	/**
	 * Gets falut
	 * 
	 * @return falut
	 */
	public Fault getFalut() {
		return falut;
	}

	/**
	 * Sets falut
	 * @param falut
	 */
	public void setFalut(Fault falut) {
		this.falut = falut;
	}

	/**
	 * Gets size
	 * 
	 * @return size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * Sets size
	 * @param size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
	
}
