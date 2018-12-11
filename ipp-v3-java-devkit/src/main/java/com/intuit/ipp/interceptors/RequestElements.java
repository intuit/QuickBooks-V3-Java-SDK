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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.services.BatchOperation;
import com.intuit.ipp.services.CallbackHandler;

/**
 * Class to hold the request elements which are used across the interceptor flow. 
 *
 */
public class RequestElements {
	
	/**
	 * variable HEADER_PARAM_CONTENT_TYPE
	 */
	public static final String HEADER_PARAM_CONTENT_TYPE = "content-type";
	
	/**
	 * variable HEADER_PARAM_CONTENT_ENCODING
	 */
	public static final String HEADER_PARAM_CONTENT_ENCODING = "Content-Encoding";
	
	/**
	 * variable HEADER_PARAM_ACCEPT_ENCODING
	 */
	public static final String HEADER_PARAM_ACCEPT_ENCODING = "Accept-Encoding";
	
	/**
	 * variable HEADER_PARAM_ACCEPT
	 */
	public static final String HEADER_PARAM_ACCEPT = "Accept";
	
	/**
	 * variable REQ_PARAM_METHOD_TYPE
	 */
	public static final String REQ_PARAM_METHOD_TYPE = "method_type";
	
	/**
	 * variable REQ_PARAM_RESOURCE_URL
	 */
	public static final String REQ_PARAM_RESOURCE_URL = "uri";
	
	/**
	 * variable REQ_PARAM_OPERATION
	 */
	public static final String REQ_PARAM_OPERATION = "operation";


	/**
	 * variable REQ_PARAM_OPERATION
	 */
	public static final String REQ_PARAM_INCLUDE = "include";

	/**
	 * variable REQ_PARAM_REQUEST_ID
	 */
	public static final String REQ_PARAM_REQUEST_ID = "requestid";
	
	/**
	 * variable REQ_PARAM_ENTITY_ID
	 */
	public static final String REQ_PARAM_ENTITY_ID = "entityid";

    /**
     * variable REQ_PARAM_ENTITY_SELECTOR
     */
    public static final String REQ_PARAM_ENTITY_SELECTOR = "selector";
    
    public static final String REQ_PARAM_PARENT_ID = "parentid";
    
    public static final String REQ_PARAM_LEVEL = "level";

	/**
	 * variable REQ_PARAM_QUERY
	 */
	public static final String REQ_PARAM_QUERY = "query";

    /**
     * variable SEND_SELECTOR
     */
    public static final String PARAM_SEND_SELECTOR = "send";

    /**
     * variable REQ_PARAM_SEND - send parameter
     */
    public static final String REQ_PARAM_SENDTO = "sendTo";
	
	
	/**
	 * variable REPORT_PARAM_START_DT
	 */
	public static final String REPORT_PARAM_START_DT = "start_date";

    /**
     * variable REPORT_PARAM_DT
     */
    public static final String REPORT_PARAM_REPORT_DT = "report_date";
	/**
	 * variable REPORT_PARAM_END_DT
	 */
	public static final String REPORT_PARAM_END_DT = "end_date";

    /**
     * variable REPORT_PARAM_END_CREATED_DT
     */
    public static final String REPORT_PARAM_END_CREATED_DT = "end_createdate";
    /**
     * variable REPORT_PARAM_START_CREATED_DT
     */
    public static final String REPORT_PARAM_START_CREATED_DT = "start_createdate";

    /**
	 * variable REPORT_PARAM_DT_MACRO
	 */
	public static final String REPORT_PARAM_DT_MACRO = "date_macro";

    /**
     * variable REPORT_PARAM_CREATE_DT_MACRO
     */
    public static final String REPORT_PARAM_CREATE_DT_MACRO = "createdate_macro";

    /**
     * variable REPORT_PARAM_MOD_DT_MACRO
     */
    public static final String REPORT_PARAM_MOD_DT_MACRO = "moddate_macro";

    /**
     * variable REPORT_PARAM_START_MOD_DT
     */
    public static final String REPORT_PARAM_START_MOD_DT = "start_moddate";

    /**
     * variable REPORT_PARAM_END_MOD_DT
     */
    public static final String REPORT_PARAM_END_MOD_DT = "end_moddate";

    /**
     * variable REPORT_PARAM_PAST_DUE - minimum days past due
     * Example: past_due=10
     */
    public static final String REPORT_PARAM_PAST_DUE = "past_due";

    /**
     * variable REPORT_PARAM_END_DUE_DT - specific the end date for due date
     */
    public static final String REPORT_PARAM_END_DUE_DT = "end_duedate";

    /**
     * variable REPORT_PARAM_START_DUE_DT - specific the start date for due date
     */
    public static final String REPORT_PARAM_START_DUE_DT = "start_duedate";

    /**
     * variable REPORT_PARAM_DUE_DT_MACRO - due Date: can be used as one report
     * customization option to filter report output
     */
    public static final String REPORT_PARAM_DUE_DT_MACRO = "duedate_macro";

    /**
	 * variable REPORT_PARAM_ACC_METHOD
	 */
	public static final String REPORT_PARAM_ACC_METHOD = "accounting_method";

    /**
     * variable REPORT_PARAM_ACCOUNT
     */
    public static final String REPORT_PARAM_ACCOUNT = "account";

    /**
     * variable REPORT_PARAM_SOURCE_ACCOUNT
     */
    public static final String REPORT_PARAM_SOURCE_ACCOUNT = "source_account";

    /**
     * variable REPORT_PARAM_ACCOUNT_TYPE
     */
    public static final String REPORT_PARAM_ACCOUNT_TYPE = "account_type";

    /**
     * variable REPORT_PARAM_SOURCE_ACCOUNT_TYPE
     */
    public static final String REPORT_PARAM_SOURCE_ACCOUNT_TYPE = "source_account_type";

    /**
	 * variable REPORT_PARAM_SUM_COL_BY
	 */
	public static final String REPORT_PARAM_SUM_COL_BY = "summarize_column_by";
	
	/**
	 * variable REPORT_PARAM_CUSTOMER
	 */
	public static final String REPORT_PARAM_CUSTOMER = "customer";
	
	/**
	 * variable REPORT_PARAM_VENDOR
	 */
	public static final String REPORT_PARAM_VENDOR = "vendor";
	
	/**
	 * variable REPORT_PARAM_ITEM
	 */
	public static final String REPORT_PARAM_ITEM = "item";

    /**
     * variable REPORT_PARAM_PAYMENT_METHOD - can be used as one report customization option
     * to filter report output, eg:to filter all the transactions by payment method
     * Supported Values: Cash,Check,Dinners Club,American Express,Discover,MasterCard, Visa
     */
    public static final String REPORT_PARAM_PAYMENT_METHOD = "payment_method";

    /**
	 * variable REPORT_PARAM_CLASS
	 */
	public static final String REPORT_PARAM_CLASS = "class";

    /**
     * variable REPORT_PARAM_APPAID - Paid ,Unpaid
     * Example: appaid=Paid
     */
    public static final String REPORT_PARAM_APPAID = "appaid";
	
	/**
	 * variable REPORT_PARAM_DEPARTMENT
	 */
	public static final String REPORT_PARAM_DEPARTMENT = "department";
	
	/**
	 * variable REPORT_PARAM_QZURL
	 */
	public static final String REPORT_PARAM_QZURL = "qzurl";
	
	/**
	 * variable REPORT_PARAM_AGING_PERIOD - days per aging period
     * Example: aging_period=30
	 */
	public static final String REPORT_PARAM_AGING_PERIOD = "aging_period";

    /**
     * variable REPORT_PARAM_AGING_METHOD - applicable values = Report Date,Current
     * Example: aging_period=Current
     */
    public static final String REPORT_PARAM_AGING_METHOD = "aging_method";

    /**
	 * variable REPORT_PARAM_NUM_PERIOD
	 */
	public static final String REPORT_PARAM_NUM_PERIOD = "num_periods";

    /**
     * variable REPORT_PARAM_TERM - specify the term id
     * Example: term=2
     */
    public static final String REPORT_PARAM_TERM = "term";

    /**
     * variable REPORT_PARAM_NAME - specify the id of the customer/vendor or employee by id
     */
    public static final String REPORT_PARAM_NAME = "name";

    /**
     * variable REPORT_PARAM_COLUMNS
     */
    public static final String REPORT_PARAM_COLUMNS = "columns";

    /**
     * variable REPORT_PARAM_TRANSACTION_TYPE - can be used as one report customization option to filter report
     * output specify the transaction type
     * Supported Values :
     * CreditCardCharge,Check,Invoice,ReceivePayment,JournalEntry,Bill,CreditCardCredit,
     * VendorCredit,Credit,BillPaymentCheck,BillPaymentCreditCard,
     * Charge,Transfer,Deposit,Statement,BillableCharge,TimeActivity,
     * CashPurchase,SalesReceipt,CreditMemo,CreditRefund,Estimate,
     * InventoryQuantityAdjustment,PurchaseOrder,GlobalTaxPayment,GlobalTaxAdjustment,
     * Service Tax Refund,Service Tax Gross Adjustment,Service Tax Reversal,
     * Service Tax Defer,Service Tax Partial Utilisation
     */
    public static final String REPORT_PARAM_TRANSACTION_TYPE = "transaction_type";

    /**
     * variable REPORT_PARAM_CLEARED - can be used as one report customization option to filter report output
     * specify the check status
     * Supported values : Cleared,Uncleared,Reconciled,Deposited
     */
    public static final String REPORT_PARAM_CLEARED = "cleared";

    /**
     * variable REPORT_PARAM_ARPAID - can be used as one report customization option to filter report output
     * Account Receivable paid or not
     * Supported values : Paid(1), Unpaid(2) Default: All
     */
    public static final String REPORT_PARAM_ARPAID = "arpaid";

    /**
     * variable REPORT_PARAM_PRINTED - an be used as one report customization option to filter report output
     * checks Printed or not
     * Supported values : Printed(1), To_be_Printed(2) Default: All
     */
    public static final String REPORT_PARAM_PRINTED = "printed";

    /**
     * variable REPORT_PARAM_BOTH_AMT - can be used as one report customization option to filter report output
     * eg: filter all transactions with amount
     */
    public static final String REPORT_PARAM_BOTH_AMT = "bothamount";

    /**
     * variable REPORT_PARAM_MEMO - can be used as one report customization option to filter report output
     * specify the memo number
     */
    public static final String REPORT_PARAM_MEMO = "memo";

    /**
     * variable REPORT_PARAM_DOC_NUM - specify the transaction number
     */
    public static final String REPORT_PARAM_DOC_NUM = "docnum";

    /**
     * variable REPORT_PARAM_SORT_BY - sorts the rows by specified column
     * Example: sort_by=txn_type
     */
    public static final String REPORT_PARAM_SORT_BY = "sort_by";

    /**
     * variable REPORT_PARAM_SORT_ORDER - applicable values are "ascend" or "descend"
     * Example: sort_order=ascend
     */
    public static final String REPORT_PARAM_SORT_ORDER = "sort_order";

    /**
     * variable REPORT_PARAM_GROUP_BY - Supported Values: Name,Account,Transaction Type,Customer,
     * Vendor,Employee,Location,Payment Method,Day,Week,Month,Quarter,Year,None
     */
    public static final String REPORT_PARAM_GROUP_BY = "group_by";
    
    public static final String REPORT_PARAM_JOURNAL_CODE = "journal_code";
    
    public static final String REPORT_PARAM_EMPLOYEE = "employee";
    
    public static final String REPORT_PARAM_AGENCY_ID = "agency_id";
    
    public static final String REPORT_PARAM_CUSTOM1 = "custom1";
    
    public static final String REPORT_PARAM_CUSTOM2 = "custom2";
    
    public static final String REPORT_PARAM_CUSTOM3 = "custom3";
    
    public static final String REPORT_PARAM_SHIPVIA = "shipvia";
    
    public static final String REPORT_PARAM_ACCOUNT_STATUS = "account_status";

    /**
	 * variable REQ_PARAM_ENTITIES
	 */
	public static final String REQ_PARAM_ENTITIES = "entities";
	
	/**
	 * variable REQ_PARAM_CHANGED_SINCE
	 */
	public static final String REQ_PARAM_CHANGED_SINCE = "changedSince";
	
	/**
	 * variable REQ_PARAM_LAST_UPDATED_TIME
	 */
	public static final String REQ_PARAM_LAST_UPDATED_TIME = "lastUpdatedTime";

	/**
	 * Content type used in Platform APIs
	 */
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	
	/**
	 * Variable  HEADER_USER_AGENT
	 */
	public static final String HEADER_USER_AGENT = "User-Agent";
	
	/**
	 * Variable  HEADER_INTUIT_TID
	 */
	public static final String HEADER_INTUIT_TID = "intuit_tid";
	
	/**
	 * Variable  REQ_PARAM_ACCOUNT_ID
	 */
	public static final String REQ_PARAM_ACCOUNT_ID = "accountid";
	
	/**
	 * Variable  REQ_PARAM_NUM_DAYS
	 */
	public static final String REQ_PARAM_NUM_DAYS = "numberofdays";
	
	/**
	 * Variable  REQ_PARAM_START_POS
	 */
	public static final String REQ_PARAM_START_POS = "startPosition";
	
	/**
	 * Variable  REQ_PARAM_MAX_RESULTS
	 */
	public static final String REQ_PARAM_MAX_RESULTS = "maxresults";
	
	/**
	 * variable QUICKBASE_ACTION
	 */
	
	public static final String QUICKBASE_ACTION = "QUICKBASE-ACTION";
	
	/**
	 * variable WORKPLACE_USER_AGENT
	 */
	public static final String WORKPLACE_USER_AGENT = "Workplace JavaAPI/1.0";
	
	/**
	 * variable requestHeaders. Holds header details which are used while making HTTP connection 
	 */
	private Map<String, String> requestHeaders = new HashMap<String, String>();
	
	/**
	 * variable requestParameters. Holds request parameters which are used while making HTTP connection
	 */
	private Map<String, String> requestParameters = new HashMap<String, String>();
	
	/**
	 * variable context
	 */
	private Context context = null;
	
	/**
	 * variable entity
	 */
	private IEntity entity;
	
	/**
	 * variable objectToSerialize
	 */
	private Object objectToSerialize;
	
	/**
	 * variable serializedData
	 */
	private String serializedData = null;
	
	/**
	 * variable compressedData
	 */
	private byte[] compressedData = null;

	/**
	 * variable callbackHandler
	 */
	private CallbackHandler callbackHandler = null;
	
	/**
	 * variable action
	 */
	private String action = null;

	/**
	 * variable batchOperation
	 */
	private BatchOperation batchOperation;
	
	/**
	 * variable postString
	 */
	private String postString;
	
	/**
	 * variable uploadFile
	 */
	private byte[] uploadFile;
	
	/**
	 * variable uploadRequestElements
	 */
	private UploadRequestElements uploadRequestElements = new UploadRequestElements();
	
	/**
	 * Gets request headers
	 * 
	 * @return returns request headers
	 */
	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	/**
	 * Sets request headers
	 * 
	 * @param requestHeaders request headers
	 */
	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	/**
	 * Gets request parameters
	 * 
	 * @return returns request parameters
	 */
	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	/**
	 * Sets request parameters
	 * 
	 * @param requestParameters request parameters
	 */
	public void setRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	/**
	 * Gets context object
	 * 
	 * @return returns context object
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Sets context object
	 * 
	 * @param context context object
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Gets entity
	 * 
	 * @return returns entity
	 */
	public IEntity getEntity() {
		return entity;
	}
	
	/**
	 * Sets entity
	 * 
	 * @param entity entity
	 */
	public <T extends IEntity> void setEntity(T entity) {
		this.entity = entity;
	}
	
	/**
	 * Gets Object to Serialize
	 * 
	 * @return returns Object to Serialize
	 */
	public Object getObjectToSerialize() {
		return objectToSerialize;
	}

	/**
	 * Sets Object to Serialize
	 * 
	 * @param objectToSerialize Object to Serialize
	 */
	public void setObjectToSerialize(Object objectToSerialize) {
		this.objectToSerialize = objectToSerialize;
	}

	/**
	 * Gets serialized data
	 * 
	 * @return returns serialized data
	 */
	public String getSerializedData() {
		return serializedData;
	}

	/**
	 * Sets serialized data
	 * 
	 * @param serializedData
	 */
	public void setSerializedData(String serializedData) {
		this.serializedData = serializedData;
	}

	/**
	 * Gets compressed data
	 * 
	 * @return returns compressed data
	 */
	public byte[] getCompressedData() {
		return compressedData;
	}

	/**
	 * Sets compressed data
	 * 
	 * @param compressedData
	 */
	public void setCompressedData(byte[] compressedData) {
		if (compressedData != null) {
			this.compressedData = Arrays.copyOf(compressedData, compressedData.length);
		}
	}
	
	/**
	 * Gets Callback Handler
	 * 
	 * @return returns Callback Handler
	 */
	public CallbackHandler getCallbackHandler() {
		return callbackHandler;
	}

	/**
	 * Sets Callback Handler
	 * 
	 * @param callbackHandler
	 */
	public void setCallbackHandler(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}

	/**
	 * Gets entity name
	 * 
	 * @return returns entity name
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets action name
	 * 
	 * @param action name
	 */
	public void setAction(String action) {
		this.action = action;
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
	 * @param batchOperation the batch operation
	 */
	public void setBatchOperation(BatchOperation batchOperation) {
		this.batchOperation = batchOperation;
	}

	/**
	 * Gets post string
	 * 
	 * @return returns post string
	 */
	public String getPostString() {
		return postString;
	}

	/**
	 * Sets post string
	 * 
	 * @param postString the post string
	 */
	public void setPostString(String postString) {
		this.postString = postString;
	}

	/**
	 * Gets upload request elements
	 * 
	 * @return returns upload request elements
	 */
	public UploadRequestElements getUploadRequestElements() {
		return uploadRequestElements;
	}

	/**
	 * Gets upload file
	 * 
	 * @return returns upload file
	 */
	public byte[] getUploadFile() {
		return uploadFile;
	}

	/**
	 * Sets upload file content
	 * 
	 * @param uploadFile the upload file content
	 */
	public void setUploadFile(byte[] uploadFile) {
		this.uploadFile = uploadFile;
	}
	
}
