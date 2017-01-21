package com.intuit.controller.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intuit.ia.connection.IAPlatformClient;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.exception.FMSException;
import com.intuit.utils.WebUtils;

/*
 * This class is a controller for the application when user completes the OAuth flow in the application.  
 */
@Controller
@RequestMapping("/bluedot.htm")
public class BluedotController {

	public static final Logger LOG = Logger.getLogger(BluedotController.class);

	/*
	 * This method is called by default when the user click on the 'Blue Dot'
	 * link on the application in the top right corner.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void getBluedotMenu(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		// Retrieve the credentials from the session. In a production app these
		// would be retrieved from a persistent store.
		LOG.info("### BlueDotMenuServlet ###");
		final HttpSession session = request.getSession();
		final WebUtils webutils = new WebUtils();

		final String accessToken = (String) session.getAttribute("accessToken");
		final String accessTokenSecret = (String) session.getAttribute("accessTokenSecret");
		final String realmID = (String) session.getAttribute("realmId");
		final String dataSource = (String) session.getAttribute("dataSource");

		response.setContentType("text/plain");
		
		Context context = null;
		try {
			context = webutils.getContext(accessToken, accessTokenSecret, realmID, dataSource);
		} catch (FMSException e1) {
			response.sendError(403, "Error while creating context");
		}
		
		final PrintWriter out = response.getWriter();
		try {
			if (context != null) {
				final IAPlatformClient pClient = new IAPlatformClient();
				final StringBuffer stringBuffer = new StringBuffer();
				final List<String> menuList = pClient.getAppMenu(WebUtils.OAUTH_CONSUMER_KEY, WebUtils.OAUTH_CONSUMER_SECRET, accessToken, accessTokenSecret);
				if (menuList != null) {
					for (String mItem : menuList) {
						stringBuffer.append(mItem);
						out.println(mItem);
					}
				}
			}
			else {
				response.sendError(403);
			}
		} catch (Exception e) {
			LOG.error("Exception in BlueDotMenuServlet " + e.getMessage());
		}

		LOG.info("#### BlueDotMenuServlet leaving now....####");
	}
}
