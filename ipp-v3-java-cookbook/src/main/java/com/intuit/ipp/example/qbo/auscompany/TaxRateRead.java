package com.intuit.ipp.example.qbo.auscompany;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TaxRate;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class TaxRateRead {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			TaxRate taxRateIn = new TaxRate();
			taxRateIn.setId("9");
            
			TaxRate taxRateOut = service.findById(taxRateIn);
			
			System.out.println("TaxRate is read!...");
			System.out.println("Name : " + taxRateOut.getName());
			System.out.println("Description : " + taxRateOut.getDescription());
			System.out.println("RateValue : " + taxRateOut.getRateValue());
			System.out.println("SpecialTaxType : " + taxRateOut.getSpecialTaxType());
			
			ReferenceType agencyRef = taxRateOut.getAgencyRef();
			System.out.println("AgencyRef Name : " + agencyRef.getName());
			System.out.println("AgencyRef Value : " + agencyRef.getValue());
			
			System.out.println();
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
