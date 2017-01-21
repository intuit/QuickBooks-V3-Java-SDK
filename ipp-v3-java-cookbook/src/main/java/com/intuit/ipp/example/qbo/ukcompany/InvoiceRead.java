package com.intuit.ipp.example.qbo.ukcompany;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;

public class InvoiceRead {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			Invoice invoice = new Invoice();
			invoice.setId("4");
            
			Invoice newInvoice = service.findById(invoice);
			
			System.out.println("Invoice is read!...");
			System.out.println("DocNumber : " + newInvoice.getDocNumber());
			System.out.println("Balance : " + newInvoice.getBalance());
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
}
