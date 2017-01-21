package com.intuit.ipp.example;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.exception.AuthenticationException;
import com.intuit.ipp.exception.BadRequestException;
import com.intuit.ipp.exception.ConfigurationException;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.InternalServiceException;
import com.intuit.ipp.exception.InvalidRequestException;
import com.intuit.ipp.exception.InvalidTokenException;
import com.intuit.ipp.exception.ServiceException;
import com.intuit.ipp.exception.ServiceUnavailableException;
import com.intuit.ipp.exception.ValidationException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class Application {
	private Application() {

	}

	public static void main(String[] args) {

		String appToken = "replace app token here";

				String consumerKey = "replace consumer key here";
				String consumerSecret = "replace consumer secret here";
				String accessToken = "replace acces token  here";
				String accessTokenSecret = "replace access token secret here";

		OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		ServiceType type=ServiceType.QBO;
		String realmID = "replace company id here";//"219431353";
		Context context = null;
		
		try {
			context = new Context(oauth, appToken, type, realmID);

			Customer customer = new Customer();
			customer.setId("QB:2");
			customer.setDisplayName("Loki32");
			/*customer.setCompanyName("Company Name 34");
			customer.setOpenBalanceDate(DateUtils.getCurrentDateTime());*/
			DataService service = new DataService(context);

			//service.findAll(customer);
			//Customer c = service.findById(customer);
			//Customer c = service.add(customer);
			Customer c = service.update(customer);
			//System.out.println(c.getCompanyName());
		} catch (FMSException e) {
			if (e instanceof AuthenticationException) {
				System.out.println("Handling Authentication Exception");
				e.printStackTrace();
			} else if (e instanceof AuthenticationException) {
				System.out.println("Handling Authorization Exception");
				e.printStackTrace();
			} else if (e instanceof BadRequestException) {
				System.out.println("Handling Bad Request Exception");
				e.printStackTrace();
			} else if (e instanceof ConfigurationException) {
				System.out.println("Handling Configuration Exception");
				e.printStackTrace();
			} else if (e instanceof InternalServiceException) {
				System.out.println("Handling Internal Service Exception");
				e.printStackTrace();
			} else if (e instanceof InvalidRequestException) {
				System.out.println("Handling Invalid Request Exception");
				e.printStackTrace();
			} else if (e instanceof InvalidTokenException) {
				System.out.println("Handling Invalid Token Exception");
				e.printStackTrace();
			} else if (e instanceof ServiceException) {
				System.out.println("Handling Service Exception");
				e.printStackTrace();
			} else if (e instanceof ServiceUnavailableException) {
				System.out.println("Service Unavailable Exception");
				e.printStackTrace();
			} else if (e instanceof ValidationException) {
				System.out.println("Service Validation Exception");
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Service.getService(context).add(customer);
		// test t = new test();
		// remove comment from below line to test generic constraint (only
		// IntuitEntity will be accepted).
		// service.Add(t);
		/*
		 * ReportClause reportClause = new ReportClause("property", "=", "value"); reportClause.toString(); List<ReportClause> reportClauseList = new
		 * ArrayList<ReportClause>(); reportClauseList.add(reportClause); List<String> queryList = new ArrayList<String>();
		 * queryList.add("SELECT * from Customer WHERE active eq true;"); List<? extends IEntity> objectList =
		 * Service.getService(context).executeQuery(queryList);
		 */
	}
}

class Test {
	Test() {

	}
}
