package com.intuit.ipp.example.qbo.auscompany;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.EmailStatusEnum;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PrintStatusEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.SubTotalLineDetail;
import com.intuit.ipp.data.TaxLineDetail;
import com.intuit.ipp.data.TxnTaxDetail;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

public class InvoiceCreate {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			Invoice invoice = getInvoiceForCreate();
            
			Invoice newInvoice = service.add(invoice);
			
			System.out.println("Invoice is created!...");
			System.out.println("Id : " + newInvoice.getId());
			System.out.println("DocNumber : " + newInvoice.getDocNumber());
			System.out.println("Balance : " + newInvoice.getBalance());
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
	public static Invoice getInvoiceForCreate() {
		
		Invoice invoice = new Invoice();
		
		try {
			invoice.setDocNumber("1033");
			invoice.setTxnDate(DateUtils.getCurrentDateTime());
			
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("Australian Dollar");
			currencyRef.setValue("AUD");
			invoice.setCurrencyRef(currencyRef);
			
			Line line1 = new Line();
			line1.setLineNum(new BigInteger("1"));
			line1.setAmount(new BigDecimal("3.00"));
			line1.setDescription("notepad");
			line1.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
			SalesItemLineDetail salesItemLineDetail1 = new SalesItemLineDetail();
			ReferenceType itemRef1 = new ReferenceType();
			itemRef1.setName("Pens");
			itemRef1.setValue("2");
			salesItemLineDetail1.setItemRef(itemRef1);
			
			ReferenceType taxCodeRef1 = new ReferenceType();
			taxCodeRef1.setValue("TAX");
			salesItemLineDetail1.setTaxCodeRef(taxCodeRef1);
			line1.setSalesItemLineDetail(salesItemLineDetail1);
			
			Line line2 = new Line();
			line2.setAmount(new BigDecimal("3.00"));
			line2.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);
			line2.setSubTotalLineDetail(new SubTotalLineDetail());
			
			List<Line> lines1 = new ArrayList<Line>();
			lines1.add(line1);
			lines1.add(line2);
			invoice.setLine(lines1);
			
			// TxnTaxDetail
			TxnTaxDetail txnTaxDetail = new TxnTaxDetail();
			txnTaxDetail.setTotalTax(new BigDecimal("0.09"));
			
			Line txnTaxLineDetail1 = new Line();
			txnTaxLineDetail1.setAmount(new BigDecimal("0.09"));
			txnTaxLineDetail1.setDetailType(LineDetailTypeEnum.TAX_LINE_DETAIL);
			TaxLineDetail taxLineDetail1 = new TaxLineDetail();
			ReferenceType taxRateRef1 = new ReferenceType();
			taxRateRef1.setValue("10");
			taxLineDetail1.setTaxRateRef(taxRateRef1);
			taxLineDetail1.setPercentBased(true);
			taxLineDetail1.setTaxPercent(new BigDecimal("3"));
			taxLineDetail1.setNetAmountTaxable(new BigDecimal("3.00"));
			txnTaxLineDetail1.setTaxLineDetail(taxLineDetail1);
			
			List<Line> lines2 = new ArrayList<Line>();
			lines2.add(txnTaxLineDetail1);
			txnTaxDetail.setTaxLine(lines2);
			
			invoice.setTxnTaxDetail(txnTaxDetail);
			
			ReferenceType customerRef = new ReferenceType();
			customerRef.setName("Walter Walsh");
			customerRef.setValue("1");
			invoice.setCustomerRef(customerRef);
			
			ReferenceType salesTermRef = new ReferenceType();
			salesTermRef.setValue("3");
			invoice.setSalesTermRef(salesTermRef);
			
			invoice.setDueDate(DateUtils.getDateWithNextDays(30));
			invoice.setGlobalTaxCalculation(GlobalTaxCalculationEnum.TAX_EXCLUDED);
			invoice.setTotalAmt(new BigDecimal("3.09"));
			invoice.setPrintStatus(PrintStatusEnum.NEED_TO_PRINT);
			invoice.setEmailStatus(EmailStatusEnum.NOT_SET);
			invoice.setBalance(new BigDecimal("3.09"));
			invoice.setDeposit(new BigDecimal("0"));
			invoice.setAllowIPNPayment(false);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return invoice;
	}
}
