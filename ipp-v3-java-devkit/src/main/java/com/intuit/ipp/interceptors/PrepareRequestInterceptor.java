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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.intuit.ipp.compression.CompressorFactory;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.net.OperationType;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor class to prepare the prerequisites which are required to make the http call
 * 
 */
public class PrepareRequestInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {

		LOG.debug("Enter PrepareRequestInterceptor...");

		RequestElements requestElements = intuitMessage.getRequestElements();
		Map<String, String> requestParameters = requestElements.getRequestParameters();
		String action = (requestElements.getAction() == null) ? getEntityName(requestElements.getEntity()) : requestElements.getAction();

		if (intuitMessage.isPlatformService()) {
			requestParameters.put(RequestElements.REQ_PARAM_RESOURCE_URL, prepareIPSUri(action, requestElements.getContext()));
		} else if (intuitMessage.isEntitlementService()) {
			prepareEntitlementsRequest(intuitMessage, requestElements, requestParameters);
		}else {
			prepareDataServiceRequest(intuitMessage, requestElements, requestParameters, action);
		}

		LOG.debug("Exit PrepareRequestInterceptor.");
	}

	/**
	 * Method to prepare request parameters for data service
	 * 
	 * @param intuitMessage
	 *            the intuit message
	 * @param requestElements
	 *            the request elements
	 * @param requestParameters
	 *            the request parameters
	 * @param action
	 *            the action type
	 * @throws FMSException
	 *             the FMS Exception
	 * @throws CompressionException
	 *             the Compression Exception
	 */
	private void prepareDataServiceRequest(IntuitMessage intuitMessage, RequestElements requestElements, Map<String, String> requestParameters,
			String action) throws FMSException {
		requestParameters.put(RequestElements.REQ_PARAM_RESOURCE_URL,
				getUri(intuitMessage.isPlatformService(), action, requestElements.getContext(), requestParameters, intuitMessage.isEntitlementService()));

		Map<String, String> requestHeaders = requestElements.getRequestHeaders();

		if (isUpload(action)) {
			prepareUploadParams(requestElements);
		} else if (StringUtils.hasText(requestElements.getPostString())) {
			// if postString exists which means that the content-type should be text and serialization is not required
			requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_TYPE, ContentTypes.TEXT.toString());
		} else {
			// validates whether to add headers for content-type for serialization
			String serializeFormat = getSerializationRequestFormat();
            // add content type, except POST queries with email ("<entity>/id/send?...") - it has no POST body
			if (StringUtils.hasText(serializeFormat) && !isSendEmail(requestParameters)) {
				requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_TYPE, ContentTypes.getContentType(serializeFormat));
			} 
			else if (StringUtils.hasText(serializeFormat) && isSendEmail(requestParameters)) {
				requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_TYPE, ContentTypes.OCTECT_STREAM.toString());
			}
		}

		// validates whether to add headers for content-encoding for compression
		String compressFormat = Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT);
		if (StringUtils.hasText(compressFormat) && CompressorFactory.isValidCompressFormat(compressFormat)) {
			requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_ENCODING, compressFormat);
		}

        setupAcceptEncoding(requestHeaders);
        setupAcceptHeader(action, requestHeaders, requestParameters);


        UUID trackingID = requestElements.getContext().getTrackingID();
		if(!(trackingID==null))
		{
			requestHeaders.put(RequestElements.HEADER_INTUIT_TID, trackingID.toString());
		}
	}

	private void prepareEntitlementsRequest(IntuitMessage intuitMessage, RequestElements requestElements, Map<String, String> requestParameters) throws FMSException {
		requestParameters.put(RequestElements.REQ_PARAM_RESOURCE_URL,
				getUri(intuitMessage.isPlatformService(), null, requestElements.getContext(), requestParameters, intuitMessage.isEntitlementService()));

		Map<String, String> requestHeaders = requestElements.getRequestHeaders();

		//set content type to xml since Entitlement API supports only XML
		requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_TYPE, ContentTypes.XML.toString());
		requestHeaders.put(RequestElements.HEADER_PARAM_ACCEPT, ContentTypes.XML.toString());

		// validates whether to add headers for content-encoding for compression
		String compressFormat = Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT);
		if (StringUtils.hasText(compressFormat) && CompressorFactory.isValidCompressFormat(compressFormat)) {
			requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_ENCODING, compressFormat);
		}

        setupAcceptEncoding(requestHeaders);
        //setupAcceptHeader(null, requestHeaders, requestParameters);


        UUID trackingID = requestElements.getContext().getTrackingID();
		if(!(trackingID==null))
		{
			requestHeaders.put(RequestElements.HEADER_INTUIT_TID, trackingID.toString());
		}
	}
	
    /**
     * Setup accept encoding header from configuration
     * @param requestHeaders
     */
    private void setupAcceptEncoding(Map<String, String> requestHeaders) {
        // validates whether to add headers for accept-encoding for compression
        String acceptCompressionFormat = Config.getProperty(Config.COMPRESSION_RESPONSE_FORMAT);
        if (StringUtils.hasText(acceptCompressionFormat)) {
            requestHeaders.put(RequestElements.HEADER_PARAM_ACCEPT_ENCODING, acceptCompressionFormat);
        }
    }

    /**
     * Setup accept header depends from the semantic of the request
     * @param action
     * @param requestHeaders
     */
    private void setupAcceptHeader(String action, Map<String, String> requestHeaders, Map<String, String> requestParameters) {
        // validates whether to add headers for accept for serialization
        String serializeAcceptFormat = getSerializationResponseFormat();
        if (StringUtils.hasText(serializeAcceptFormat)
                && !isDownload(action)
                && !isDownloadPDF(requestParameters)) {
                     requestHeaders.put(RequestElements.HEADER_PARAM_ACCEPT, ContentTypes.getContentType(serializeAcceptFormat));
        }

        if(isDownloadPDF(requestParameters)) {
            requestHeaders.put(RequestElements.HEADER_PARAM_ACCEPT, ContentTypes.getContentType(requestParameters.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR)));
        }
    }


    /**
     * Returns serialization format from config for received objects
     * @return sting format
     */
    protected String getSerializationResponseFormat() {
        return Config.getProperty(Config.SERIALIZATION_RESPONSE_FORMAT);
    }

    /**
     * Returns serialization format from config for sending objects
     * @return string format
     */
    protected String getSerializationRequestFormat() {
        return Config.getProperty(Config.SERIALIZATION_REQUEST_FORMAT);
    }

    /**
	 * Method to get the entity name from the given entity object
	 * 
	 * @param entity
	 *            the entity object
	 * @return returns entity name
	 */
	private <T extends IEntity> String getEntityName(T entity) {
		if (entity != null) {
			return entity.getClass().getSimpleName().toLowerCase();
		}
		return null;
	}

	/**
	 * Method to construct the URI
	 * 
	 * @param action
	 *            the entity name
	 * @param context
	 *            the context
	 * @param requestParameters
	 *            the request params
	 * @return returns URI
	 */
	private <T extends IEntity> String getUri(Boolean platformService, String action, Context context, Map<String, String> requestParameters, Boolean entitlementService)
			throws FMSException {
		String uri = null;
		if (!platformService) {
			
			ServiceType serviceType = context.getIntuitServiceType();
			if (entitlementService) {
				uri = prepareEntitlementUri(context);
			}
			else if (ServiceType.QBO == serviceType) {
				uri = prepareQBOUri(action, context, requestParameters);
			} else if (ServiceType.QBOPremier == serviceType) {
				uri = prepareQBOPremierUri(action, context, requestParameters);
			} else {
				throw new FMSException("SDK doesn't support for the service type : " + serviceType);
			}
		} else {
			uri = prepareIPSUri(action, context);
		}

		return uri;
	}

    /**
     * Return QBO base configuration from config file
     *
     * @return URL
     */
    protected String getBaseUrl(String url) {
    	if (url.endsWith("/")) {
    	    return url.substring(0, url.length() - 1);
    	}
    	else {
    	    return url;
    	}
    	
    }

	/**
	 * Method to construct the QBO URI
	 * 
	 * @param entityName
	 *            the entity name
	 * @param context
	 *            the context
	 * @param requestParameters
	 *            the request params
	 * @return the QBO URI
	 * @throws FMSException
	 *             the FMSException
	 */
	private <T extends IEntity> String prepareQBOUri(String entityName, Context context, 
			Map<String, String> requestParameters) throws FMSException {

		StringBuilder uri = new StringBuilder();
		if(entityName.equalsIgnoreCase("Taxservice"))
				{
					entityName = entityName + "/" + "taxcode";
				}
		
		// constructs request URI
		uri.append(getBaseUrl(Config.getProperty(Config.BASE_URL_QBO))).append("/").append(context.getRealmID()).append("/").append(entityName);
        addEntityID(requestParameters, uri);
        addEntitySelector(requestParameters, uri);
        addParentID(requestParameters, uri);

        // adds the built request param
      /*  if(requestParameters.equals("updateaccountontxns"))
        {
        	
    		uri.append("include=updateaccountontxns");

        }*/
		uri.append("?").append(buildRequestParams(requestParameters));
		// adds the generated request id as a parameter
		uri.append("requestid").append("=").append(context.getRequestID()).append("&");
		//set RequestId to null once appended, so the next random num can be generated
		context.setRequestID(null);
		//uri.append("include=abcdgef");
		
		if(context.getMinorVersion() == null)
		{
		context.setMinorVersion("38");
		}
		
		uri.append("minorversion").append("=").append(context.getMinorVersion()).append("&");
		if( context.getIncludeParam() !=null)
		{
		int includeval = context.getIncludeParam().size();
		String includeParam = "";
		if (includeval > 0)
		{
			for (int i=0; i<includeval; i++)
			{
				includeParam = includeParam + context.getIncludeParam().get(i) + ",";				
			}
			uri.append("include").append("=").append(includeParam.substring(0, includeParam.length()-1)).append("&");
		}
		}
		
		return uri.toString();
	}

    /**
     * adding the entity id in the URI, which is required for READ operation
     * @param requestParameters
     * @param uri
     */
    private void addEntityID(Map<String, String> requestParameters, StringBuilder uri) {
        if (StringUtils.hasText(requestParameters.get(RequestElements.REQ_PARAM_ENTITY_ID))) {
            uri.append("/").append(requestParameters.get(RequestElements.REQ_PARAM_ENTITY_ID));
        }
    }
    /**
     * adding additional selector in the URI, which is required for READ operation
     * Main purpose is to select or modify requested resource with well-defined keyword
     * @param requestParameters
     * @param uri
     */
    private void addEntitySelector(Map<String, String> requestParameters, StringBuilder uri) {
        if (StringUtils.hasText(requestParameters.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR))) {
            uri.append("/").append(requestParameters.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR));
        }
    }
    
    private void addParentID(Map<String, String> requestParameters, StringBuilder uri) {
        if (StringUtils.hasText(requestParameters.get(RequestElements.REQ_PARAM_PARENT_ID))) {
            uri.append("/").append(requestParameters.get(RequestElements.REQ_PARAM_PARENT_ID)).append("/children");
        }
    }

    /**
	 * Method to construct the OLB QBO URI
	 * 
	 * @param entityName
	 *            the entity name
	 * @param context
	 *            the context
	 * @param requestParameters
	 *            the request params
	 * @return the QBO URI
	 * @throws FMSException
	 *             the FMSException
	 */
	private <T extends IEntity> String prepareQBOPremierUri(String entityName, Context context, 
			Map<String, String> requestParameters) throws FMSException {

		StringBuilder uri = new StringBuilder();
		// constructs request URI
		uri.append(Config.getProperty("BASE_URL_QBO_OLB")).append("/").append(context.getRealmID()).append("/").append(entityName);

		// adding the entity id in the URI, which is required for READ operation
        addEntityID(requestParameters, uri);
        addEntitySelector(requestParameters, uri);

        // adds the built request param
		uri.append("?").append(buildRequestParams(requestParameters));
		// adds the generated request id as a parameter
		uri.append("requestid").append("=").append(context.getRequestID()).append("&");
		//set RequestId to null once appended, so the next random num can be generated
		context.setRequestID(null);
		
		
		if(context.getMinorVersion() !=null)
		{
		uri.append("minorversion").append("=").append(context.getMinorVersion()).append("&");
		}
		
		return uri.toString();
	}

	/**
	 * Method to construct the IPS URI
	 * 
	 * @param action
	 *            the entity name
	 * @param context
	 *            the context
	 * @return the IPS URI
	 * @throws FMSException
	 *             the FMSException
	 */
	private String prepareIPSUri(String action, Context context) throws FMSException {
		StringBuilder uri = new StringBuilder();
		uri.append(Config.getProperty(Config.BASE_URL_PLATFORMSERVICE)).append("/").append(context.getAppDBID())
			.append("?act=").append(action).append("&token=").append(context.getAppToken());
		return uri.toString();
	}
	
	private String prepareEntitlementUri(Context context) throws FMSException {
		StringBuilder uri = new StringBuilder();
		uri.append(getBaseUrl(Config.getProperty(Config.BASE_URL_ENTITLEMENTSERVICE))).append("/").
		append("entitlements").append("/").append("v3").append("/").append(context.getRealmID());
		return uri.toString();
	}

	/**
	 * Method to build the request params which are to be added in the URI
	 * 
	 * @param requestParameters
	 *            the request parameters
	 * @return returns constructed request param string
	 */
	private String buildRequestParams(Map<String, String> requestParameters) throws FMSException {
		StringBuilder reqParams = new StringBuilder();
		Set<String> keySet = requestParameters.keySet();
		Iterator<String> keySetIterator = keySet.iterator();

		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			String value = requestParameters.get(key);
			if (isKeyValueExpected(key)) {
//Changes for France and Minor Version 5
				if (value=="updateAccountOnTxns"){
					reqParams.append("include").append("=").append("updateaccountontxns").append("&");
					}
				else if (value=="donotUpdateAccountOnTxns"){
					reqParams.append("include").append("=").append("donotupdateaccountontxns").append("&");
					}
				else
					{
						reqParams.append(key).append("=").append(value).append("&");
					}
		
			} else if (key.equals(RequestElements.REQ_PARAM_QUERY) || key.equals(RequestElements.REQ_PARAM_ENTITIES)) {
				try {
					String encodedQuery = URLEncoder.encode(value, "UTF-8");
					reqParams.append(key).append("=").append(encodedQuery).append("&");
				} catch (UnsupportedEncodingException e) {
					throw new FMSException("UnsupportedEncodingException", e);
				}
			}
		}

		return reqParams.toString();
	}

    private boolean isKeyValueExpected(String key) {
        return key.equals(RequestElements.REQ_PARAM_OPERATION)
				|| key.equals(RequestElements.REQ_PARAM_INCLUDE)
                || key.equals(RequestElements.REQ_PARAM_CHANGED_SINCE)
                || key.equals(RequestElements.REQ_PARAM_LAST_UPDATED_TIME)
                || key.equals(RequestElements.REQ_PARAM_ACCOUNT_ID)
                || key.equals(RequestElements.REQ_PARAM_NUM_DAYS)
                || key.equals(RequestElements.REQ_PARAM_START_POS)
                || key.equals(RequestElements.REQ_PARAM_MAX_RESULTS)
                || key.equals(RequestElements.REQ_PARAM_SENDTO)
                || key.equals(RequestElements.REQ_PARAM_LEVEL)
                || key.equals(RequestElements.REPORT_PARAM_START_DT)
                || key.equals(RequestElements.REPORT_PARAM_END_DT)
                || key.equals(RequestElements.REPORT_PARAM_DT_MACRO)
                || key.equals(RequestElements.REPORT_PARAM_ACC_METHOD)
                || key.equals(RequestElements.REPORT_PARAM_SUM_COL_BY)
                || key.equals(RequestElements.REPORT_PARAM_CUSTOMER)
                || key.equals(RequestElements.REPORT_PARAM_VENDOR)
                || key.equals(RequestElements.REPORT_PARAM_ITEM)
                || key.equals(RequestElements.REPORT_PARAM_CLASS)
                || key.equals(RequestElements.REPORT_PARAM_DEPARTMENT)
                || key.equals(RequestElements.REPORT_PARAM_QZURL)
                || key.equals(RequestElements.REPORT_PARAM_AGING_PERIOD)
                || key.equals(RequestElements.REPORT_PARAM_NUM_PERIOD)
                || key.equals(RequestElements.REPORT_PARAM_REPORT_DT)
                || key.equals(RequestElements.REPORT_PARAM_COLUMNS)
                || key.equals(RequestElements.REPORT_PARAM_ACCOUNT)
        		|| key.equals(RequestElements.REPORT_PARAM_ACCOUNT_TYPE)
        		|| key.equals(RequestElements.REPORT_PARAM_SOURCE_ACCOUNT_TYPE)
        		|| key.equals(RequestElements.REPORT_PARAM_SORT_BY)
        		|| key.equals(RequestElements.REPORT_PARAM_SORT_ORDER)
        		|| key.equals(RequestElements.REPORT_PARAM_SOURCE_ACCOUNT)
        		|| key.equals(RequestElements.REPORT_PARAM_PAYMENT_METHOD)
        		|| key.equals(RequestElements.REPORT_PARAM_START_DUE_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_END_DUE_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_APPAID)
        		|| key.equals(RequestElements.REPORT_PARAM_ARPAID)
        		|| key.equals(RequestElements.REPORT_PARAM_BOTH_AMT)
        		|| key.equals(RequestElements.REPORT_PARAM_CLEARED)
        		|| key.equals(RequestElements.REPORT_PARAM_CREATE_DT_MACRO)
        		|| key.equals(RequestElements.REPORT_PARAM_DOC_NUM)
        		|| key.equals(RequestElements.REPORT_PARAM_DUE_DT_MACRO)
        		|| key.equals(RequestElements.REPORT_PARAM_GROUP_BY)
        		|| key.equals(RequestElements.REPORT_PARAM_MEMO)
        		|| key.equals(RequestElements.REPORT_PARAM_MOD_DT_MACRO)
        		|| key.equals(RequestElements.REPORT_PARAM_NAME)
        		|| key.equals(RequestElements.REPORT_PARAM_PRINTED)
        		|| key.equals(RequestElements.REPORT_PARAM_END_MOD_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_START_MOD_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_TERM)
        		|| key.equals(RequestElements.REPORT_PARAM_TRANSACTION_TYPE)
        		|| key.equals(RequestElements.REPORT_PARAM_AGING_METHOD)
        		|| key.equals(RequestElements.REPORT_PARAM_PAST_DUE)
        		|| key.equals(RequestElements.REPORT_PARAM_CREATE_DT_MACRO)
        		|| key.equals(RequestElements.REPORT_PARAM_END_CREATED_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_START_CREATED_DT)
        		|| key.equals(RequestElements.REPORT_PARAM_JOURNAL_CODE)
        		|| key.equals(RequestElements.REPORT_PARAM_EMPLOYEE)
        		|| key.equals(RequestElements.REPORT_PARAM_AGENCY_ID)
        		|| key.equals(RequestElements.REPORT_PARAM_CUSTOM1)
        		|| key.equals(RequestElements.REPORT_PARAM_CUSTOM2)
        		|| key.equals(RequestElements.REPORT_PARAM_CUSTOM3)
        		|| key.equals(RequestElements.REPORT_PARAM_SHIPVIA)
        		|| key.equals(RequestElements.REPORT_PARAM_ACCOUNT_STATUS);
    }

    /**
	 * Method to prepare the request params for upload functionality
	 * 
	 * @param requestElements the request elements
	 */
	private void prepareUploadParams(RequestElements requestElements) {
		Map<String, String> requestHeaders = requestElements.getRequestHeaders();
		UploadRequestElements uploadRequestElements = requestElements.getUploadRequestElements();
		String boundaryId = uploadRequestElements.getBoundaryId();
		String formMetadataName = "file_metadata_" + boundaryId.substring(0, 5) + "_" + uploadRequestElements.getElementsId();
		String formContentName = "file_content_" + boundaryId.substring(0, 5) + "_" + uploadRequestElements.getElementsId();
		String contentType = ContentTypes.getContentType(getSerializationRequestFormat());
		
		Attachable attachable = (Attachable) requestElements.getEntity();
		String fileName = (attachable.getFileName() != null) ? "; filename=\"" + attachable.getFileName() + "\"" : "";
		String fileType = (attachable.getContentType() != null) ? "Content-Type: " + attachable.getContentType() + "\r\n" : "";
		
		String entityBoundary = String.format(UploadRequestElements.TEMPLATE_ENTITY_BOUNDARY, boundaryId, formMetadataName, contentType);
		String contentBoundary = String.format(UploadRequestElements.TEMPLATE_CONTENT_BOUNDARY, boundaryId, formContentName, fileName, fileType);
		
		requestHeaders.put(RequestElements.HEADER_PARAM_CONTENT_TYPE, ContentTypes.MULTIPART_FORMDATA.toString() + "; boundary=" + boundaryId);
		
		uploadRequestElements.setBoundaryForEntity(entityBoundary);
		uploadRequestElements.setBoundaryForContent(contentBoundary);
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

    /**
     * Method returns true if this request expects PDF as response
     * @param map
     * @return
     */
    private boolean isDownloadPDF(Map<String, String> map) {
        return StringUtils.hasText(map.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR))
                && map.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR).equalsIgnoreCase(ContentTypes.PDF.name());
    }

    /**
     * Method returns true if this request should be send as email
     * @param map
     * @return
     */
    private boolean isSendEmail(Map<String, String> map) {
        return StringUtils.hasText(map.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR))
                && map.get(RequestElements.REQ_PARAM_ENTITY_SELECTOR).equalsIgnoreCase(RequestElements.PARAM_SEND_SELECTOR);
    }

	/**
	 * Method to validate if the given action is upload feature
	 * 
	 * @param action the action type
	 * @return boolean returns true if the action is upload
	 */
	private boolean isUpload(String action) {
		if (StringUtils.hasText(action) && action.equals(OperationType.UPLOAD.toString())) {
			return true;
		}
		return false;
	}
	
}
