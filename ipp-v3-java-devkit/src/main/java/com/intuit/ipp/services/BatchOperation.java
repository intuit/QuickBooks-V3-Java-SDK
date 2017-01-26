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

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.CDCQuery;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Class to prepare the BatchItemrequets and get the responses for the corresponding bIds.
 * The response is segregated to corresponding result types so that user can validate the corresponding batch item using the bId.
 *
 */
public class BatchOperation {

	/**
	 * the logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable batchItemRequests
	 */
	protected List<BatchItemRequest> batchItemRequests = new LinkedList<BatchItemRequest>();
	
	/**
	 * variable faultResult
	 */
	private Map<String, Fault> faultResult = new HashMap<String, Fault>();
	
	/**
	 * variable reportResult
	 */
	private Map<String, Report> reportResult = new HashMap<String, Report>();
	
	/**
	 * variable queryResult
	 */
	private Map<String, QueryResult> queryResult = new HashMap<String, QueryResult>();
	
	/**
	 * variable cdcQueryResult
	 */
	private Map<String, CDCQueryResult> cdcQueryResult = new HashMap<String, CDCQueryResult>();
	
	/**
	 * variable entityResult
	 */
	private Map<String, IEntity> entityResult = new HashMap<String, IEntity>();
	
	/**
	 * variable bIds
	 */
	protected List<String> bIds = new ArrayList<String>();
	
	/**
	 * Method to add the entity batch operations to the batchItemRequest
	 * 
	 * @param entity the entity
	 * @param operation the OperationEnum
	 * @param bId the batch Id
	 */
	public <T extends IEntity> void addEntity(T entity, OperationEnum operation, String bId) {
		
		BatchItemRequest batchItemRequest = new BatchItemRequest();
		batchItemRequest.setBId(bId);
		batchItemRequest.setOperation(operation);
		batchItemRequest.setIntuitObject(getIntuitObject(entity));
		
		batchItemRequests.add(batchItemRequest);
		bIds.add(bId);
	}
	
	/**
	 * Method to add the query batch operation to batchItemRequest
	 * 
	 * @param query the query
	 * @param bId the batch Id
	 */
	public void addQuery(String query, String bId) {
		
		BatchItemRequest batchItemRequest = new BatchItemRequest();
		batchItemRequest.setBId(bId);
		batchItemRequest.setQuery(query);
		
		batchItemRequests.add(batchItemRequest);
		bIds.add(bId);
	}

	/**
	 * Method to add the cdc query batch operation to batchItemRequest
	 * 
	 * @param entities the list of entities
	 * @param changedSince the date where the entities should be listed from the last changed date
	 * @param bId the batch Id
	 */
	public void addCDCQuery(List<? extends IEntity> entities, String changedSince, String bId) throws FMSException {
		
		if (entities == null || entities.isEmpty()) {
			throw new FMSException("Entities is required.");
		}
		
		if (!StringUtils.hasText(changedSince)) {
			throw new FMSException("changedSince is required.");
		}
		
		CDCQuery cdcQuery = new CDCQuery();
		
		StringBuffer entityParam = new StringBuffer();
		for (IEntity entity : entities) {
			entityParam.append(entity.getClass().getSimpleName()).append(",");
		}
		entityParam.delete(entityParam.length() - 1, entityParam.length());
		cdcQuery.setEntities(entityParam.toString());
		
		try {
			cdcQuery.setChangedSince(DateUtils.getDateFromString(changedSince));
		} catch (ParseException e) {
			LOG.error("ParseException while converting to Date.", e);
			throw new FMSException("ParseException while converting to Date. Please provide valid DateTime (yyyy-MM-ddTHH:mm:ss.SSSZ).", e);
		}
		
		BatchItemRequest batchItemRequest = new BatchItemRequest();
		batchItemRequest.setBId(bId);
		batchItemRequest.setCDCQuery(cdcQuery);
		
		batchItemRequests.add(batchItemRequest);
		bIds.add(bId);
	}
	
	/**
	 * Method to add the report query batch operation to batchItemRequest
	 *  
	 * @param reportQuery
	 * @param bId
	 */
	public void addReportQuery(String reportQuery, String bId) {
		
		BatchItemRequest batchItemRequest = new BatchItemRequest();
		batchItemRequest.setBId(bId);
		batchItemRequest.setReportQuery(reportQuery);
		
		batchItemRequests.add(batchItemRequest);
		bIds.add(bId);
	}

	/**
	 * Gets batchItemRequests
	 * 
	 * @return batchItemRequests
	 */
	public List<BatchItemRequest> getBatchItemRequests() {
		return batchItemRequests;
	}
	
	/**
	 * Gets bIds
	 * 
	 * @return bIds
	 */
	public List<String> getBIds() {
		return bIds;
	}
	
	/**
	 * Method to validate whether the batch item is fault for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public boolean isFault(String bId) {
		return faultResult.containsKey(bId);
	}
	
	/**
	 * Method to validate whether the batch item is report for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public boolean isReport(String bId) {
		return reportResult.containsKey(bId);
	}
	
	/**
	 * Method to validate whether the batch item is query for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public boolean isQuery(String bId) {
		return queryResult.containsKey(bId);
	}
	
	/**
	 * Method to validate whether the batch item is cdc query for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public boolean isCDCQuery(String bId) {
		return cdcQueryResult.containsKey(bId);
	}
	
	/**
	 * Method to validate whether the batch item is entity for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public boolean isEntity(String bId) {
		return entityResult.containsKey(bId);
	}
	
	/**
	 * Method to get the fault object for the given bId
	 * 
	 * @param bId
	 * @return boolean
	 */
	public Fault getFault(String bId) {
		return faultResult.get(bId);
	}
	
	/**
	 * Method to get the report object for the given bId
	 * 
	 * @param bId
	 * @return Report
	 */
	public Report getReport(String bId) {
		return reportResult.get(bId);
	}
	
	/**
	 * Method to get the query result object for the given bId
	 * 
	 * @param bId
	 * @return QueryResult
	 */
	public QueryResult getQueryResponse(String bId) {
		return queryResult.get(bId);
	}
	
	/**
	 * Method to get the cdc query result object for the given bId
	 * 
	 * @param bId
	 * @return CDCQueryResult
	 */
	public CDCQueryResult getCDCQueryResult(String bId) {
		return cdcQueryResult.get(bId);
	}
	
	/**
	 * Method to get the entity object for the given bId
	 * 
	 * @param bId
	 * @return IEntity
	 */
	public IEntity getEntity(String bId) {
		return entityResult.get(bId);
	}
	
	/**
	 * Gets faultResult list
	 * @return faultResult
	 */
	public Map<String, Fault> getFaultResult() {
		return faultResult;
	}

	/**
	 * Gets reportResult list
	 * @return reportResult
	 */
	public Map<String, Report> getReportResult() {
		return reportResult;
	}

	/**
	 * Gets queryResult list
	 * @return queryResult
	 */
	public Map<String, QueryResult> getQueryResult() {
		return queryResult;
	}

	/**
	 * Gets cdcQueryResult list
	 * @return cdcQueryResult
	 */
	public Map<String, CDCQueryResult> getCDCQueryResult() {
		return cdcQueryResult;
	}
	
	/**
	 * Gets entityResult list
	 * @return entityResult
	 */
	public Map<String, IEntity> getEntityResult() {
		return entityResult;
	}
	
	/**
	 * Method to get the corresponding IEntity type for the given JAXBElement
	 * 
	 * @param entity
	 * @return entity
	 */
	@SuppressWarnings("unchecked")
	protected <T> JAXBElement<? extends IntuitEntity> getIntuitObject(T entity) {
		Class<?> objectClass = entity.getClass();
		String methodName = "create".concat(objectClass.getSimpleName());
		ObjectFactory objectEntity = new ObjectFactory();
		Class<?> objectEntityClass = objectEntity.getClass();

		Method method = null;
		try {
			method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
		} catch (Exception e) {
			LOG.error("Exception while prepare the method signature using reflection to generate JAXBElement", e);
		}
		JAXBElement<? extends IntuitEntity> jaxbElement = null;
		try {
			jaxbElement = (JAXBElement<? extends IntuitEntity>) method.invoke(objectEntity, entity);
		} catch (Exception e) {
			LOG.error("Exception while invoking the method using reflection to generate JAXBElement", e);
		}
		
		return jaxbElement;
	}

}
