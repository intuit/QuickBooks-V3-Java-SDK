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

/**
 * 
 * This class exposes APIs for disconnect (disconnect) and fetching blue dot menu dropdown(getAppMenu)
 */

import java.util.List;
import java.util.Map;

import org.openid4java.discovery.Identifier;

import com.intuit.ia.exception.ConnectionException;
import com.intuit.ia.exception.OAuthException;
import com.intuit.ia.exception.OpenIdException;

public class IAPlatformClient {

	private PlatformHttpClient httpClient;

	private OAuthHelper oAuthHelper;
	private OpenIdHelper openIdHelper;

	public IAPlatformClient() {
		openIdHelper = new OpenIdHelper();
		oAuthHelper = new OAuthHelper();
	}

	/**
	 * Disconnects the user from quickbooks
	 * 
	 * @throws ConnectionException
	 */
	public PlatformResponse disconnect(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret)
			throws ConnectionException {
		httpClient = new PlatformHttpClient(consumerKey, consumerSecret,
				accessToken, accessTokenSecret);
		return this.httpClient.disconnect();
	}
	/**
	 * getCurrentUser the user from quickbooks
	 * 
	 * @throws ConnectionException
	 */
	public User getcurrentUser(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret)
			throws ConnectionException {
		httpClient = new PlatformHttpClient(consumerKey, consumerSecret,
				accessToken, accessTokenSecret);
		User user = null;;
		try {
			user = this.httpClient.getCurrentUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Get App Menu returns list of all the applications that are linked with
	 * the selected company
	 * 
	 * @return List<String>: Returns HTML as a list of Strings
	 * @throws ConnectionException
	 *
	 */
	public List<String> getAppMenu(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret)
			throws ConnectionException {
		try {
			List<String> menulist;
			httpClient = new PlatformHttpClient(consumerKey, consumerSecret,
					accessToken, accessTokenSecret);
			menulist = this.httpClient.getAppMenu();
			return menulist;
		} catch (ConnectionException conEx) {
			throw conEx;
		} catch (Exception e) {
			throw new ConnectionException("Failed to fetch appmenu: "
					+ e.getMessage());
		}
	}

	public Map<String, String> getRequestTokenAndSecret(String consumerKey,
			String consumerSecret) throws OAuthException {
		return oAuthHelper.getRequestToken(consumerKey, consumerSecret);
	}

	/**
	 * 
	 * @param requestToken
	 * @return authorizeUrl
	 * @throws OAuthException
	 * 
	 * This API will prepare the authorization URL and return it back
	 */

	public String getOauthAuthorizeUrl(String requestToken)
			throws OAuthException {

		return oAuthHelper.getAuthorizeUrl(requestToken);

	}

	/**
	 * 
	 * Gets the accesstoken and accesstokensecret
	 * 
	 * @param verofierCode
	 * @param requestToken
	 *            (After the user authorization)
	 * @param requestTokensecret
	 * @return Map<String> : where accesstoken will be in the key "accessToken"
	 *         and accesstokensecret will be in key "accessTokenSecret"
	 * 
	 */

	public Map<String, String> getOAuthAccessToken(String verifierCode,
			String requestToken, String requestTokenSecret, String consumerKey,
			String consumerSecret) throws OAuthException {

		return oAuthHelper.getAccessToken(verifierCode, requestToken,
				requestTokenSecret, consumerKey, consumerSecret);
	}

	/**
	 * Gets the authorization url for OpenId
	 * 
	 * @throws
	 * 
	 * 
	 */

	public String getOpenIdAuthorizeUrl() throws OpenIdException {

		return openIdHelper.initOpenIdFlow();
	}

	/**
	 * 
	 * @param receivingUrl
	 * @param parameterMap
	 * @return Identifier : the OpenId Identifier
	 * @throws OpenIdException
	 */
	public Identifier verifyOpenIdResponse(String receivingUrl,
			Map<String, String[]> parameterMap) throws OpenIdException {
		return openIdHelper.verifyResponse(receivingUrl, parameterMap);
	}

	/**
	 * 
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @throws ConnectionException
	 */
	public PlatformResponse reConnect(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret)
			throws ConnectionException {

		httpClient = new PlatformHttpClient(consumerKey, consumerSecret,
				accessToken, accessTokenSecret);
		return this.httpClient.reConnect();
	}
}
