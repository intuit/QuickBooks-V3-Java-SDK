package com.intuit.ipp.example.qbo.ukcompany;

import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class TaxCodeFindAll {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			List<TaxCode> taxCodes = service.findAll(new TaxCode());
			
			System.out.println("No of TaxCode : " + taxCodes.size());
			Iterator<TaxCode> itr = taxCodes.iterator();
			while(itr.hasNext()) {
				TaxCode taxCode = itr.next();
				System.out.println("TaxCode Id : " + taxCode.getId());
			}
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
