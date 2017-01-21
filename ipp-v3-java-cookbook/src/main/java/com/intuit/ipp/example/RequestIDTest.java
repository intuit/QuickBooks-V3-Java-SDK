package com.intuit.ipp.example;

import java.util.UUID;

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
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;


public class RequestIDTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//QBO creds

			String appToken = "replace app token here";

				String consumerKey = "replace consumer key here";
				String consumerSecret = "replace consumer secret here";
				String accessToken = "replace acces token  here";
				String accessTokenSecret = "replace access token secret here";

				OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, accessToken, accessTokenSecret);

				String appDBId = "QBO";
				String realmID = "replace company id here";//"219431353";
				Context context = null;
				
		//QBD creds

/*				String appToken = "9197bc4ca03e1a4487aabe4a7fc370a12d8e";

				String consumerKey = "qyprdfHetq2Y9uXoF9X1cWK29qdrJa";
				String consumerSecret = "rHC37E9eJ5qIC54c9OcFVQqLY0QvuEi555rPQWrG";
				String accessToken = "qyprdfeWnv9Gsu9RjhB0LD1QppgiHuKLyn8lV0zCyiH9hy0Z";
				String accessTokenSecret = "Xg4n8u50oaizoC3KntqTRNLe6vNZj474jCrNMZuZ";

				OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, accessToken, accessTokenSecret);

				String appDBId = "QBD";
				String realmID = "1010877775";*/

				try {
					context = new Context(oauth, appToken, ServiceType.QBD, realmID);
					Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
					DataService service = new DataService(context);
					Customer customer = new Customer();
					customer.setDisplayName("ReqIDName" + UUID.randomUUID().toString().substring(0, 5));
					customer.setTitle("ReqIDTitle" + UUID.randomUUID().toString().substring(0, 5));
					customer.setGivenName("ReqIDGN" + UUID.randomUUID().toString().substring(0, 5));
					customer.setMiddleName("ReqIDMN" + UUID.randomUUID().toString().substring(0, 5));
					customer.setFamilyName("ReqIDFN" + UUID.randomUUID().toString().substring(0, 5));
					context.setRequestID("111222333666");
					//context.setTrackingID(UUID.randomUUID());
					
					System.out.println("customer added...");

					Customer c1 = service.add(customer);
					System.out.println("Given name is " + c1.getGivenName());
					
					System.out.println("I'm done");

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
			
	}

}
}
