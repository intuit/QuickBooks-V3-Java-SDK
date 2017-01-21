package com.intuit.ipp.example.qbo.ukcompany;

import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.data.TaxRateDetail;
import com.intuit.ipp.data.TaxRateList;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class TaxCodeRead {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			TaxCode taxCodeIn = new TaxCode();
			taxCodeIn.setId("9");
            
			TaxCode taxCodeOut = service.findById(taxCodeIn);
			
			System.out.println("TaxCode is read!...");
			System.out.println("TaxCode Name : " + taxCodeOut.getName());
			System.out.println();
			
			TaxRateList taxRateList = taxCodeOut.getSalesTaxRateList();
			List<TaxRateDetail> taxRateDetails = taxRateList.getTaxRateDetail();
			Iterator<TaxRateDetail> itr = taxRateDetails.iterator();
			while(itr.hasNext()) {
				TaxRateDetail taxRateDetail = itr.next();
				System.out.println("TaxOrder : " + taxRateDetail.getTaxOrder());
				System.out.println("TaxTypeApplicable : " + taxRateDetail.getTaxTypeApplicable());
				
				ReferenceType taxRateRef = taxRateDetail.getTaxRateRef();
				System.out.println("TaxRateRef Name : " + taxRateRef.getName());
				System.out.println("TaxRateRef Value : " + taxRateRef.getValue());
				System.out.println("TaxRateRef Type : " + taxRateRef.getType());
				System.out.println();
			}
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
