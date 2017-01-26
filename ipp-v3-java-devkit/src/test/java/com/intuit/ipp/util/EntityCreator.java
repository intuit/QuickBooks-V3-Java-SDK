/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.CustomField;
import com.intuit.ipp.data.CustomFieldTypeEnum;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.ObjectNameEnumType;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.IAuthorizer;
import com.intuit.ipp.services.DataService;

public class EntityCreator {

	private static Context context;
	private static DataService service;
	public static String billId;
	public static Customer customer;

	private static void setup(IAuthorizer authorizer) throws FMSException {
		//IPPHelper ippHelper = IPPHelper.getInstance();
		//IAMCookieAuthorizer authorizer = new IAMCookieAuthorizer(ippHelper.getCookie());
		//TicketAuthorizer authorizer = new TicketAuthorizer(ippHelper.getPlatformTicket(), ippHelper.getAppToken());
		service = new DataService(context);
	}

	public static void addCustomer(IAuthorizer authorizer, Context con) throws FMSException {
		context = con;
		setup(authorizer);
		
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + Calendar.getInstance().getTimeInMillis());
		customerIn.setCompanyName("Company Name Test" + Calendar.getInstance().getTimeInMillis());
		Customer customerOut = service.add(customerIn);
		customer = customerOut;
	}

	public void deleteCustomer() throws FMSException {
		try {
			Customer customerIn = new Customer();
			customerIn.setId(customer.getId());
			service.delete(customerIn);
		} catch (Exception ex) {

		}
	}

	public static void addBill(IAuthorizer authorizer, Context con) throws FMSException {
		context = con;
		setup(authorizer);
		
		Bill billIn = new Bill();
		billIn.setSyncToken("0");
		CustomField customField = new CustomField();
		customField.setDefinitionId("123");
		customField.setName("CustName");
		customField.setType(CustomFieldTypeEnum.STRING_TYPE);
		customField.setStringValue("val");
		List<CustomField> customFields = new ArrayList<CustomField>();
		customFields.add(customField);
		billIn.setCustomField(customFields);
		
		ReferenceType refAPAcc = new ReferenceType();
		refAPAcc.setName("Account Payable");
		refAPAcc.setType(ObjectNameEnumType.ACCOUNT.value());
		refAPAcc.setValue("QB:37");
		
		billIn.setAPAccountRef(refAPAcc);
		billIn.setTotalAmt(new BigDecimal(0));
		//billIn.setDocNumber("Bill" + Calendar.getInstance().getTimeInMillis());
		//billIn.setBillEmail("abcd@abc.com");
		Bill billOut = service.add(billIn);
		billId = billOut.getId();
	}
}
