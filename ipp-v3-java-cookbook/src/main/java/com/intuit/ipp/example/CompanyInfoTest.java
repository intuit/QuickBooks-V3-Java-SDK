package com.intuit.ipp.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.CompanyInfo;
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
import com.intuit.ipp.util.Config;

public class CompanyInfoTest {
	
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
				


				try {
					context = new Context(oauth, appToken, ServiceType.QBO, realmID);
					//context.setMinorVersion("1");
					Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
					DataService service = new DataService(context);
					CompanyInfo ci = new CompanyInfo();
					ci.setId(realmID);
					List<String> includeQueryParam = new ArrayList<String>();
					includeQueryParam.add("firsttxndate");
					//includeQueryParam.add("totalcountoftransactions");
					context.setIncludeParam(includeQueryParam);
					ci = service.findById(ci);
					System.out.println("Company Info" +  ci.getNameValue().get(1).getName() + " " + ci.getNameValue().get(1).getValue() + " " + ci.getNameValue().get(3).getName() + " " + ci.getNameValue().get(3).getValue());

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
