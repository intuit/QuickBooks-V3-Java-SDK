package com.intuit.ipp.example.qbo.auscompany;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.EmailAddress;
import com.intuit.ipp.data.JobInfo;
import com.intuit.ipp.data.JobStatusEnum;
import com.intuit.ipp.data.PhysicalAddress;
import com.intuit.ipp.data.TelephoneNumber;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.DateUtils;

public class CustomerCreate {
	
	public static void main(String[] args) {

		OAuthAuthorizer oauth = new OAuthAuthorizer(Credentials.CONSUMER_KEY, Credentials.CONSUMER_SECRET, Credentials.ACCESS_TOKEN, Credentials.ACCESS_TOKEN_SECRET);

		Context context = null;
		
		try {
			context = new Context(oauth, Credentials.APP_TOKEN, ServiceType.QBO, Credentials.REALM_ID);
			DataService service = new DataService(context);

			Customer customer = getCustomerForCreate();
            
			Customer customerOut = service.add(customer);
			
			System.out.println("Customer is created!...");
			System.out.println("Id : " + customerOut.getId());
			System.out.println("DisplayName : " + customerOut.getDisplayName());
			System.out.println("Title : " + customerOut.getTitle());
			
		} catch (FMSException e) {
			e.printStackTrace();
		}
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static Customer getCustomerForCreate() {
		
		Customer customer = new Customer();
		
		try {
			
			// Mandatory Fields
			customer.setDisplayName(getUUID().substring(0, 25));
			customer.setTitle(getUUID().substring(0, 7));
			customer.setGivenName(getUUID().substring(0, 20));
			customer.setMiddleName(getUUID().substring(0, 4));
			customer.setFamilyName(getUUID().substring(0, 20));

			// Optional Fields
			customer.setOrganization(false);
			customer.setSuffix("Sr.");
			customer.setCompanyName("Nidhi Sharma");
			customer.setPrintOnCheckName("Print name");
			customer.setActive(true);

			TelephoneNumber primaryNum = new TelephoneNumber();
			primaryNum.setDeviceType("LandLine");
			primaryNum.setFreeFormNumber("(650)111-1111");
			primaryNum.setDefault(true);
			primaryNum.setTag("Business");
			customer.setPrimaryPhone(primaryNum);

			TelephoneNumber alternativeNum = new TelephoneNumber();
			alternativeNum.setDeviceType("LandLine");
			alternativeNum.setFreeFormNumber("(650)111-1111");
			alternativeNum.setDefault(false);
			alternativeNum.setTag("Business");
			customer.setAlternatePhone(alternativeNum);

			TelephoneNumber mobile = new TelephoneNumber();
			mobile.setDeviceType("LandLine");
			mobile.setFreeFormNumber("(650)111-1111");
			mobile.setDefault(false);
			mobile.setTag("Home");
			customer.setMobile(mobile);

			TelephoneNumber fax = new TelephoneNumber();
			fax.setDeviceType("LandLine");
			fax.setFreeFormNumber("(650)111-1111");
			fax.setDefault(false);
			fax.setTag("Business");
			customer.setFax(fax);

			EmailAddress emailAddr = new EmailAddress();
			emailAddr.setAddress("test@testing.com");
			customer.setPrimaryEmailAddr(emailAddr);

			List<EmailAddress> addressList = new ArrayList<EmailAddress>();
			EmailAddress alternativeEmail = new EmailAddress();
			alternativeEmail.setAddress("test@testing.com");
			alternativeEmail.setDefault(true);
			alternativeEmail.setTag("Business");

			addressList.add(alternativeEmail);
			//customer.setOtherEmailAddresses(addressList);

			customer.setContactName("Contact Name");
			customer.setAltContactName("Alternate Name");
			customer.setNotes("Testing Notes");
			customer.setBalance(new BigDecimal("100055.55"));
			customer.setOpenBalanceDate(DateUtils.getCurrentDateTime());
			customer.setBalanceWithJobs(new BigDecimal("5055.5"));
			customer.setCreditLimit(new BigDecimal("200000"));
			customer.setAcctNum("Test020102");
			customer.setResaleNum("40");
			customer.setJob(false);

			JobInfo jobInfo = new JobInfo();
			jobInfo.setDescription("In Progress");
			jobInfo.setStatus(JobStatusEnum.IN_PROGRESS);
			jobInfo.setStartDate(DateUtils.getDateWithPrevDays(2));
			jobInfo.setEndDate(DateUtils.getDateWithNextDays(5));
			jobInfo.setProjectedEndDate(DateUtils.getDateWithNextDays(5));
			customer.setJobInfo(jobInfo);

			PhysicalAddress billingAdd = new PhysicalAddress();
			billingAdd.setLine1("Testing1");
			billingAdd.setLine2("Testing2");
			billingAdd.setLine3("Testing3");
			billingAdd.setCity("Bangalore");
			billingAdd.setCountry("India");
			billingAdd.setCountrySubDivisionCode("KA");
			billingAdd.setPostalCode("560097");
			customer.setBillAddr(billingAdd);

			PhysicalAddress shippingAdd = new PhysicalAddress();
			shippingAdd.setLine1("Shipping1");
			shippingAdd.setLine2("Shipping1");
			shippingAdd.setLine3("Shipping1");
			shippingAdd.setCity("Bangalore");
			shippingAdd.setCountry("India");
			shippingAdd.setCountrySubDivisionCode("KA");
			shippingAdd.setPostalCode("560097");
			customer.setShipAddr(shippingAdd);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
}
