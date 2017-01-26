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
package com.intuit.ipp.interceptors;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.net.OperationType;
import com.intuit.ipp.services.BatchOperation;
import com.intuit.ipp.services.CDCQueryResult;
import com.intuit.ipp.services.CallbackMessage;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor class to prepare the response and call the callback method
 * 
 */
public class CallbackHandlerInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {

		LOG.debug("Enter CallbackHandlerInterceptor...");

		CallbackMessage callbackMessage = intuitMessage.getResponseElements().getCallbackMessage();

		Response response = intuitMessage.getResponseElements().getResponse();
		if (response != null) {
			IntuitResponse intuitResponse = (IntuitResponse) response;
			// get IntuitObject and convert to IEntity
			JAXBElement<? extends IntuitEntity> intuitObject = intuitResponse.getIntuitObject();
			if (intuitObject != null) {
				LOG.debug("CallbackHandlerInterceptor setting IntuitObject...");
				callbackMessage.setEntity((IEntity) intuitObject.getValue());
			}

			// get QueryResponse and convert to IEntity list
			QueryResponse queryResponse = intuitResponse.getQueryResponse();
			if (queryResponse != null) {
				LOG.debug("CallbackHandlerInterceptor setting QueryResponse...");
				QueryResult queryResult = getQueryResult(queryResponse);
				
				// sets callbackMessage object
				callbackMessage.setQueryResult(queryResult);
			}
			
			// get CDCQueryResponse and convert to IEntity list
			List<CDCResponse> cdcResponses = intuitResponse.getCDCResponse();
			if (cdcResponses != null) {
				LOG.debug("CallbackHandlerInterceptor setting CDCQueryResponse...");
				List<CDCQueryResult> cdcQueryResults = getCDCQueryResult(cdcResponses);
				
				// sets callbackMessage object
				callbackMessage.setCDCQueryResults(cdcQueryResults);
			}
			
			// get AttachableResponse and convert to IEntity list
			List<AttachableResponse> attachableResponses = intuitResponse.getAttachableResponse();
			if (attachableResponses != null) {
				LOG.debug("CallbackHandlerInterceptor setting AttachableResponse...");
				// sets callbackMessage object
				callbackMessage.setAttachableResponse(attachableResponses);
			}
			
			// get BatchItemResponse and covert to corresponding results
			List<BatchItemResponse> batchItemResponses = intuitResponse.getBatchItemResponse();
			if (batchItemResponses != null && !batchItemResponses.isEmpty()) {
				LOG.debug("CallbackHandlerInterceptor setting BatchItemResponse...");
				BatchOperation batchOperation = intuitMessage.getRequestElements().getBatchOperation();
				int count = 0;
				Iterator<BatchItemResponse> itr = batchItemResponses.iterator();
				while (itr.hasNext()) {
					BatchItemResponse batchItemResponse = itr.next();
					String bId = batchItemResponse.getBId();
					if (!StringUtils.hasText(bId)) {
						bId = batchOperation.getBatchItemRequests().get(count).getBId();
					}
					if (batchItemResponse.getFault() != null) {
						// segregate fault batch items
						batchOperation.getFaultResult().put(bId, batchItemResponse.getFault());
					} else if (batchItemResponse.getReport() != null) {
						// segregate report batch items
						batchOperation.getReportResult().put(bId, batchItemResponse.getReport());
					} else if (batchItemResponse.getIntuitObject() != null) {
						// segregate entity batch items
						batchOperation.getEntityResult().put(bId, (IEntity) batchItemResponse.getIntuitObject().getValue());
					} else if (batchItemResponse.getQueryResponse() != null) {
						// segregate query batch items
						QueryResult queryResult = getQueryResult(batchItemResponse.getQueryResponse());
						batchOperation.getQueryResult().put(bId, queryResult);
					} else if (batchItemResponse.getCDCResponse() != null) {
						// segregate cdc query batch items
						CDCQueryResult cdcQueryResult = getCDCQueryResult(batchItemResponse.getCDCResponse());
						batchOperation.getCDCQueryResult().put(bId, cdcQueryResult);
					} else {
						LOG.warn("BatchItemResponse is not Fault, Entity, Query and Report.");
					}
					count++;
				}
				// sets batch operation
				callbackMessage.setBatchOperation(batchOperation);
			}
		} else if (isDownload(intuitMessage.getRequestElements().getAction())) {
			LOG.debug("CallbackHandlerInterceptor setting downloadedFile...");
			callbackMessage.setDownloadedFile(getDownloadedFile(intuitMessage.getResponseElements().getDecompressedData()));
		} else if (isDownloadPDF(intuitMessage.getRequestElements().getRequestParameters())) {
            LOG.debug("CallbackHandlerInterceptor setting binary content for PDF...");
            callbackMessage.setDownloadedFile(intuitMessage.getResponseElements().getResponseBytes());
        }

		// calls callback method
		intuitMessage.getRequestElements().getCallbackHandler().execute(callbackMessage);
		LOG.debug("Exit CallbackHandlerInterceptor.");
	}

	/**
	 * Method to parse the QueryResponse and create the entity list
	 * 
	 * @param queryResponse the query response
	 * @return List<T> returns list of entity
	 */
	@SuppressWarnings("unchecked")
	private <T extends IEntity> List<T> getEntities(QueryResponse queryResponse) {
		List<T> entityList = new ArrayList<T>();
		List<JAXBElement<? extends IntuitEntity>> intuitObjectsList = queryResponse.getIntuitObject();
		
		// Iterate the IntuitObjects list in QueryResponse and convert to <T> entity 
		if (intuitObjectsList != null && !intuitObjectsList.isEmpty()) {
			Iterator<JAXBElement<? extends IntuitEntity>> itr = intuitObjectsList.iterator();
			while (itr.hasNext()) {
				JAXBElement<? extends IntuitEntity> intuitObject = itr.next();
				entityList.add((T) intuitObject.getValue());
			}
		}
		
		return entityList;
	}
	
	/**
	 * Method to read the query response from QueryResponse and set into QueryResult
	 * 
	 * @param queryResponse the query response
	 * @return queryResult
	 */
	private QueryResult getQueryResult(QueryResponse queryResponse) {
		QueryResult queryResult = null;
		if (queryResponse != null) {
			queryResult = new QueryResult();
			queryResult.setEntities(getEntities(queryResponse));
			queryResult.setFault(queryResponse.getFault());
			queryResult.setMaxResults(queryResponse.getMaxResults());
			queryResult.setStartPosition(queryResponse.getStartPosition());
			queryResult.setTotalCount(queryResponse.getTotalCount());
		}

		return queryResult;
	}
	
	/**
	 * Method to get the list of CDCQueryResult object from list of CDCResponse
	 * 
	 * @param cdcResponses the cdc responses list
	 * @return list of CDCQueryResult object
	 */
	private List<CDCQueryResult> getCDCQueryResult(List<CDCResponse> cdcResponses) {
		List<CDCQueryResult> cdcQueryResults = null;
		if (cdcResponses != null) {
			Iterator<CDCResponse> cdcResponseItr = cdcResponses.iterator();
			while (cdcResponseItr.hasNext()) {
				cdcQueryResults = new ArrayList<CDCQueryResult>();
				CDCQueryResult cdcQueryResult = getCDCQueryResult(cdcResponseItr.next());
				cdcQueryResults.add(cdcQueryResult);
			}
		}
		
		return cdcQueryResults;
	}
	
	/**
	 * Method to construct and return the CDCQueryResult object from CDCResponse
	 * 
	 * @param cdcResponse
	 *            the CDC Response object
	 * @return the CDCQueryResult object
	 */
	private CDCQueryResult getCDCQueryResult(CDCResponse cdcResponse) {
		CDCQueryResult cdcQueryResult = new CDCQueryResult();
		List<QueryResponse> queryResponses = cdcResponse.getQueryResponse();
		if (queryResponses != null) {
			Map<String, QueryResult> queryResults = new HashMap<String, QueryResult>();
			Iterator<QueryResponse> queryResponseItr = queryResponses.iterator();
			while (queryResponseItr.hasNext()) {
				QueryResponse queryResponse = queryResponseItr.next();
				QueryResult queryResult = getQueryResult(queryResponse);
				populateQueryResultsInCDC(queryResults, queryResult);
				populateFaultInCDC(cdcQueryResult, queryResult);
			}
			if (queryResults != null && !queryResults.isEmpty()) {
				cdcQueryResult.setQueryResults(queryResults);
				cdcQueryResult.setSize(cdcResponse.getSize());
			}
		} else if (cdcResponse.getFault() != null) {
			cdcQueryResult.setFalut(cdcResponse.getFault());
		}

		return cdcQueryResult;
	}

	/**
	 * Method to populate the QueryResults hash map by reading the key from QueryResult entities
	 * 
	 * @param queryResults
	 *            the queryResults hash map to be populated
	 * @param queryResult
	 *            the QueryResult object
	 */
	private void populateQueryResultsInCDC(Map<String, QueryResult> queryResults, QueryResult queryResult) {
		if (queryResult != null) {
			List<? extends IEntity> entities = queryResult.getEntities();
			if (entities != null && !entities.isEmpty()) {
				IEntity entity = entities.get(0);
				String entityName = entity.getClass().getSimpleName();
				queryResults.put(entityName, queryResult);
			}
		}
	}
	
	/**
	 * Method to populate the fault in CDCQueryResult if any
	 * 
	 * @param cdcQueryResult the CDCQueryResult
	 * @param queryResult the QueryResult
	 */
	private void populateFaultInCDC(CDCQueryResult cdcQueryResult, QueryResult queryResult) {
		if (queryResult != null) {
			Fault fault = queryResult.getFault();
			if (fault != null) {
				cdcQueryResult.setFalut(fault);
			}
		}
	}
	
	/**
	 * Method to validate if the given action is download feature
	 * 
	 * @param action the action type
	 * @return boolean returns true if the action is download 
	 */
	private boolean isDownload(String action) {
		if (StringUtils.hasText(action) && action.equals(OperationType.DOWNLOAD.toString())) {
			return true;
		}
		return false;
	}

    private boolean isDownloadPDF(Map<String, String> requestHeaders) {
        return StringUtils.hasText(requestHeaders.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR))
                && requestHeaders.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR).equalsIgnoreCase(ContentTypes.PDF.name());
    }
	
	/**
	 * Method to get the input stream from the download URL returned from service
	 *  
	 * @param response the download URL string
	 * @return InputStream the downloaded file
	 * @throws FMSException
	 */
	private InputStream getDownloadedFile(String response) throws FMSException {
		if (response != null) {
			try {
				URL url = new URL(response);
				return url.openStream();
			} catch (Exception e) {
				throw new FMSException("Exception while downloading the file from URL.", e);
			}
		}
		
		return null;
	}
	
}
