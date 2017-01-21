package com.intuit.ipp.example;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.ColData;
import com.intuit.ipp.data.CompanyInfo;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Header;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.data.Row;
import com.intuit.ipp.data.Rows;
import com.intuit.ipp.data.Summary;
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
import com.intuit.ipp.services.ReportName;
import com.intuit.ipp.services.ReportService;
import com.intuit.ipp.util.Config;

public class ReportTest {
	
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
					Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
					ReportService service = new ReportService(context);
					service.setStart_date("2014-01-01");
					service.setEnd_date("2014-03-20");
					service.setAccounting_method("Accrual");
					//Report r = service.executeReport("BalanceSheet");
					Report r = service.executeReport(ReportName.PROFITANDLOSS.toString());
					System.out.println("Report Header" + r.getHeader().getReportName());
					Rows rows = r.getRows();
					List<Row> rowlist = rows.getRow();
					Iterator<Row> i = rowlist.listIterator();

					while (i.hasNext())
					{
						Row row = i.next();
						System.out.println("Group" + row.getGroup());
						
						Header header = row.getHeader();
						List<ColData> hcoldatalist = header.getColData();
						Iterator<ColData> j = hcoldatalist.iterator();
						while(j.hasNext())
						{
							ColData coldata = j.next();
							System.out.println("Header ColData value" + coldata.getValue());
						}
						
						//summary
						Summary summary = row.getSummary();
						List<ColData> scoldatalist = summary.getColData();
						Iterator<ColData> k = scoldatalist.iterator();
						while(k.hasNext())
						{
							ColData coldata = k.next();
							System.out.println("Summary ColData value" + coldata.getValue());
						}
						
						System.out.println("Row Type" + row.getType().value());
						
						//columns
						List<ColData> rcoldatalist = row.getColData();
						Iterator<ColData> l = rcoldatalist.iterator();
						while(l.hasNext())
						{
							ColData coldata = l.next();
							System.out.println("Summary ColData value" + coldata.getValue());
							
						}
						
						//Rows
					
						
						
						
						Rows rows1 = row.getRows();
						List<Row> rowlist1 = rows1.getRow();
						Iterator<Row> m = rowlist1.listIterator();
						
						while (m.hasNext())
						{
							Row row1 = m.next();
							System.out.println("Group" + row1.getGroup());
							
							Header header1 = row1.getHeader();
							List<ColData> hcoldatalist1 = header1.getColData();
							Iterator<ColData> n = hcoldatalist1.iterator();
							while(n.hasNext())
							{
								ColData coldata = n.next();
								System.out.println("Header ColData value" + coldata.getValue());
							}
							
							//summary
							Summary summary1 = row1.getSummary();
							List<ColData> scoldatalist1 = summary1.getColData();
							Iterator<ColData> o = scoldatalist1.iterator();
							while(o.hasNext())
							{
								ColData coldata = o.next();
								System.out.println("Summary ColData value" + coldata.getValue());
							}
							
							System.out.println("Row Type" + row1.getType().value());
							
							//columns
							List<ColData> rcoldatalist1 = row1.getColData();
							Iterator<ColData> p = rcoldatalist1.iterator();
							while(p.hasNext())
							{
								ColData coldata = p.next();
								System.out.println("Summary ColData value" + coldata.getValue());
								
							}
						
						}			
						
						
					}
					
					
					
					
					System.out.println("Summary" + r.getRows().getRow().get(0).getSummary().getColData().get(1).getValue());
					//System.out.println("Report Header" + r.getHeader().getReportName());
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


