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

import java.util.List;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.Fault;

/**
 * Class to hold the query response details
 *
 */
public class QueryResult {

	/**
	 * variable fault
	 */
	private Fault fault;

	/**
	 * variable entities
	 */
	private List<? extends IEntity> entities;

	/**
	 * variable 
	 */
	private Integer startPosition;

	/**
	 * variable startPosition
	 */
	private Integer maxResults;

	/**
	 * variable totalCount
	 */
	private Integer totalCount;

	/**
	 * Gets fault
	 * 
	 * @return fault
	 */
	public Fault getFault() {
		return fault;
	}

	/**
	 * Sets fault
	 * 
	 * @param fault
	 */
	public void setFault(Fault fault) {
		this.fault = fault;
	}

	/**
	 * Gets entities
	 * 
	 * @return entities
	 */
	public List<? extends IEntity> getEntities() {
		return entities;
	}

	/**
	 * Sets entities
	 * 
	 * @param entities
	 */
	public void setEntities(List<? extends IEntity> entities) {
		this.entities = entities;
	}

	/**
	 * Gets startPosition
	 * 
	 * @return startPosition
	 */
	public Integer getStartPosition() {
		return startPosition;
	}

	/**
	 * Sets startPosition
	 * 
	 * @param startPosition
	 */
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Gets maxResults
	 * 
	 * @return maxResults
	 */
	public Integer getMaxResults() {
		return maxResults;
	}

	/**
	 * Sets maxResults
	 * 
	 * @param maxResults
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * Gets totalCount
	 * 
	 * @return totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * Sets totalCount
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
