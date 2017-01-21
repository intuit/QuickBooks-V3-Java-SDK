package com.intuit.ipp.example.qbo.ukcompany;

import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.TaxRate;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class TaxRateFindAll {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			List<TaxRate> taxRates = service.findAll(new TaxRate());
			
			System.out.println("No of TaxRates : " + taxRates.size());
			Iterator<TaxRate> itr = taxRates.iterator();
			while(itr.hasNext()) {
				TaxRate taxRate = itr.next();
				System.out.println("TaxRate Id : " + taxRate.getId());
			}
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
