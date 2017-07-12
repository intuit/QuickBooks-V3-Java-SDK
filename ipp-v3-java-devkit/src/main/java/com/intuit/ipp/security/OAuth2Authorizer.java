package com.intuit.ipp.security;

import java.net.HttpURLConnection;

import org.apache.http.client.methods.HttpRequestBase;

import com.intuit.ipp.exception.FMSException;

/**
 * This class will sign the http request using oAuth2 credentials
 */
public class OAuth2Authorizer implements IAuthorizer {

	private String accessToken;

	@Override
	public void authorize(HttpRequestBase httpRequest) throws FMSException {
		httpRequest.setHeader("Authorization", "Bearer " + accessToken);
	}
	
	@Override
	public void authorize(HttpURLConnection httpUrlConnection) throws FMSException {
		httpUrlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);	
	}

	/**
	 * To create the oAuth authorizer object
	 * 
	 * @param accessToken the access token
	 */
	public OAuth2Authorizer(String accessToken) {
		this.accessToken = trim(accessToken);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private static String trim(String key) {
		if (key == null) {
            return null;
        }
		return key.trim();
	}
}
