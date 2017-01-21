package com.intuit.ipp.example.qbo.auscompany;

import java.util.Iterator;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class InvoiceFindAll {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			List<Invoice> invoices = service.findAll(new Invoice());
			
			System.out.println("No of invoices : " + invoices.size());
			Iterator<Invoice> itr = invoices.iterator();
			while(itr.hasNext()) {
				Invoice invoice = itr.next();
				System.out.println("Invoice Id : " + invoice.getId());
			}
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
