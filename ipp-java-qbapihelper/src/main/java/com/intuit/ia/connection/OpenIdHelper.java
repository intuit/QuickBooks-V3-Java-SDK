/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.intuit.ia.connection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openid4java.OpenIDException;
import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.FetchRequest;
import org.slf4j.Logger;

import com.intuit.ia.exception.OpenIdException;
import com.intuit.ia.utils.IAHelperConfig;
import com.intuit.ia.utils.LoggerImpl;
import com.intuit.ia.utils.PlatformConstants;

/*
 * This is a utility class for OpenID routines.
 */

public class OpenIdHelper {

	private Logger logger = LoggerImpl.getInstance();
	private ConsumerManager consumerManager = null;
	private DiscoveryInformation discoveryInfo;

	/**
	 * 
	 * @param receivingUrl
	 * @param parameterMap
	 * @return OpenId Identifier
	 * @throws OpenIdException
	 */
	public Identifier verifyResponse(String receivingUrl,
			Map<String, String[]> parameterMap) throws OpenIdException {
		try {

			if (consumerManager == null) {
				logger.debug("The ConsumerManager is null");
			}
			final ParameterList response = new ParameterList(parameterMap);
			final VerificationResult verification = consumerManager.verify(
					receivingUrl, response, discoveryInfo);

			// Examine the verification result
			final Identifier verified = verification.getVerifiedId();
			return verified; // Success.

		} catch (OpenIDException e) {
			logger.debug("OpenID exception :" + e.getMessage());
			throw new OpenIdException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return String:the openID auth url
	 * @throws OpenIdException
	 * @throws OpenIDException 
	 */
	public String initOpenIdFlow() throws OpenIdException {

		final List<DiscoveryInformation> discoveries = new ArrayList<DiscoveryInformation>();
		consumerManager = new ConsumerManager();
		consumerManager.setAssociations(new InMemoryConsumerAssociationStore());
		consumerManager.setNonceVerifier(new InMemoryNonceVerifier(5000));
		consumerManager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
		DiscoveryInformation discovered = null;
		String openIdProviderUrl;
		openIdProviderUrl = IAHelperConfig.getInstance().getProperty(PlatformConstants.OPENID_PROVIDER_URL);
		try {
			logger.debug("OpenID Provider URL = " + openIdProviderUrl);
			discovered = new DiscoveryInformation(new URL(openIdProviderUrl));
		} catch (DiscoveryException e) {
			logger.debug("Failed to obtain discovery information :" + e.getMessage());
			throw new OpenIdException(e.getMessage(),e);
		} catch (MalformedURLException me) {
			logger.debug("Malformed URL :" + me.getMessage());
			throw new OpenIdException(me.getMessage(),me);
		}
		discoveries.add(discovered);
		discoveryInfo = consumerManager.associate(discoveries);
		final FetchRequest fetch = FetchRequest.createFetchRequest();
		try {
			fetch.addAttribute("FirstName",
					"http://axschema.org/namePerson/first", true);
			fetch.addAttribute("LastName",
					"http://axschema.org/namePerson/last", true);
			fetch.addAttribute("Email", "http://axschema.org/contact/email",
					true);
			fetch.addAttribute("RealmId", "http://axschema.org/intuit/realmId",
					true);
		} catch (MessageException e) {
			// LOG.error(e.getLocalizedMessage());
			throw new OpenIdException(e.getMessage(), e);
		}

		fetch.setCount("Email", 3);
		String openIdReturnUrl;
		openIdReturnUrl = IAHelperConfig.getInstance().getProperty(PlatformConstants.OPENID_RETURN_URL);
		AuthRequest authReq = null;
		logger.debug("openIdReturnUrl = " + openIdReturnUrl);
		try {
			authReq = consumerManager.authenticate(discoveryInfo,openIdReturnUrl);
			authReq.addExtension(fetch);
		} catch (MessageException e) {
			logger.debug("Message exception while init flow : " + e.getMessage());
		} catch (ConsumerException e) {
			logger.debug("Consumer exception : " + e.getMessage());
		}

		return authReq.getDestinationUrl(true);
	}

}
