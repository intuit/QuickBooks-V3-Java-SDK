/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
