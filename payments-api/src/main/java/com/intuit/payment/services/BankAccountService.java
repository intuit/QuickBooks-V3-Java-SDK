/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.services;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.config.RequestContext;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.QueryResponse;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.LoggerImpl;

/**
 * Provides operations for BankAccount API
 * 
 * @author dderose
 *
 */
public class BankAccountService extends ServiceBase {

	private static final Logger logger = LoggerImpl.getInstance();
	
	private RequestContext requestContext;

	public BankAccountService(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Hiding the default constructor
	 */
	protected BankAccountService() {
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Method to create BankAccount for a given customer
	 * 
	 * @param bankAccount
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	public BankAccount create(BankAccount bankAccount, String customerId) throws BaseException {

		logger.debug("Enter BankAccountService::create");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/bank-accounts"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<BankAccount> typeReference = new TypeReference<BankAccount>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(bankAccount)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// retrieve response
		BankAccount bankAccountResponse = (BankAccount) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, bankAccountResponse);
		return bankAccountResponse;
	}

	/**
	 * Method to create a BankAccount for a given customer using Token value
	 * 
	 * @param token
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	public BankAccount createFromToken(Token token, String customerId) throws BaseException {

		logger.debug("Enter BankAccountService::createFromToken");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers")
				+ "{id}/bank-accounts/createFromToken".replaceAll("\\{format\\}", "json")
						.replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<BankAccount> typeReference = new TypeReference<BankAccount>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(token)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// retrieve response
		BankAccount bankAccountResponse = (BankAccount) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, bankAccountResponse);
		return bankAccountResponse;
	}

	/**
	 * Method to delete a bankAccount
	 * 
	 * @param customerId
	 * @param bankaccountId
	 * @return
	 * @throws BaseException
	 */
	public BankAccount delete(String customerId, String bankaccountId) throws BaseException {

		logger.debug("Enter BankAccountService::delete");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		if (StringUtils.isBlank(bankaccountId)) {
			logger.error("IllegalArgumentException {}", bankaccountId);
			throw new IllegalArgumentException("bankaccountId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers")
				+ "{id}/bank-accounts/{bankaccount_id}".replaceAll("\\{format\\}", "json")
						.replaceAll("\\{" + "id" + "\\}", customerId.toString())
						.replaceAll("\\{" + "bankaccount_id" + "\\}", bankaccountId);
		logger.info("apiUrl - " + apiUrl);

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.DELETE, apiUrl).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// For delete, there's not body returned back, hence initializing a
		// dummy object to return other attributes
		BankAccount bankAccountResponse = new BankAccount();

		// set additional attributes
		prepareResponse(request, response, bankAccountResponse);
		return bankAccountResponse;
	}

	/**
	 * Method to retrieve a bankAccount based on a bankaccountId for a given
	 * customer
	 * 
	 * @param customerId
	 * @param bankaccountId
	 * @return
	 * @throws BaseException
	 */
	public BankAccount getBankAccount(String customerId, String bankaccountId) throws BaseException {

		logger.debug("Enter BankAccountService::getBankAccount");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		if (StringUtils.isBlank(bankaccountId)) {
			logger.error("IllegalArgumentException {}", bankaccountId);
			throw new IllegalArgumentException("bankaccountId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers")
				+ "{id}/bank-accounts/{bankaccount_id}".replaceAll("\\{format\\}", "json")
						.replaceAll("\\{" + "id" + "\\}", customerId.toString())
						.replaceAll("\\{" + "bankaccount_id" + "\\}", bankaccountId);
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<BankAccount> typeReference = new TypeReference<BankAccount>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// retrieve response
		BankAccount bankAccountResponse = (BankAccount) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, bankAccountResponse);
		return bankAccountResponse;
	}

	/**
	 * Method to Get All BankAccounts for a given customer
	 *
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public QueryResponse getAllBankAccounts(String customerId) throws BaseException {

		logger.debug("Enter BankAccountService::getAllBankAccounts");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/bank-accounts"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<List<BankAccount>> typeReference = new TypeReference<List<BankAccount>>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		List<BankAccount> bankAccounts = (List<BankAccount>) response.getResponseObject();
		QueryResponse queryResponse = new QueryResponse.Builder().bankAccounts(bankAccounts).build();

		// set additional attributes
		prepareResponse(request, response, queryResponse);
		return queryResponse;
	}
	
	/**
	 * Method to Get All BankAccounts for a given customer specifying the count of records to be returned
	 * 
	 * @param customerId
	 * @param count
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public QueryResponse getAllBankAccounts(String customerId, int count) throws BaseException {

		logger.debug("Enter BankAccountService::getAllBankAccounts");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/bank-accounts"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		if(count > 0 ) {
			apiUrl = apiUrl + "?count="+ count;
		}
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<List<BankAccount>> typeReference = new TypeReference<List<BankAccount>>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		List<BankAccount> bankAccounts = (List<BankAccount>) response.getResponseObject();
		QueryResponse queryResponse = new QueryResponse.Builder().bankAccounts(bankAccounts).build();

		// set additional attributes
		prepareResponse(request, response, queryResponse);
		return queryResponse;
	}

}
