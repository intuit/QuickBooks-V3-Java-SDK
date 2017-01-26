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

import java.io.InputStream;
import java.util.List;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.exception.FMSException;

/**
 * Class to hold callback messages which are to be passed to callback method
 *
 */
public class CallbackMessage {

	/**
	 * variable entity
	 */
	private IEntity entity;
	
	/**
	 * variable queryResult
	 */
	private QueryResult queryResult;

	/**
	 * variable cdcQueryResults
	 */
	private List<CDCQueryResult> cdcQueryResults;
	
	/**
	 * variable fmsException
	 */
	private FMSException fmsException;
	
	/**
	 * variable batchOperation
	 */
	private BatchOperation batchOperation;
	
	/**
	 * variable attachableResponse
	 */
	private List<AttachableResponse> attachableResponse;
	
	/**
	 * variable downloadedFile
	 */
	private InputStream downloadedFile;
	
	/**
	 * Gets entity 
	 * 
	 * @return entity
	 */
	public IEntity getEntity() {
		return entity;
	}

	/**
	 * Sets entity
	 * 
	 * @param entity
	 */
	public void setEntity(IEntity entity) {
		this.entity = entity;
	}

	/**
	 * Gets QueryResult 
	 * 
	 * @return QueryResult
	 */
	public QueryResult getQueryResult() {
		return queryResult;
	}

	/**
	 * Sets CDCQueryResult
	 * 
	 * @param List<CDCQueryResult>
	 */
	public void setCDCQueryResults(List<CDCQueryResult> cdcQueryResults) {
		this.cdcQueryResults = cdcQueryResults;
	}

	/**
	 * Gets List<CDCQueryResult> 
	 * 
	 * @return List<CDCQueryResult>
	 */
	public List<CDCQueryResult> getCDCQueryResults() {
		return cdcQueryResults;
	}

	/**
	 * Sets QueryResult
	 * 
	 * @param QueryResult
	 */
	public void setQueryResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}
	
	/**
	 * Gets FMSException 
	 * 
	 * @return FMSException
	 */
	public FMSException getFMSException() {
		return fmsException;
	}

	/**
	 * Sets FMSException
	 * 
	 * @param FMSException
	 */
	public void setFMSException(FMSException fmsException) {
		this.fmsException = fmsException;
	}
	
	/**
	 * Gets batch operation
	 * 
	 * @return returns batch operation
	 */
	public BatchOperation getBatchOperation() {
		return batchOperation;
	}

	/**
	 * Sets batch operation
	 * 
	 * @param batch operation
	 */
	public void setBatchOperation(BatchOperation batchOperation) {
		this.batchOperation = batchOperation;
	}
	
	/**
	 * Gets attachable response
	 * 
	 * @return returns attachable response
	 */
	public List<AttachableResponse> getAttachableResponse() {
		return attachableResponse;
	}

	/**
	 * Sets attachable response
	 * 
	 * @param attachable response
	 */
	public void setAttachableResponse(List<AttachableResponse> attachableResponse) {
		this.attachableResponse = attachableResponse;
	}

	/**
	 * Gets downloadedFile
	 * 
	 * @return returns downloadedFile
	 */
	public InputStream getDownloadedFile() {
		return downloadedFile;
	}

	/**
	 * Sets downloadedFile
	 * 
	 * @param downloadedFile
	 */
	public void setDownloadedFile(InputStream downloadedFile) {
		this.downloadedFile = downloadedFile;
	}
	
}
