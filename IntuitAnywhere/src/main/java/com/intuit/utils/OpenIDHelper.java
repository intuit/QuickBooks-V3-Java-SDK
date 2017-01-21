package com.intuit.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.openid4java.discovery.Identifier;

import com.intuit.ia.connection.IAPlatformClient;
import com.intuit.ia.exception.OpenIdException;

/*
 * This is a utility class for OpenID routines.
 */

public class OpenIDHelper {

	public static final Logger LOG = Logger.getLogger(OpenIDHelper.class);
	private static OpenIDHelper openIdHelper;
	private static IAPlatformClient platformClient;

	public Identifier verifyResponse(final HttpServletRequest httpReq) {
		LOG.info("start verifyResponse method ");

		try {
			final String receivingURL = httpReq.getRequestURL().toString();

			LOG.info("---Before calling ia-helper API---");
			return platformClient.verifyOpenIdResponse(receivingURL,
					httpReq.getParameterMap());
		} catch (OpenIdException e) {
			LOG.error("OpenIDException caught in verifyResponse: "
					+ e.toString());
		}

		LOG.info("Error: OpenIDHelper.verifyResponse(), verified is null.");
		return null;
	}

	public String getOpenIdAuthorizeUrl() {
		try {
			return platformClient.getOpenIdAuthorizeUrl();
		} catch (OpenIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static OpenIDHelper getInstance() {
		if (openIdHelper == null) {
			openIdHelper = new OpenIDHelper();
			platformClient = new IAPlatformClient();
		}
		return openIdHelper;
	}

}
