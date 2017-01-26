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
package com.intuit.ipp.core;

import java.util.List;
import java.util.UUID;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.IAuthorizer;

/**
 * Class which holds the data service or platform service context 
 *
 */
public class Context {

	/**
	 * variable authorizer
	 */
	private IAuthorizer authorizer = null;
	
	/**
	 * variable appToken
	 */
	private String appToken = null;
	
	/**
	 * variable appDBID
	 */
	private String appDBID = null;
	
	/**
	 * variable realmID
	 */
	private String realmID = null;
	
	/**
	 * variable intuitServiceType
	 */
	private ServiceType intuitServiceType = null;
	
	/**
	 * variable requestID
	 */
	private String requestID = null;
	
	/**
	 * variable minorVersion
	 */
	private String minorVersion = null;
	
	/**
	 * variable includeParam
	 */
	private List includeParam = null;

    /**
     * variable customerRequestTimeout
      */
    private Integer customerRequestTimeout = null;

	/**
	 * variable ticket
	 */
	private String ticket = null;
	private UUID trackingID = null;

	/**
	 * Constructor for creating context when you know ServiceType (Data Source) and realmID.
	 * 
	 * @param authorizer the authorizer
	 * @param appToken the app token
	 * @param platformServiceType the platform service type
	 * @param realmID the realm id
	 * @throws FMSException the FMS Exception
	 */
	public Context(IAuthorizer authorizer, String appToken, ServiceType platformServiceType, String realmID) throws FMSException {
		this.authorizer = authorizer;
		this.appToken = appToken;
		if (platformServiceType == null) {
			throw new FMSException("Unable to get ServiceType for the given information");
		}
		this.intuitServiceType = platformServiceType;
		this.realmID = realmID;
	}


	/**
	 * Constructor for creating context when you know ServiceType (Data Source) and realmID.
	 * 
	 * @param authorizer the authorizer
	 * @param platformServiceType the platform service type
	 * @param realmID the realm id
	 * @throws FMSException the FMS Exception
	 */
	public Context(IAuthorizer authorizer, ServiceType platformServiceType, String realmID) throws FMSException {
		this.authorizer = authorizer;
		if (platformServiceType == null) {
			throw new FMSException("Unable to get ServiceType for the given information");
		}
		this.intuitServiceType = platformServiceType;
		this.realmID = realmID;
	}
	
	/**
	 * Method to retrieve realm Id
	 * 
	 * @return the realm Id
	 * @throws FMSException
	 */

	/**
	 * Method to get authorizer
	 * 
	 * @return authorizer
	 */
	public IAuthorizer getAuthorizer() {
		return authorizer;
	}

	/**
	 * Method to set authorizer
	 * 
	 * @param authorizer
	 */
	public void setAuthorizer(final IAuthorizer authorizer) {
		this.authorizer = authorizer;
	}

	/**
	 * Method to get Service type (Data Source)
	 * 
	 * @return intuitServiceType
	 */
	public ServiceType getIntuitServiceType() {
		return intuitServiceType;
	}

	/**
	 * Method to set Service type (Data Source)
	 * 
	 * @param intuitServiceType
	 */
	public void setIntuitServiceType(ServiceType intuitServiceType) {
		this.intuitServiceType = intuitServiceType;
	}

	/**
	 * Method to get Application token
	 * 
	 * @return appToken
	 */
	public String getAppToken() {
		return appToken;
	}

	/**
	 * Method to set Application token
	 * 
	 * @param appToken
	 */
	public void setAppToken(final String appToken) {
		this.appToken = appToken;
	}

	/**
	 * Method to get Application Database ID
	 * 
	 * @return appDBID
	 */
	public String getAppDBID() {
		return appDBID;
	}

	/**
	 * Method to set Application Database ID
	 * 
	 * @param appDBID
	 */
	public void setAppDBID(final String appDBID) {
		this.appDBID = appDBID;
	}

	/**
	 * Method to get RealmId
	 * 
	 * @return realmID
	 */
	public String getRealmID() {
		return realmID;
	}
	
	/**
	 * Method to get minorVersion
	 * 
	 * @return minorVersion
	 */
	
	public String getMinorVersion() {
		return minorVersion;
	}

	/**
	 * Method to set RealmId
	 * 
	 * @param realmID
	 */
	public void setRealmID(final String realmID) {
		this.realmID = realmID;
	}

	/**
	 * Method to invalidate every fields in context.
	 */
	public void invalidate() {
		this.appToken = null;
		this.appDBID = null;
		this.authorizer = null;
		this.intuitServiceType = null;
		this.realmID = null;
	}

	/**
	 * Method to generate unique requestID in the context of context
	 * 
	 * @return requestID
	 */
	public String getRequestID() {
		if(requestID == null)
		{
		requestID = UUID.randomUUID().toString().replace("-", "");
		}
		return requestID;
	}

	/**
	 * Method to get ticket
	 * 
	 * @return ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * Method to get trackingID
	 * 
	 * @return trackingID
	 */
	public UUID getTrackingID() {
		return trackingID;
	}
	
	
	/**
	 * Method to get trackingID
	 * 
	 * @return includeParam
	 */
	public List getIncludeParam() {
		return includeParam;
	}

	/**
	 * Method to set requestID
	 * 
	 * @param requestID
	 */
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	/**
	 * Method to set ticket
	 * 
	 * @param ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * Method to set trackingId
	 * 
	 * @param trackingID
	 */
	public void setTrackingID(UUID trackingID) {
		this.trackingID = trackingID;
	}
	

	/**
	 * Method to set minorVersion
	 * 
	 * @param minorVersion
	 */
	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}
	

	/**
	 * Method to set include query param
	 * 
	 * @param includeParam
	 */
	public void setIncludeParam(List includeParam) {
		this.includeParam = includeParam;
	}
    /**
     * Method to get customerRequestTimeout
     *
     * @return customerRequestTimeout
     */
    public Integer getCustomerRequestTimeout() {
        return customerRequestTimeout;
    }

    /**
     * Method to set customerRequestTimeout. If this is set to a non-null value, the requestTimeout in the configuration file
     * will be skipped.
     *
     * @param customerRequestTimeout
     */
    public void setCustomerRequestTimeout(Integer customerRequestTimeout) {
        this.customerRequestTimeout = customerRequestTimeout;
    }

}
