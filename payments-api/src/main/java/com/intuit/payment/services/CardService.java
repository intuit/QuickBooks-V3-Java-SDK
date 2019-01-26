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
import com.intuit.payment.data.Card;
import com.intuit.payment.data.QueryResponse;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.http.MethodType;
import com.intuit.payment.http.Request;
import com.intuit.payment.http.Response;
import com.intuit.payment.services.base.ServiceBase;
import com.intuit.payment.util.LoggerImpl;

/**
 * Provides operations for Card API
 * 
 * @author dderose
 *
 */
public class CardService extends ServiceBase {

	private static final Logger logger = LoggerImpl.getInstance();
	
	private RequestContext requestContext;

	public CardService(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Hiding the default constructor
	 */
	protected CardService() {
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * Method to create Card for a given customer
	 * 
	 * @param card
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	public Card create(Card card, String customerId) throws BaseException {

		logger.debug("Enter CardService::create");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<Card> typeReference = new TypeReference<Card>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(card)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Card cardResponse = (Card) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, cardResponse);
		return cardResponse;
	}

	/**
	 * Method to create a Card for a given customer using Token value
	 * 
	 * @param token
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	public Card createFromToken(Token token, String customerId) throws BaseException {

		logger.debug("Enter CardService::createFromToken");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards/createFromToken"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<Card> typeReference = new TypeReference<Card>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.POST, apiUrl).requestObject(token)
				.typeReference(typeReference).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Card cardResponse = (Card) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, cardResponse);
		return cardResponse;
	}

	/**
	 * Method to delete a card
	 * 
	 * @param customerId
	 * @param cardId
	 * @return
	 * @throws BaseException
	 */
	public Card delete(String customerId, String cardId) throws BaseException {

		logger.debug("Enter CardService::delete");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		if (StringUtils.isBlank(cardId)) {
			logger.error("IllegalArgumentException {}", cardId);
			throw new IllegalArgumentException("cardId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards/{card_id}"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString())
				.replaceAll("\\{" + "card_id" + "\\}", cardId);
		logger.info("apiUrl - " + apiUrl);

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.DELETE, apiUrl).context(requestContext).build();

		// make API call
		Response response = sendRequest(request);
		// For delete, there's not body returned back, hence initializing a
		// dummy object to return other attributes
		Card cardResponse = new Card();

		// set additional attributes
		prepareResponse(request, response, cardResponse);
		return cardResponse;
	}

	/**
	 * Method to retrieve a card based on a cardId for a given customer
	 * 
	 * @param customerId
	 * @param cardId
	 * @return
	 * @throws BaseException
	 */
	public Card getCard(String customerId, String cardId) throws BaseException {

		logger.debug("Enter CardService::getCard");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		if (StringUtils.isBlank(cardId)) {
			logger.error("IllegalArgumentException {}", cardId);
			throw new IllegalArgumentException("cardId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards/{card_id}"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString())
				.replaceAll("\\{" + "card_id" + "\\}", cardId);
		logger.info("apiUrl - " + apiUrl);

		// assign TypeReference for deserialization
		TypeReference<Card> typeReference = new TypeReference<Card>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		Card cardResponse = (Card) response.getResponseObject();

		// set additional attributes
		prepareResponse(request, response, cardResponse);
		return cardResponse;
	}
	
	/**
	 * Method to Get All Cards for a given customer
	 * 
	 * @param customerId
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public QueryResponse getAllCards(String customerId) throws BaseException {

		logger.debug("Enter CardService::getAllCards");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<List<Card>> typeReference = new TypeReference<List<Card>>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		List<Card> cards = (List<Card>) response.getResponseObject();
		QueryResponse queryResponse = new QueryResponse.Builder().cards(cards).build();

		// set additional attributes
		prepareResponse(request, response, queryResponse);
		return queryResponse;
	}

	/**
	 * Method to Get All Cards for a given customer specifying the count of records to be returned
	 * 
	 * @param customerId
	 * @param count
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public QueryResponse getAllCards(String customerId, int count) throws BaseException {

		logger.debug("Enter CardService::getAllCards");
		
		if (StringUtils.isBlank(customerId)) {
			logger.error("IllegalArgumentException {}", customerId);
			throw new IllegalArgumentException("customerId cannot be empty or null");
		}

		// prepare API url
		String apiUrl = requestContext.getBaseUrl().replaceAll("payments", "customers") + "{id}/cards"
				.replaceAll("\\{format\\}", "json").replaceAll("\\{" + "id" + "\\}", customerId.toString());
		if(count > 0 ) {
			apiUrl = apiUrl + "?count="+ count;
		}
		logger.info("apiUrl - " + apiUrl);
		
		// assign TypeReference for deserialization
		TypeReference<List<Card>> typeReference = new TypeReference<List<Card>>() {
		};

		// prepare service request
		Request request = new Request.RequestBuilder(MethodType.GET, apiUrl).typeReference(typeReference)
				.context(requestContext).build();

		// make API call
		Response response = sendRequest(request);

		// retrieve response
		List<Card> cards = (List<Card>) response.getResponseObject();
		QueryResponse queryResponse = new QueryResponse.Builder().cards(cards).build();

		// set additional attributes
		prepareResponse(request, response, queryResponse);
		return queryResponse;
	}

}
