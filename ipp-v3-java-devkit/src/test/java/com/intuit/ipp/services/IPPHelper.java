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

import java.util.Map;
import java.util.ResourceBundle;

public class IPPHelper {

	private static IPPHelper ippHelper = null;
	
	private static final String ENVIRONMENT_VAR_QBO_APP_TOKEN = "IPP_QBO_APP_TOKEN";
	private static final String ENVIRONMENT_VAR_QBO_CONSUMER_KEY = "IPP_QBO_CONSUMER_KEY";
	private static final String ENVIRONMENT_VAR_QBO_CONSUMER_SECRET = "IPP_QBO_CONSUMER_SECRET";
	private static final String ENVIRONMENT_VAR_QBO_ACCESS_TOKEN = "IPP_QBO_ACCESS_TOKEN";
	private static final String ENVIRONMENT_VAR_QBO_ACCESS_TOKEN_SECRET = "IPP_QBO_ACCESS_TOKEN_SECRET";
	private static final String ENVIRONMENT_VAR_QBO_REALMID = "IPP_QBO_REALMID";
	
	private String qboAppToken;
	private String qboConsumerKey;
	private String qboConsumerSecret;
	private String qboAccessToken;
	private String qboAccessTokenSecret;
	private String qboAppDBId;
	private String qboRealmID;
	
	private IPPHelper() {
	}
	
	public static IPPHelper getInstance() {
		if(ippHelper == null) {
			return init();
		}
		return ippHelper;
	}
	
	private static IPPHelper init() {
		ippHelper = new IPPHelper();
		ResourceBundle bundle = ResourceBundle.getBundle("ippdevkit");
		
		ippHelper.setQboAppToken(bundle.getString("testsuit.qbo.app.token"));
		ippHelper.setQboConsumerKey(bundle.getString("testsuit.qbo.consumer.key"));
		ippHelper.setQboConsumerSecret(bundle.getString("testsuit.qbo.consumer.secret"));
		ippHelper.setQboAccessToken(bundle.getString("testsuit.qbo.access.token"));
		ippHelper.setQboAccessTokenSecret(bundle.getString("testsuit.qbo.access.token.secret"));
		ippHelper.setQboRealmID(bundle.getString("testsuit.qbo.realm.id"));
		
		Map<String, String> env = System.getenv();
		
		if (env.containsKey(ENVIRONMENT_VAR_QBO_APP_TOKEN) && env.get(ENVIRONMENT_VAR_QBO_APP_TOKEN) != null) {
			ippHelper.setQboAppToken(env.get(ENVIRONMENT_VAR_QBO_APP_TOKEN));
		}
		if (env.containsKey(ENVIRONMENT_VAR_QBO_CONSUMER_KEY) && env.get(ENVIRONMENT_VAR_QBO_CONSUMER_KEY) != null) {
			ippHelper.setQboConsumerKey(env.get(ENVIRONMENT_VAR_QBO_CONSUMER_KEY));
		}
		if (env.containsKey(ENVIRONMENT_VAR_QBO_CONSUMER_SECRET) && env.get(ENVIRONMENT_VAR_QBO_CONSUMER_SECRET) != null) {
			ippHelper.setQboConsumerSecret(env.get(ENVIRONMENT_VAR_QBO_CONSUMER_SECRET));
		}
		if (env.containsKey(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN) && env.get(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN) != null) {
			ippHelper.setQboAccessToken(env.get(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN));
		}
		if (env.containsKey(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN_SECRET) && env.get(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN_SECRET) != null) {
			ippHelper.setQboAccessTokenSecret(env.get(ENVIRONMENT_VAR_QBO_ACCESS_TOKEN_SECRET));
		}
		if (env.containsKey(ENVIRONMENT_VAR_QBO_REALMID) && env.get(ENVIRONMENT_VAR_QBO_REALMID) != null) {
			ippHelper.setQboRealmID(env.get(ENVIRONMENT_VAR_QBO_REALMID));
		}
		
		return ippHelper;
	}

	

	public static IPPHelper getIppHelper() {
		return ippHelper;
	}

	public static void setIppHelper(IPPHelper ippHelper) {
		IPPHelper.ippHelper = ippHelper;
	}

	public String getQboAppToken() {
		return qboAppToken;
	}

	public void setQboAppToken(String qboAppToken) {
		this.qboAppToken = qboAppToken;
	}

	public String getQboConsumerKey() {
		return qboConsumerKey;
	}

	public void setQboConsumerKey(String qboConsumerKey) {
		this.qboConsumerKey = qboConsumerKey;
	}

	public String getQboConsumerSecret() {
		return qboConsumerSecret;
	}

	public void setQboConsumerSecret(String qboConsumerSecret) {
		this.qboConsumerSecret = qboConsumerSecret;
	}

	public String getQboAccessToken() {
		return qboAccessToken;
	}

	public void setQboAccessToken(String qboAccessToken) {
		this.qboAccessToken = qboAccessToken;
	}

	public String getQboAccessTokenSecret() {
		return qboAccessTokenSecret;
	}

	public void setQboAccessTokenSecret(String qboAccessTokenSecret) {
		this.qboAccessTokenSecret = qboAccessTokenSecret;
	}

	public String getQboAppDBId() {
		return qboAppDBId;
	}

	public void setQboAppDBId(String qboAppDBId) {
		this.qboAppDBId = qboAppDBId;
	}

	public String getQboRealmID() {
		return qboRealmID;
	}

	public void setQboRealmID(String qboRealmID) {
		this.qboRealmID = qboRealmID;
	}	
	
}
