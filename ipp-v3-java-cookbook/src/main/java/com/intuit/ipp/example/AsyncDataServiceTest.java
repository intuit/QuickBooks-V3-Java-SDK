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
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

public class AsyncDataServiceTest {

	/**
	 * @param args
	 */
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

			DataService service = new DataService(context);

			Customer customer = new Customer();
			customer.setId("NG:2285964");
			
			service.findAllAsync(customer, new AsyncCallBackFind());
			System.out.println("findAll called...");
			service.findByIdAsync(customer, new AsyncCallBackFind());
			System.out.println("findById called...");

			// for update
			// valid id NG:2285964
			customer.setId("NG:2285964");
			customer.setSparse(true);
			customer.setDisplayName("L 34");
			
			service.updateAsync(customer, new AsyncCallBackUpdate());
			System.out.println("update called...");

			
			System.out.println("I'm done");
			//Customer c = service.add(customer);
			//System.out.println(c);
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

class AsyncCallBackFind implements CallbackHandler {

	@Override
	public void execute(CallbackMessage callbackMessage) {
		System.out.println("In AsyncCallBackFind callback...");
		FMSException fmsException = callbackMessage.getFMSException();
		if(fmsException != null) {
			System.out.println("Exception");
			System.err.println(fmsException.getMessage());
		} else {
			QueryResult queryResult = callbackMessage.getQueryResult();
			System.out.println("no of entities : " + queryResult.getTotalCount());
			System.out.println("no of entities : " + ((Customer)queryResult.getEntities().get(0)).getDisplayName());
		}
	}

}

class AsyncCallBackUpdate implements CallbackHandler {

	@Override
	public void execute(CallbackMessage callbackMessage) {
		
		System.out.println("In AsyncCallBackUpdate callback...");
		
		FMSException fmsException = callbackMessage.getFMSException();
		if(fmsException != null) {
			System.out.println("Exception");
			System.err.println(fmsException.getMessage());
		} else {
			Customer c = (Customer) callbackMessage.getEntity();
			System.out.println("entity : " + c.getDisplayName());
		}
	}

}