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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.ipp.util.StringUtils;

import com.intuit.ipp.data.WebhooksEvent;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;

/**
 * Class to provide webhooks related functions
 *
 */
public class WebhooksService {

	/**
	 * the logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * define algorithm
	 */
	private static final String ALGORITHM = "HmacSHA256";
	
	public boolean verifyPayload(String intuitSignature, String payload) {	
		try {
			SecretKeySpec secretKey = new SecretKeySpec(getVerifierKey().getBytes("UTF-8"), ALGORITHM);
			Mac mac = Mac.getInstance(ALGORITHM);
			mac.init(secretKey);
			String hash = DatatypeConverter.printBase64Binary(mac.doFinal(payload.getBytes()));
			return hash.equals(intuitSignature);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException while validating payload", e);
			return false;
		} catch (UnsupportedEncodingException e) {
			LOG.error("UnsupportedEncodingException while validating payload", e);
			return false;
		} catch (InvalidKeyException e) {
			LOG.error("InvalidKeyException validating payload", e);
			return false;
		}
	} 
	
	public WebhooksEvent getWebhooksEvent(String payload) {
		
		if (!StringUtils.hasText(payload)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(payload, WebhooksEvent.class);
		} catch (JsonParseException e) {
			LOG.error("Error while parsing payload", e);
			return null;
		} catch (JsonMappingException e) {
			LOG.error("Error while mapping payload", e);
			return null;
		} catch (IOException e) {
			LOG.error("IO exception while parsing payload", e);
			return null;
		}
			
	}
	
	/**
     * Verifier key to validate webhooks payload
     * @return
     */
    private String getVerifierKey() {
    	return Config.getProperty(Config.WEBHOOKS_VERIFIER_TOKEN);
    }
	

}
