package com.intuit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * This class is a controller for the application Log In / Log out activities.
 */
@Controller
public class LoginController {

	public static final Logger LOG = Logger.getLogger(LoginController.class);

	/*
	 * This method is called when the user enters the application path
	 */
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String showLoginPage() {
		LOG.info("LoginController -> showLoginPage()");

		return "login";
	}

	/*
	 * This method is called when the user submits the Login form.
	 */
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String authenticateUser(final HttpServletRequest request) {
		LOG.info("LoginController -> authenticateUser()");

		String redirectTo;

		final String userName = request.getParameter("username");
		final String passCode = request.getParameter("passcode");

		final HttpSession session = request.getSession();

		if ("ippuser".equals(userName)
				&& "password".equals(passCode)) {
			session.setAttribute("displayUserName", "IPP User");
			session.setAttribute("emailMapped", "ippuser@gmail.com");
			session.setAttribute("firstName", null);
			session.setAttribute("lastName", null);
			session.setAttribute("openIDidentity", null);

			redirectTo = "redirect:/home.htm";
		} else if (userName.equalsIgnoreCase((String) session
				.getAttribute("emailMapped"))
				&& passCode
						.equals((String) session.getAttribute("emailMapped"))) {
			session.setAttribute("displayUserName",
					session.getAttribute("displayUserName"));
			session.setAttribute("emailMapped",
					session.getAttribute("emailMapped"));
			session.setAttribute("firstName", null);
			session.setAttribute("lastName", null);
			session.setAttribute("openIDidentity", null);

			redirectTo = "redirect:/home.htm";
		} else {
			redirectTo = "redirect:/login.htm?isValidUser=false";
		}

		return redirectTo;
	}

	/*
	 * This method is called when the user clicks Logout button.
	 */
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String redirectToLoginPage(final HttpServletRequest request) {
		LOG.info("LoginController -> redirectToLoginPage()");

		request.getSession().setAttribute("displayUserName", null);
		
		return "login";
	}
}
