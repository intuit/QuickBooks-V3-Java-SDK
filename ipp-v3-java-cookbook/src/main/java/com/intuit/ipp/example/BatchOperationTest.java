package com.intuit.ipp.example;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.data.Report;
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
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.BatchOperation;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;

public class BatchOperationTest {

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

		String appDBId = "db";
		String realmID = "replace company id here";//"219431353";
		Context context = null;

		try {
			context = new Context(oauth, appToken, ServiceType.QBD, realmID);

			Customer customer = new Customer();
			//customer.setId("NG:2285964");
			//customer.setSparse(true);
			customer.setGivenName("Mac Book");
			customer.setDisplayName("L 34");
			
			BatchOperation batchOperation = new BatchOperation();
			batchOperation.addEntity(customer, OperationEnum.CREATE, "12");
			
			Customer c = GenerateQuery.createQueryEntity(Customer.class);
			String query = select($(c.getId()), $(c.getDisplayName())).generate();
			batchOperation.addQuery(query, "13");
			context.setRequestID("987654321099");
			DataService service = new DataService(context);

			service.executeBatch(batchOperation);

			List<String> bIds = batchOperation.getBIds();
			
			for(String bId : bIds) {
				if(batchOperation.isFault(bId)) {
					Fault fault = batchOperation.getFault(bId);
					Error error = fault.getError().get(0);
					System.out.println("Fault error :" + error.getCode() + ", " + error.getDetail() + ", " + error.getMessage());
				} else if(batchOperation.isEntity(bId)) {
					System.out.println("Entity : " + ((Customer)batchOperation.getEntity(bId)).getDisplayName());
				} else if(batchOperation.isQuery(bId)) {
					QueryResult queryResult = batchOperation.getQueryResponse(bId);
					System.out.println("Query : " + queryResult.getMaxResults());
				} else if(batchOperation.isReport(bId)) {
					Report report = batchOperation.getReport(bId);
					//System.out.println("Report : " + report.getName().value());
				} else {
					System.out.println("Something wrong!...");
				}
			}
			
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
