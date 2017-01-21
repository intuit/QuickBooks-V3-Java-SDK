package com.intuit.ipp.example.qbo.ukcompany;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.SubTotalLineDetail;
import com.intuit.ipp.data.TaxLineDetail;
import com.intuit.ipp.data.TxnTaxDetail;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

public class BillCreate {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			Bill bill = getBillForCreate();
            
			Bill billOut = service.add(bill);
			
			System.out.println("Bill is created!...");
			System.out.println("Id : " + billOut.getId());
			System.out.println("DocNumber : " + billOut.getDocNumber());
			System.out.println("Balance : " + billOut.getBalance());
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
	public static Bill getBillForCreate() {
		
		Bill bill = new Bill();
		
		try {
			bill.setDocNumber("1033");
			bill.setTxnDate(DateUtils.getCurrentDateTime());
			
			ReferenceType currencyRef = new ReferenceType();
			currencyRef.setName("Australian Dollar");
			currencyRef.setValue("AUD");
			bill.setCurrencyRef(currencyRef);
			
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
			taxCodeRef1.setValue("10");
			salesItemLineDetail1.setTaxCodeRef(taxCodeRef1);
			line1.setSalesItemLineDetail(salesItemLineDetail1);
			
			Line line2 = new Line();
			line2.setAmount(new BigDecimal("3.00"));
			line2.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);
			line2.setSubTotalLineDetail(new SubTotalLineDetail());
			
			List<Line> lines1 = new ArrayList<Line>();
			lines1.add(line1);
			lines1.add(line2);
			bill.setLine(lines1);
			
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
			
			bill.setTxnTaxDetail(txnTaxDetail);
			
			ReferenceType salesTermRef = new ReferenceType();
			salesTermRef.setValue("3");
			bill.setSalesTermRef(salesTermRef);
			
			bill.setDueDate(DateUtils.getDateWithNextDays(30));
			bill.setGlobalTaxCalculation(GlobalTaxCalculationEnum.TAX_EXCLUDED);
			bill.setTotalAmt(new BigDecimal("3.09"));
			bill.setBalance(new BigDecimal("3.09"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return bill;
	}
}
