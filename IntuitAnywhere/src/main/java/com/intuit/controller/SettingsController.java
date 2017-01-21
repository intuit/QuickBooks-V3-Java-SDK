package com.intuit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intuit.ia.connection.IAPlatformClient;
import com.intuit.utils.WebUtils;

/*
 * This class is a controller for the application settings related activities.  
 */
@Controller
public class SettingsController {
	
	public static final Logger LOG = Logger.getLogger(SettingsController.class);

	/*
	 * This method is called when the user is clicks on the 'Setting' button.
	 */
	@RequestMapping(value = "/settings.htm")
	public String getSettings(final HttpServletRequest request, final Model model) {
		LOG.info("SettingsController -> getSettings()");

		final HttpSession session = request.getSession();
		String redirectTo;

		if (session.getAttribute("displayUserName") != null
				|| session.getAttribute("firstName") != null
				|| session.getAttribute("lastName") != null
				|| session.getAttribute("connectionStatus") != null) {
			redirectTo = "settings";
		} else {
			redirectTo = "redirect:/login.htm?isLoggedIn=false";
		}

		LOG.info("openIDidentity : "
				+ session.getAttribute("openIDidentity"));

		if (session.getAttribute("connectionStatus") != null
				&& session.getAttribute("connectionStatus")
						.equals("authorized")) {
			model.addAttribute("authorized", "true");
		}
		return redirectTo;
	}
	
	/*
	 * This method is called when the user clicks on 'disconnect' button on
	 * Settings Page.
	 */
	@RequestMapping(value = "/disconnect.htm", method = RequestMethod.GET)
	public String disconnectToIntuit(final HttpServletRequest request) throws IOException {
		LOG.info("HomeController -> disconnectToIntuit() - started ###");

		final HttpSession session = request.getSession();

		if(request.getParameter("fromAppCenter") != null){
			session.setAttribute("invalidateOAuth", "true");
			
			LOG.info("SDKAccessController -> disconnectToIntuit() - fromAppCenter completed ###");
			return "redirect:/initialize.htm";
		}
		else{
			invalidateOAuthTokens(session);
			session.setAttribute("accessToken", null);
			session.setAttribute("accessTokenSecret", null);
			session.setAttribute("connectionStatus", "not_authorized");

			LOG.info("SDKAccessController -> disconnectToIntuit() - fromApp completed ###");
			return "redirect:/home.htm";
		}
		

	}

	/*
	 * This method a helper to invalidate the OAuth Tokens.
	 */
	public static void invalidateOAuthTokens(final HttpSession session) {
		final WebUtils webutils = new WebUtils();
		final String accessToken = (String) session.getAttribute("accessToken");
		final String accessTokenSecret = (String) session
				.getAttribute("accessTokenSecret");
		final String realmID = (String) session.getAttribute("realmId");
		final String dataSource = (String) session.getAttribute("dataSource");

		try {
			if (accessToken != null && accessTokenSecret != null
					&& realmID != null) {
				final IAPlatformClient pClient = new IAPlatformClient();
				pClient.disconnect(WebUtils.OAUTH_CONSUMER_KEY, WebUtils.OAUTH_CONSUMER_SECRET, accessToken, accessTokenSecret);

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
