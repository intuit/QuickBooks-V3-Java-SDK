package com.intuit.ipp.security;

import java.net.HttpURLConnection;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;

import org.apache.http.client.methods.HttpRequestBase;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.interceptors.HTTPURLConnectionInterceptor;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.StringUtils;

/**
 * This class will sign the http request using oAuth credentials
 */
public class OAuthAuthorizer implements IAuthorizer {

	/**
	 * variable oAuthConsumer
	 */
	private OAuthConsumer oAuthConsumer;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void authorize(HttpRequestBase httpRequest) throws FMSException {
		try {
			oAuthConsumer.sign(httpRequest);
		} catch (OAuthMessageSignerException e) {
			throw new FMSException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new FMSException(e);
		} catch (OAuthCommunicationException e) {
			throw new FMSException(e);
		}
	}
	@Override
	public void authorize(HttpURLConnection httpUrlConnection) throws FMSException {
		try {
			oAuthConsumer.sign(httpUrlConnection);
		} catch (OAuthMessageSignerException e) {
			throw new FMSException(e);
		} catch (OAuthExpectationFailedException e) {
			throw new FMSException(e);
		} catch (OAuthCommunicationException e) {
			throw new FMSException(e);
		}		
	}

	/**
	 * This is a private constructor which creates the oAuthConsumer object
	 * 
	 * @param consumerKey
	 *            the consumer key
	 * @param consumerSecret
	 *            the consumer secret
	 */
	private OAuthAuthorizer(String consumerKey, String consumerSecret) {
		String httpTransport = Config.getProperty(Config.HTTP_TRANSPORT);
		if (StringUtils.hasText(httpTransport) && httpTransport.equals(HTTPURLConnectionInterceptor.HTTP_URL_CONNECTION)) {
		oAuthConsumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		}else
		{
		oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		}
	}

	/**
	 * To create the oAuth authorizer object
	 * 
	 * @param consumerKey the consumer key
	 * @param consumerSecret the consumer secret
	 * @param accessToken the access token
	 * @param accessTokenSecret the access token secret
	 */
	public OAuthAuthorizer(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		this(consumerKey, consumerSecret);
		oAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
		oAuthConsumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
	}

}
