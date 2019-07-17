/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.services;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBElement;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.CDCResponse;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitBatchRequest;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.interceptors.IntuitBatchInterceptorProvider;
import com.intuit.ipp.interceptors.IntuitInterceptorProvider;
import com.intuit.ipp.interceptors.IntuitMessage;
import com.intuit.ipp.interceptors.RequestElements;
import com.intuit.ipp.interceptors.UploadRequestElements;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.net.MethodType;
import com.intuit.ipp.net.OperationType;
import com.intuit.ipp.net.UploadEntry;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Class which has common methods for CRUD operations
 * 
 */
public class DataService {

    /**
     * the logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();

    /**
     * variable LENGTH_200
     */
    private static final int LENGTH_200 = 200;



    /**
     * variable context
     */
    private transient Context context = null;


    /**
     * Hiding the default constructor as Context is always required to function properly
     */
    protected DataService() {

    }

    /**
     * Constructor DataService
     *
     * @param context
     *            the context
     */
    public DataService(final Context context) {
        this.context = context;
    }

    /**
     * Method to retrieve all records for the given entity
     * Note, without pagination this will return only 100 records
     * Use query API to add pagintion and obtain additional records
     *
     * @param entity
     *            the entity
     * @return returns the queryResult
     * @throws FMSException
     *             throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> List<T> findAll(T entity) throws FMSException {

        String intuitQuery = "SELECT * FROM " + entity.getClass().getSimpleName();
        QueryResult result = executeQuery(intuitQuery);
        return (List<T>) result.getEntities();
    }
    
    @SuppressWarnings("unchecked")
    public QueryResult findAllTaxClassification() throws FMSException {

        String intuitQuery = "SELECT * FROM TaxClassification";
        QueryResult result = executeQuery(intuitQuery);
        return result;
    }

    /**
     * Method to add the given entity
     *
     * @param entity
     *            the entity
     * @return returns the added entity
     * @throws FMSException
     *             throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T add(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareAdd(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }

    /**
     * Invokes interceptors, which perform networking operations (serialization, compression, connection etc)
     * @param intuitMessage
     * @throws FMSException
     */
    protected void executeInterceptors(IntuitMessage intuitMessage) throws FMSException {
        new IntuitInterceptorProvider().executeInterceptors(intuitMessage);
    }

    /**
     * Invokes interceptors, which perform networking operations (serialization, compression, connection etc)
     * @param intuitMessages
     * @throws FMSException
     */
    protected void executeInterceptors(List<IntuitMessage> intuitMessages) throws FMSException {
      new IntuitBatchInterceptorProvider().executeInterceptors(intuitMessages);
    }

    /**
     * Invokes async interceptors, which creates thread and perform networking
     * @param intuitMessage
     */
    protected void executeAsyncInterceptors(IntuitMessage intuitMessage) {
        new IntuitInterceptorProvider().executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to delete record for the given entity
     *
     * @param entity
     *            the entity
     * @return returns deleted y status
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T delete(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareDelete(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }

    /**
     * Method to update the record of the corresponding entity
     *
     * @param entity
     *            the entity
     * @return returns upadated status
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T update(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareUpdate(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }

    //updateAccountOnTxns used for France Locale with Minor Version >= 5.
    
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T updateAccountOnTxns(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareupdateAccountOnTxns(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }
    
    //donotUpdateAccountOnTxns used for France Locale with Minor Version >= 5.
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T donotUpdateAccountOnTxns(T entity) throws FMSException {

        IntuitMessage intuitMessage = preparedonotUpdateAccountOnTxns(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }
    /**
     * Common method to retrieve result entity from IntuitMessage
     * @param intuitMessage
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T extends IEntity> T retrieveEntity(IntuitMessage intuitMessage) {
        T returnEntity = null;
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            JAXBElement<? extends IntuitEntity> intuitObject = intuitResponse.getIntuitObject();
            if (intuitObject != null) {
                returnEntity = ((T) intuitObject.getValue());
            }
        }
        return returnEntity;
    }

    /**
     * Method to find the record for the given id for the corresponding entity
     *
     * @param entity
     * @return returns the entity
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T findById(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareFindById(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        T returnEntity = null;
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            //QBD service is returning object as QueryResponse while QBO is returning object as IntuitObject in IntuitResponse

            if (intuitResponse.getIntuitObject() != null) {
                JAXBElement<? extends IntuitEntity> intuitObject = intuitResponse.getIntuitObject();
                if (intuitObject != null) {
                    returnEntity = ((T) intuitObject.getValue());
                }
            } else if (intuitResponse.getQueryResponse() != null) {
                QueryResponse queryResponse = intuitResponse.getQueryResponse();
                List<JAXBElement<? extends IntuitEntity>> intuitObject = queryResponse.getIntuitObject();
                if (intuitObject != null && intuitObject.size() > 0) {
                    returnEntity = ((T) intuitObject.get(0).getValue());
                }
            }
        }
        return returnEntity;
    }
    
    /**
     * Method to find the record for the given id for the corresponding entity
     *
     * @param entity
     * @return returns the entity
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public QueryResult findTaxClassificationByParentId(IEntity entity) throws FMSException {

        IntuitMessage intuitMessage = prepareFindByParentId(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        QueryResult queryResult = null;

        // Iterate the IntuitObjects list in QueryResponse and convert to <T> entity
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            QueryResponse queryResponse = intuitResponse.getQueryResponse();
            if (queryResponse != null) {
                queryResult = getQueryResult(queryResponse);
            }
        }
        return queryResult;
    }
    
    /**
     * Method to find the record for the given id for the corresponding entity
     *
     * @param entity
     * @return returns the entity
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public QueryResult findTaxClassificationByLevel(IEntity entity) throws FMSException {

        IntuitMessage intuitMessage = prepareFindByLevel(entity);
 
        //execute interceptors
        executeInterceptors(intuitMessage);
        
        QueryResult queryResult = null;

        // Iterate the IntuitObjects list in QueryResponse and convert to <T> entity
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            QueryResponse queryResponse = intuitResponse.getQueryResponse();
            if (queryResponse != null) {
                queryResult = getQueryResult(queryResponse);
            }
        }
        return queryResult;
    }
    


    /**
     * Method to cancel the operation for the corresponding entity
     *
     * @param entity
     * @return entity the entity
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T voidRequest(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareVoidRequest(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }

    /**
     * Method to upload the given document content for the corresponding entity
     *
     * @param entity the entity
     * @return returns the entity
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T upload(T entity, InputStream docContent) throws FMSException {

        IntuitMessage intuitMessage = prepareUpload(entity, docContent);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) getReturnEntity(intuitMessage);
    }
    @SuppressWarnings("unchecked")
    private <T extends IEntity> T getReturnEntity(IntuitMessage intuitMessage) {
        return (T) getReturnEntity(intuitMessage, 0);
    }

    @SuppressWarnings("unchecked")
    private <T extends IEntity> T getReturnEntity(IntuitMessage intuitMessage, int idx) {
        T returnEntity = null;
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            if (intuitResponse.getIntuitObject() != null) {
                JAXBElement<? extends IntuitEntity> intuitObject = intuitResponse.getIntuitObject();
                if (intuitObject != null) {
                    returnEntity = ((T) intuitObject.getValue());
                }
            } else if (intuitResponse.getQueryResponse() != null) {
                QueryResponse queryResponse = intuitResponse.getQueryResponse();
                List<JAXBElement<? extends IntuitEntity>> intuitObject = queryResponse.getIntuitObject();
                if (intuitObject != null && intuitObject.size() > 0) {
                    returnEntity = ((T) intuitObject.get(idx).getValue());
                }
            } else if (intuitResponse.getAttachableResponse() != null) {
                List<AttachableResponse> attachableResponse = intuitResponse.getAttachableResponse();
                if (attachableResponse != null && attachableResponse.size() > 0) {
                    returnEntity = ((T) attachableResponse.get(idx).getAttachable());
                }
            }
        }
        return returnEntity;
    }


    /**
     * Method to upload entities with their correspond binary
     * @param entries
     * @param <T>
     * @return
     * @throws FMSException
     */
    public <T extends IEntity> List<T> upload(List<UploadEntry> entries) throws FMSException {


        List<IntuitMessage> intuitMessages = prepareUpload(entries);

        //execute interceptors
        if(!intuitMessages.isEmpty()) {
            executeInterceptors(intuitMessages);
        }

        return getResultEntities(intuitMessages);

    }

    /**
     * Processes list of intuitMessages and returns list of resultEntities
     * @param intuitMessages
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T extends IEntity> List<T> getResultEntities(List<IntuitMessage> intuitMessages) {
        List<T> resultEntities = new ArrayList<T>();
        int i = 0;
        for(IntuitMessage intuitMessage : intuitMessages) {
            if(!isContainResponse(intuitMessage, i)) {
                LOG.warn("Response object with index="+i+" was expected, got nothing.");
            } else {
                resultEntities.add((T) getReturnEntity(intuitMessage, i));
            }
            i++;
        }

        return resultEntities;
    }

    /**
     * Creates list of IntuitMessage instances based on list of entries
     * @param entries items and files which are going to be uploaded
     * @return list of intuitMessage instances
     * @throws FMSException
     */
    private List<IntuitMessage> prepareUpload(List<UploadEntry> entries) throws FMSException {
        List<IntuitMessage> intuitMessages = new ArrayList<IntuitMessage>();

        String boundaryId = null;
        for(UploadEntry item : entries) {
            if(item.isEmpty()) {
                LOG.warn("UploadEntry instance (hash:"+System.identityHashCode(item)+") has at least one null property. " +
                        "It should have intuit entity and InputStream instances");
            }
            IntuitMessage intuitMessage = prepareUpload(item.getEntity(), item.getStream(), boundaryId);

            // To keep boundary ID for next attachments
            if(null == boundaryId) {
                boundaryId = intuitMessage.getRequestElements().getUploadRequestElements().getBoundaryId();
            }
            intuitMessages.add(intuitMessage);
        }
        return intuitMessages;
    }

    /**
     * verifies availability of an object in response
     * @param intuitMessage
     * @param idx
     * @return
     */
    private boolean isContainResponse(IntuitMessage intuitMessage, int idx)
    {
        List<AttachableResponse> response = ((IntuitResponse) intuitMessage.getResponseElements().getResponse()).getAttachableResponse();
        if(null == response)          { return false; }
        if(0    >= response.size() )  { return false; }
        if(idx  >= response.size() )  { return false; }
        return true;
    }


    /**
     * Method to find the record for the given id for the corresponding entity
     *
     * @param entity
     * @return returns the entity
     * @throws FMSException
     */
    public <T extends IEntity> InputStream download(T entity) throws FMSException {

        IntuitMessage intuitMessage = prepareDownload(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        String response = intuitMessage.getResponseElements().getDecompressedData();
        if (response != null) {
            try {
                URL url = new URL(response);
                return url.openStream();
            } catch (Exception e) {
                throw new FMSException("Exception while downloading the input file from URL.", e);
            }
        }

        return null;
    }

    public <T extends IEntity> InputStream downloadPDF(T entity) throws FMSException {
        if(!isAvailableAsPDF(entity)) {
            throw new FMSException("Following entity: " + entity.getClass().getSimpleName() + " cannot be exported as PDF " );
        }
        IntuitMessage intuitMessage = prepareDownloadPDF(entity);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return intuitMessage.getResponseElements().getResponseBytes();

    }

    /**
     * Send entity via email using address associated with this entity in the system
     * @param entity
     * @param <T>
     * @return
     * @throws FMSException
     */
    public <T extends IEntity> T sendEmail(T entity) throws FMSException {
        return sendEmail(entity, null);
    }

    /**
     * Send entity via email using specified address
     * @param entity
     * @param email
     * @param <T>
     * @return
     * @throws FMSException
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T sendEmail(T entity, String email) throws FMSException {
        if(!isAvailableToEmail(entity)) {
            throw new FMSException("Following entity: " + entity.getClass().getSimpleName() + " cannot be send as email" );
        }
        IntuitMessage intuitMessage = prepareEmail(entity, email);

        //execute interceptors
        executeInterceptors(intuitMessage);

        return (T) retrieveEntity(intuitMessage);
    }

    /**
     * Method verifies that entity is included in special list which might have additional features (like export as pdf)
     * @param entity
     * @param <T>
     * @return
     */
    private <T extends IEntity> boolean isSpecialEntity(T entity) {
        return (entity instanceof Estimate)
                || (entity instanceof Invoice)
                || (entity instanceof SalesReceipt);
    }

    /**
     * Returns true if the entity can be downloaded as PDF
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends IEntity> boolean isAvailableAsPDF(T entity)
    {
        return isSpecialEntity(entity);
    }


    /**
     * Returns true if the entity can be send as email
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends IEntity> boolean isAvailableToEmail(T entity)
    {
        return isSpecialEntity((T) entity);
    }



    /**
     * Method to retrieve records for the given list of query
     *
     * @param query
     *            the query string
     * @return query result
     * @throws FMSException
     *             throws FMSException
     */
    public QueryResult executeQuery(String query) throws FMSException {

        IntuitMessage intuitMessage = prepareQuery(query);

        // execute interceptors
        executeInterceptors(intuitMessage);

        QueryResult queryResult = null;

        // Iterate the IntuitObjects list in QueryResponse and convert to <T> entity
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            QueryResponse queryResponse = intuitResponse.getQueryResponse();
            if (queryResponse != null) {
                queryResult = getQueryResult(queryResponse);
            }
        }
        return queryResult;
    }

    /**
     * Method to retrieve the list of records for the given entities whose last modified date is greater than the given changedSince date
     *
     * @param entities the list of entities to be listed in the response
     * @param changedSince the date where the entities should be listed from the last changed date
     * @return the list of CDCQueryResult object
     * @throws FMSException throws FMSException
     */
    public List<CDCQueryResult> executeCDCQuery(List<? extends IEntity> entities, String changedSince) throws FMSException {

        if (entities == null || entities.isEmpty()) {
            throw new FMSException("Entities is required.");
        }

        if (!StringUtils.hasText(changedSince)) {
            throw new FMSException("changedSince is required.");
        }

        IntuitMessage intuitMessage = prepareCDCQuery(entities, changedSince);

        // execute interceptors
        executeInterceptors(intuitMessage);

        List<CDCQueryResult> cdcQueryResults = null;

        // Iterate the IntuitObjects list in QueryResponse and convert to <T> entity
        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            List<CDCResponse> cdcResponses = intuitResponse.getCDCResponse();
            if (cdcResponses != null) {
                cdcQueryResults = getCDCQueryResult(cdcResponses);
            }
        }
        return cdcQueryResults;
    }

    /**
     * Method to execute the batch operation
     *
     * @param batchOperation
     *            the batch operation
     * @throws FMSException
     *             throws FMSException
     */
    public void executeBatch(BatchOperation batchOperation) throws FMSException {

        IntuitMessage intuitMessage = prepareBatch(batchOperation);

        //execute interceptors
        executeInterceptors(intuitMessage);

        IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
        if (intuitResponse != null) {
            List<BatchItemResponse> batchItemResponses = intuitResponse.getBatchItemResponse();
            if (batchItemResponses != null && !batchItemResponses.isEmpty()) {
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
            }
        }
    }

    /**
     * Method to retrieve all records for the given entity in asynchronous fashion
     * Note, without pagination this will return only 100 records
     * Use query API to add pagintion and obtain additional records
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void findAllAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        //findall is to be called as query
        String query = "SELECT * FROM " + entity.getClass().getSimpleName();
        executeQueryAsync(query, callbackHandler);
    }

    /**
     * Method to add the given entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void addAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareAdd(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }



    /**
     * Method to delete record for the given entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public <T extends IEntity> void deleteAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareDelete(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to update the record of the corresponding entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public <T extends IEntity> void updateAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareUpdate(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to find the record for the given id for the corresponding entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public <T extends IEntity> void findByIdAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareFindById(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to cancel the operation for the corresponding entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public <T extends IEntity> void voidRequestAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareVoidRequest(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to upload the file for the given entity in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param docContent
     *            the content of the file to upload
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void uploadAsync(T entity, InputStream docContent, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareUpload(entity, docContent);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to download the file for the given entity id in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void downloadAsync(T entity, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareDownload(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }


    /**
     * Method to download the file for the given entity id in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void downloadPDFAsync(T entity, CallbackHandler callbackHandler) throws FMSException {
        if(!isAvailableAsPDF(entity)) {
            throw new FMSException("Following entity: " + entity.getClass().getSimpleName() + " cannot be exported as PDF (Async) " );
        }
        IntuitMessage intuitMessage = prepareDownloadPDF(entity);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }


    /**
     *  Method to send the entity to default email for the given id in asynchronous fashion
     * @param entity
     * @param callbackHandler
     * @param <T>
     * @throws FMSException
     */
    public <T extends IEntity> void sendEmailAsync(T entity, CallbackHandler callbackHandler) throws FMSException {
        sendEmailAsync(entity,null,callbackHandler);
    }

    /**
     * Method to send the entity to email for the given id in asynchronous fashion
     *
     * @param entity
     *            the entity
     * @param email
     *            the mail string
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     *             throws FMSException
     */
    public <T extends IEntity> void sendEmailAsync(T entity, String email, CallbackHandler callbackHandler) throws FMSException {
        if(!isAvailableToEmail(entity)) {
            throw new FMSException("Following entity: " + entity.getClass().getSimpleName() + " cannot send as email (Async) " );
        }
        IntuitMessage intuitMessage = prepareEmail(entity, email);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }



    /**
     * Method to retrieve records for the given list of query in asynchronous fashion
     *
     * @param query
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public void executeQueryAsync(String query, CallbackHandler callbackHandler) throws FMSException {
        IntuitMessage intuitMessage = prepareQuery(query);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Method to retrieve records for the given list of query in asynchronous fashion
     *
     * @param entities the list of entities to be listed in the response
     * @param changedSince the date where the entities should be listed from the last changed date
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public void executeCDCQueryAsync(List<? extends IEntity> entities, String changedSince, CallbackHandler callbackHandler) throws FMSException {
        IntuitMessage intuitMessage = prepareCDCQuery(entities, changedSince);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }
    
    public EntitlementsResponse getEntitlements() throws FMSException {
    	
    	//prepare request
    	IntuitMessage intuitMessage = prepareEntitlementsRequest();

        //execute interceptors
        executeInterceptors(intuitMessage);

        //return response
        EntitlementsResponse entitlementsResponse = (EntitlementsResponse) intuitMessage.getResponseElements().getResponse();
        return entitlementsResponse;
    }

    /**
     * Method to cancel the operation for the corresponding entity in asynchronous fashion
     *
     * @param batchOperation
     *            the batch operation
     * @param callbackHandler
     *            the callback handler
     * @throws FMSException
     */
    public void executeBatchAsync(BatchOperation batchOperation, CallbackHandler callbackHandler) throws FMSException {

        IntuitMessage intuitMessage = prepareBatch(batchOperation);

        //set callback handler
        intuitMessage.getRequestElements().setCallbackHandler(callbackHandler);

        //execute async interceptors
        executeAsyncInterceptors(intuitMessage);
    }

    /**
     * Common method to prepare the request params for add operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareAdd(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }

    /**
     * Common method to prepare the request params for update operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareUpdate(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_OPERATION, OperationType.UPDATE.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }
    
    //updateAccountOnTxns Used for France Locale with Minor Version >=5.
    private <T extends IEntity> IntuitMessage prepareupdateAccountOnTxns(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_OPERATION, OperationType.UPDATEACCOUNTONTXNS.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }
    //donotUpdateAccountOnTxns used for France Locale with Minor Version<=5
    private <T extends IEntity> IntuitMessage preparedonotUpdateAccountOnTxns(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_OPERATION, OperationType.DONOTUPDATEACCOUNTONTXNS.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }

    /**
     * Common method to prepare the request params for delete operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareDelete(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_OPERATION, OperationType.DELETE.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }

    /**
     * Common method to prepare the request params for findById operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareFindById(T entity) throws FMSException {
        Class<?> objectClass = entity.getClass();
        String entityName = objectClass.getSimpleName();
        Object rid = null;
        Method m;

        try {
            m = objectClass.getMethod("getId");
            rid = m.invoke(entity);
        } catch (Exception e) {
            throw new FMSException("Unable to read the method getId", e);
        }

        // The preferences/companyInfo check is to skip the Id null validation as it is not required for Preferences/CompanyInfo Read operation
        if ((!entityName.equals("Preferences") && !entityName.equals("OLBTransaction") && !entityName.equals("OLBStatus")) && rid == null) {
            throw new FMSException("Id is required.");
        }

        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());

        // The preferences/companyInfo check is to skip adding the entity id in the request URI
        if (!entityName.equals("Preferences") && !entityName.equals("OLBTransaction") && !entityName.equals("OLBStatus")) {
            requestParameters.put(RequestElements.REQ_PARAM_ENTITY_ID, rid.toString());
        }

        requestElements.setContext(context);
        requestElements.setEntity(entity);

        return intuitMessage;
    }
    
    private <T extends IEntity> IntuitMessage prepareFindByLevel(T entity) throws FMSException {
        Class<?> objectClass = entity.getClass();
        Object level = null;
        Method m;

        try {
            m = objectClass.getMethod("getLevel");
            level = m.invoke(entity);
        } catch (Exception e) {
            throw new FMSException("Unable to read the method getId", e);
        }

        // The preferences/companyInfo check is to skip the Id null validation as it is not required for Preferences/CompanyInfo Read operation
        if (level == null) {
            throw new FMSException("level is required.");
        }

        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
        requestParameters.put(RequestElements.REQ_PARAM_LEVEL, level.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);

        return intuitMessage;
    }
    
    private <T extends IEntity> IntuitMessage prepareFindByParentId(T entity) throws FMSException {
        Class<?> objectClass = entity.getClass();
        Class<?> parentClass;
        Object parentRef = null;
        Object parentId = null;
        Method m;

        try {
            m = objectClass.getMethod("getParentRef");
            parentRef = m.invoke(entity);
            parentClass = parentRef.getClass();
            m = parentClass.getMethod("getValue");
            parentId = m.invoke(parentRef);
        } catch (Exception e) {
            throw new FMSException("Unable to read the method getId", e);
        }

        // The preferences/companyInfo check is to skip the Id null validation as it is not required for Preferences/CompanyInfo Read operation
        if (parentId == null) {
            throw new FMSException("parentId is required.");
        }

        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
        requestParameters.put(RequestElements.REQ_PARAM_PARENT_ID, parentId.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);

        return intuitMessage;
    }
    
    private <T extends IEntity> IntuitMessage prepareEntitlementsRequest() throws FMSException {
        
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());

        requestElements.setContext(context);
        intuitMessage.setEntitlementService(true);

        return intuitMessage;
    }

    /**
     * Common method to prepare the request params for voidRequest operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareVoidRequest(T entity) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request parameters
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_INCLUDE, OperationType.VOID.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }

    /**
     * Common method to prepare the request params for upload operation for both sync and async calls
     *
     * @param entity the entity
     * @param docContent the content for document to upload
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareUpload(T entity, InputStream docContent, String boundaryId) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request parameters
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());

        UploadRequestElements uploadRequestElements = requestElements.getUploadRequestElements();
        uploadRequestElements.setDocContent(docContent);
        uploadRequestElements.setBoundaryId( utilizeIf(boundaryId) );
        uploadRequestElements.setElementsId(generateId().substring(0, 5));

        requestElements.setAction(OperationType.UPLOAD.toString());
        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setObjectToSerialize(getSerializableObject(entity));

        return intuitMessage;
    }


    private <T extends IEntity> IntuitMessage prepareUpload(T entity, InputStream docContent) throws FMSException {
       return prepareUpload(entity,docContent,null);
    }

    private String utilizeIf(String id)
    {
        return (null == id ? generateId() : id);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }


    /**
     * Common method to prepare the request params for findById operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareDownload(T entity) throws FMSException {
        Object rid = verifyEntityId(entity);

        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
        requestParameters.put(RequestElements.REQ_PARAM_ENTITY_ID, rid.toString());

        requestElements.setContext(context);
        requestElements.setEntity(entity);
        requestElements.setAction(OperationType.DOWNLOAD.toString());

        return intuitMessage;
    }


    /**
     * Common method to prepare the request params for findById operation for both sync and async calls
     *
     * @param entity
     *            the entity
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareDownloadPDF(T entity) throws FMSException {
        Object rid = verifyEntityId(entity);

        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
        requestParameters.put(RequestElements.REQ_PARAM_ENTITY_ID, rid.toString());
        requestParameters.put(RequestElements.REQ_PARAM_ENTITY_SELECTOR, ContentTypes.PDF.name().toLowerCase());

        requestElements.setContext(context);
        requestElements.setEntity(entity);

        return intuitMessage;
    }

    /**
     * Verifies that entity has getID method which can be invoked to retrieve entity id
     * @param entity
     * @param <T>
     * @return the result of dispatching getId method
     * @throws FMSException
     */
    private <T extends IEntity> Object verifyEntityId(T entity) throws FMSException {
        Class<?> objectClass = entity.getClass();
        Object rid = null;
        Method m;

        try {
            m = objectClass.getMethod("getId");
            rid = m.invoke(entity);
        } catch (Exception e) {
            throw new FMSException("Unable to read the method getId", e);
        }

        if (rid == null) {
            throw new FMSException("Id is required.");
        }
        return rid;
    }


    private <T extends IEntity> IntuitMessage prepareEmail(T entity, String email) throws FMSException {
        Object rid = verifyEntityId(entity);
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
        requestParameters.put(RequestElements.REQ_PARAM_ENTITY_ID, rid.toString());
        requestParameters.put(RequestElements.REQ_PARAM_ENTITY_SELECTOR, RequestElements.PARAM_SEND_SELECTOR);
        if((null != email)  && !email.isEmpty()) {
            // maybe keep email

            try {
                InternetAddress internetAddress = new InternetAddress(email);
                internetAddress.validate();
            } catch (AddressException e) {
                throw new FMSException("Email address \"" + email + "\" is not valid address according to  RFC 822.\n" + e.getMessage());
            }

            requestParameters.put(RequestElements.REQ_PARAM_SENDTO,email);
        }

        requestElements.setContext(context);
        requestElements.setEntity(entity);


        return intuitMessage;
    }

    
    /**
     * Common method to prepare the request params for query operation for both sync and async calls
     * 
     * @param query
     *            the query list
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareQuery(String query) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        if (query.length() > LENGTH_200) {
            requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());
            requestElements.setPostString(query);
        } else {
            requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
            requestParameters.put(RequestElements.REQ_PARAM_QUERY, query);
        }

        requestElements.setAction(OperationType.QUERY.toString());
        requestElements.setContext(context);

        return intuitMessage;
    }
    
    

    /**
     * Common method to prepare the request params for CDC query operation for both sync and async calls
     * 
     * @param entities
     *            the list of entities
     * @param changedSince
     *            the date where the entities should be listed from the last changed date
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareCDCQuery(List<? extends IEntity> entities, String changedSince) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());
        if (entities != null) {
            StringBuffer entityParam = new StringBuffer();
            for (IEntity entity : entities) {
                entityParam.append(entity.getClass().getSimpleName()).append(",");
            }
            entityParam.delete(entityParam.length() - 1, entityParam.length());
            requestParameters.put(RequestElements.REQ_PARAM_ENTITIES, entityParam.toString());
        }

        String cdcChangedSinceParam = null;
        String cdcAction = null;
        cdcChangedSinceParam = RequestElements.REQ_PARAM_CHANGED_SINCE;
        cdcAction = OperationType.CDCQUERY.toString();
        
        if (StringUtils.hasText(changedSince)) {
            requestParameters.put(cdcChangedSinceParam, changedSince);
        }
        requestElements.setAction(cdcAction);

        requestElements.setContext(context);

        return intuitMessage;
    }
    
    /**
     * Common method to prepare the request params for batch operation for both sync and async calls
     * 
     * @param batchOperation
     *            the batch operation
     * @return IntuitMessage the intuit message
     * @throws FMSException
     */
    private <T extends IEntity> IntuitMessage prepareBatch(BatchOperation batchOperation) throws FMSException {
        IntuitMessage intuitMessage = new IntuitMessage();
        RequestElements requestElements = intuitMessage.getRequestElements();

        //set the request params
        Map<String, String> requestParameters = requestElements.getRequestParameters();
        requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.POST.toString());

        IntuitBatchRequest intuitBatchRequest = new IntuitBatchRequest();
        intuitBatchRequest.setBatchItemRequest(batchOperation.getBatchItemRequests());
        
        requestElements.setAction(OperationType.BATCH.toString());
        requestElements.setContext(context);
        requestElements.setObjectToSerialize(getSerializableRequestObject(intuitBatchRequest));
        requestElements.setBatchOperation(batchOperation);

        return intuitMessage;
    }

    /**
     * Method to get the serializable object for the given entity
     * 
     * @param object
     *            the entity object
     * @return Object the serializable object
     */
    @SuppressWarnings("unchecked")
    protected <T extends IEntity> Object getSerializableObject(T object) throws FMSException {

        Class<?> objectClass = object.getClass();
        String methodName = "create".concat(objectClass.getSimpleName());
        ObjectFactory objectEntity = new ObjectFactory();
        Class<?> objectEntityClass = objectEntity.getClass();

        Method method = null;
        try {
            method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
        } catch (Exception e) {
            LOG.error("Exception while prepare the method signature using reflection to generate JAXBElement", e);
            throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
        }
        JAXBElement<? extends IEntity> jaxbElement = null;
        try {
            jaxbElement = (JAXBElement<? extends IEntity>) method.invoke(objectEntity, object);
        } catch (Exception e) {
            LOG.error("Exception while invoking the method using reflection to generate JAXBElement", e);
            throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
        }

        return jaxbElement;
    }

    /**
     * Method to get the JAXBElement, using ObjectFactory, to serialize the request
     * 
     * @param object
     *            the object to be converted to JAXBElement
     * @return Object the serializable object
     */
    @SuppressWarnings("unchecked")
    protected <T extends Object> Object getSerializableRequestObject(T object) throws FMSException {
        Class<?> objectClass = object.getClass();
        String methodName = "create".concat(objectClass.getSimpleName());
        ObjectFactory objectEntity = new ObjectFactory();
        Class<?> objectEntityClass = objectEntity.getClass();

        Method method = null;
        try {
            method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
        } catch (Exception e) {
            LOG.error("Exception while prepare the method signature using reflection to generate JAXBElement", e);
            throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
        }
        JAXBElement<? extends Object> jaxbElement = null;
        try {
            jaxbElement = (JAXBElement<? extends Object>) method.invoke(objectEntity, object);
        } catch (Exception e) {
            LOG.error("Exception while invoking the method using reflection to generate JAXBElement", e);
            throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
        }
        
        return jaxbElement;
    }
    
    /**
     * Method to parse the QueryResponse and create the entity list
     * 
     * @param queryResponse
     *            the query response
     * @return returns list of entity
     */
    @SuppressWarnings("unchecked")
    private <T extends IEntity> List<T> getEntities(QueryResponse queryResponse) {
        List<T> entityList = new ArrayList<T>();
        List<JAXBElement<? extends IntuitEntity>> intuitObjectsList = queryResponse.getIntuitObject();
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
     * @param queryResponse
     *            the query response
     * @return queryResult
     */
    protected QueryResult getQueryResult(QueryResponse queryResponse) {
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
    protected List<CDCQueryResult> getCDCQueryResult(List<CDCResponse> cdcResponses) {
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
    protected CDCQueryResult getCDCQueryResult(CDCResponse cdcResponse) {
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
}
