package com.intuit.controller.support;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intuit.utils.OauthHelper;

/*
 * This class is a controller for the application to authorize the Intuit user.    
 */
@Controller
public class OAuthController {
	
	public static final Logger LOG = Logger.getLogger(OAuthController.class);

	/*
	 * This method is called by the application when the user clicks on 'Connect
	 * to Quick Books' button from the Home Page / Settings Page to get the 'Request Token'.
	 */
	@RequestMapping(value = "/requesttoken.htm", method = RequestMethod.GET)
	public void requestToken(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		LOG.info("#### OAuthController -> requestToken() - started ####");
		// Invoke the helper class and retrieve the request token.
		final OauthHelper oauthhelper = new OauthHelper();
		final Map<String, String> requesttokenmap = oauthhelper.getRequestTokenSignPost();

		final HttpSession session = request.getSession();
		session.setAttribute("requestToken",
				requesttokenmap.get("requestToken"));
		session.setAttribute("requestTokenSecret",
				requesttokenmap.get("requestTokenSecret"));

		// Retrieve the Authorize URL
		final String authURL = oauthhelper.getAuthorizeURL(
				requesttokenmap.get("requestToken"),
				requesttokenmap.get("requestTokenSecret"));

		LOG.info("Redirecting to authURL : " + authURL);
		LOG.info("#### OAuthController -> requestToken() - completed ####");
		// Redirect to the authorized URL page and retrieve the verifier code.
		response.sendRedirect(authURL);
	}

	/*
	 * This method is a call back method by the OAuth flow to get the 'Access
	 * Token'.
	 */
	@RequestMapping(value = "/accesstoken.htm", method = RequestMethod.GET)
	public String getAccessToken(final HttpServletRequest request) throws IOException {

		LOG.info("#### OAuthController ->  getAccessToken() - started ####");
		final HttpSession session = request.getSession();
		final String verifierCode = request.getParameter("oauth_verifier");
		final String realmID = request.getParameter("realmId");
		session.setAttribute("realmId", realmID);
		final String dataSource = request.getParameter("dataSource");
		session.setAttribute("dataSource", dataSource);

		final String requestToken = (String) session.getAttribute("requestToken");
		final String requestTokenSecret = (String) session
				.getAttribute("requestTokenSecret");

		LOG.info("verifier code:  " + verifierCode);
		LOG.info("realmID:  " + realmID);
		LOG.info("dataSource:  " + dataSource);

		final OauthHelper oauthhelper = new OauthHelper();
		LOG.info("before calling Access token API");
		final Map<String, String> accesstokenmap = oauthhelper.getAccessToken(
				verifierCode, requestToken, requestTokenSecret);
		LOG.info("after calling Access token API");

		session.setAttribute("accessToken", accesstokenmap.get("accessToken"));
		session.setAttribute("accessTokenSecret",
				accesstokenmap.get("accessTokenSecret"));
		session.setAttribute("connectionStatus", "authorized");

		final String flowType = (String) session.getAttribute("flowType");
		String redirectPage;

		// If the user authorized with the Connect to Intuit button, then
		// redirect to inter.jsp.
		// Otherwise, redirect to dashboard.jsp.

		LOG.info("flowType = " + flowType);

		if (session.getAttribute("isLinkingRequired") != null) {
			redirectPage = "redirect:/linking.htm";
		}
		else {
			redirectPage = "intermediate";
		}

		// The OAuth flow has been completed, so go to the app.
		LOG.info("#### OAuthController ->  getAccessToken() - completed ####");
		return redirectPage;
	}
}
