package com.intuit.ipp.example;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.CompanyInfo;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TaxAgency;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.data.TaxRate;
import com.intuit.ipp.data.TaxRateApplicableOnEnum;
import com.intuit.ipp.data.TaxRateDetails;
import com.intuit.ipp.data.TaxService;
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
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.GlobalTaxService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;

public class TaxServiceTest {
	
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
					context = new Context(oauth, ServiceType.QBO, realmID);
					
					Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
					Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "json");
					DataService service = new DataService(context);
					
					//Tax Agency 
//					TaxAgency taxagency = new TaxAgency();
//					taxagency.setDisplayName("TaxagencyAPI" +UUID.randomUUID());
//					taxagency.setDomain("QBO");
//					taxagency.setSparse(false);
					
//					TaxAgency taxagencynew = service.add(taxagency);
					//System.out.println("Tax Agency Create " + taxagencynew.getId());
					
//					TaxAgency taxfind = service.findById(taxagencynew);
//					System.out.println("Tax Agency Find by Id " + taxfind.getDisplayName());
//					
//					TaxAgency taxquery = GenerateQuery.createQueryEntity(TaxAgency.class);
//					String query = select($(taxquery)).where($(taxquery.getId()).eq("7")).generate(); 
//					List<String> queryList = new ArrayList<String>();
//					queryList.add(query);
//					QueryResult queryResult = service.executeQuery(query);
//					if (queryResult != null) {
//						TaxAgency tqueryResult = (TaxAgency)queryResult.getEntities().get(0);
//						System.out.println("Tax Agency Query " + tqueryResult.getDisplayName() + " " + tqueryResult.getId());
//					}
						
					//TaxService
						
					TaxService taxservice = new TaxService();
					taxservice.setTaxCode("MyTaxCode" + UUID.randomUUID());
					
	
					TaxRateDetails trd = new TaxRateDetails();
					trd.setRateValue(BigDecimal.ONE);
					trd.setTaxAgencyId("7");
					trd.setTaxRateName("MyTaxRate" + UUID.randomUUID());
					trd.setTaxApplicableOn(TaxRateApplicableOnEnum.SALES);
					
					List<TaxRateDetails> taxRateDetails = new ArrayList<TaxRateDetails>();
					taxRateDetails.add(trd);
					
					taxservice.setTaxRateDetails(taxRateDetails);
					
					GlobalTaxService taxdataservice = new GlobalTaxService(context);
					TaxService ts = taxdataservice.addTaxCode(taxservice);
					System.out.println("Response from server: " + ts.getTaxCode());
					System.out.println("Response from server: " + ts.getTaxCodeId());
					System.out.println("Response from server: " + ts.getTaxRateDetails().get(0).getTaxRateName());
					System.out.println("Response from server: " + ts.getTaxRateDetails().get(0).getTaxRateId());
					System.out.println("Response from server: " + ts.getTaxRateDetails().get(0).getTaxAgencyId());
					System.out.println("Response from server: " + ts.getTaxRateDetails().get(0).getRateValue());
					System.out.println("Response from server: " + ts.getTaxRateDetails().get(0).getTaxApplicableOn().value());

	
} catch (FMSException e)
{
	e.printStackTrace();
}
			
	}
					
				}

