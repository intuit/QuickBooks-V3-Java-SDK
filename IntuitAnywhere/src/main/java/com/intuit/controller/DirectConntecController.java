package com.intuit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * This class is a controller for the application when user directly connecting from Intuit Application Center.  
 */
@Controller
public class DirectConntecController {

	public static final Logger LOG = Logger
			.getLogger(DirectConntecController.class);

	/*
	 * This method is called when the user click on the 'Try Buy' link from the
	 * IntuitApplication Center.
	 */
	@RequestMapping(value = "/directconnect.htm", method = RequestMethod.GET)
	public String directConnectToIntuit(final HttpServletRequest request) {
		LOG.info("DirectConntecController -> directConnectToIntuit()");

		request.getSession().setAttribute("isLinkingRequired", "true");

		return "redirect:/initialize.htm";
	}

	/*
	 * This method is a call back method by the application when the user click
	 * on the 'Try Buy' link from the IntuitApplication Center.
	 */
	@RequestMapping(value = "/linking.htm", method = RequestMethod.GET)
	public String linkingCredentials(final HttpServletRequest request) {
		LOG.info("DirectConntecController -> linkingCredentials()");

		final HttpSession session = request.getSession();

		final String intuitEmailId = (String) session.getAttribute("email");
		final String intuitUserName = (String) session
				.getAttribute("firstName")
				+ " "
				+ session.getAttribute("lastName");

		session.setAttribute("intuitEmailId", intuitEmailId);
		session.setAttribute("intuitUserName", intuitUserName);

		return "linking";
	}

	/*
	 * This method will redirect the user to directconnect to Intuit.
	 */
	@RequestMapping(value = "/redirect.htm", method = RequestMethod.GET)
	public String redirectConnectToIntuit(final HttpServletRequest request) {
		LOG.info("DirectConntecController -> directConnectToIntuit()");

		request.getSession().removeAttribute("isLinkingRequired");
		request.getSession().setAttribute("displayUserName",
				request.getParameter("intuitUserName"));
		request.getSession().setAttribute("emailMapped",
				request.getParameter("eMailId"));

		return "directconnect";
	}
}
