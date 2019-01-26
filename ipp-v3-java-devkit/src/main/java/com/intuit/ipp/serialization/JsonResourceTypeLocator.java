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
package com.intuit.ipp.serialization;

import java.util.HashMap;
import java.util.Map;

import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.BillPayment;
import com.intuit.ipp.data.Budget;
import com.intuit.ipp.data.Company;
import com.intuit.ipp.data.CompanyCurrency;
import com.intuit.ipp.data.CompanyInfo;
import com.intuit.ipp.data.CreditMemo;
import com.intuit.ipp.data.Currency;
import com.intuit.ipp.data.CustomField;
import com.intuit.ipp.data.CustomFieldDefinition;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.CustomerMsg;
import com.intuit.ipp.data.CustomerType;
import com.intuit.ipp.data.Department;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.Employee;
import com.intuit.ipp.data.Estimate;
import com.intuit.ipp.data.ExchangeRate;
import com.intuit.ipp.data.FixedAsset;
import com.intuit.ipp.data.InventorySite;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.JobType;
import com.intuit.ipp.data.JournalCode;
import com.intuit.ipp.data.JournalEntry;
import com.intuit.ipp.data.Money;
import com.intuit.ipp.data.OLBStatus;
import com.intuit.ipp.data.OLBTransaction;
import com.intuit.ipp.data.OtherName;
import com.intuit.ipp.data.Payment;
import com.intuit.ipp.data.PaymentMethod;
import com.intuit.ipp.data.Preferences;
import com.intuit.ipp.data.PriceLevel;
import com.intuit.ipp.data.PriceLevelPerItem;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.data.PurchaseOrder;
import com.intuit.ipp.data.QueryResponse;
import com.intuit.ipp.data.RefundReceipt;
import com.intuit.ipp.data.SalesOrder;
import com.intuit.ipp.data.SalesReceipt;
import com.intuit.ipp.data.SalesRep;
import com.intuit.ipp.data.ShipMethod;
import com.intuit.ipp.data.StatementCharge;
import com.intuit.ipp.data.Status;
import com.intuit.ipp.data.SyncActivity;
import com.intuit.ipp.data.SyncError;
import com.intuit.ipp.data.SyncErrorResponse;
import com.intuit.ipp.data.SyncObject;
import com.intuit.ipp.data.Task;
import com.intuit.ipp.data.TaxAgency;
import com.intuit.ipp.data.TaxClassification;
import com.intuit.ipp.data.TaxCode;
import com.intuit.ipp.data.TaxRate;
import com.intuit.ipp.data.TaxService;
import com.intuit.ipp.data.TemplateName;
import com.intuit.ipp.data.Term;
import com.intuit.ipp.data.TimeActivity;
import com.intuit.ipp.data.Transaction;
import com.intuit.ipp.data.Transfer;
import com.intuit.ipp.data.UOM;
import com.intuit.ipp.data.User;
import com.intuit.ipp.data.UserAlert;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.data.VendorCredit;
import com.intuit.ipp.data.VendorType;

/**
 * Class to hold the resource type locators for JSON to deserialize
 */
public final class JsonResourceTypeLocator {

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private JsonResourceTypeLocator() {
	}
	
	/**
	 * variable typeRegistry
	 */
	@SuppressWarnings("serial")
	private static Map<String, Class<?>> typeRegistry = new HashMap<String, Class<?>>() {
		{
			put("Account", Account.class);
			put("Attachable", Attachable.class);
			put("AttachableResponse", AttachableResponse.class);
			put("BatchItemResponse", BatchItemResponse.class);
			put("Bill", Bill.class);
			put("BillPayment", BillPayment.class);
			put("Budget", Budget.class);
			put("Class", com.intuit.ipp.data.Class.class);
			put("Company", Company.class);
			put("CompanyCurrency", CompanyCurrency.class);
			put("CompanyInfo", CompanyInfo.class);
			put("CreditMemo", CreditMemo.class);
			put("Currency", Currency.class);
			put("Customer", Customer.class);
			put("CustomerMsg", CustomerMsg.class);
			put("CustomerType", CustomerType.class);
			put("CustomField", CustomField.class);
			put("CustomFieldDefinition", CustomFieldDefinition.class);
			put("Department", Department.class);
			put("Deposit", Deposit.class);
			put("Employee", Employee.class);
			put("Estimate", Estimate.class);
			put("ExchangeRate", ExchangeRate.class);
			put("FixedAsset", FixedAsset.class);
			put("InventorySite", InventorySite.class);
			put("Invoice", Invoice.class);
			put("Item", Item.class);
			put("JobType", JobType.class);
			put("JournalCode", JournalCode.class);
			put("JournalEntry", JournalEntry.class);
			put("Money", Money.class);
			put("Payment", Payment.class);
			put("PaymentMethod", PaymentMethod.class);
			put("Preferences", Preferences.class);
			put("PriceLevel", PriceLevel.class);
			put("PriceLevelPerItem", PriceLevelPerItem.class);
			put("Purchase", Purchase.class);
			put("PurchaseOrder", PurchaseOrder.class);
			put("OLBStatus", OLBStatus.class);
			put("OLBTransaction", OLBTransaction.class);
			put("OtherName", OtherName.class);
			put("QueryResponse", QueryResponse.class);
			put("RefundReceipt", RefundReceipt.class);
			put("SalesOrder", SalesOrder.class);
			put("SalesReceipt", SalesReceipt.class);
			put("SalesRep", SalesRep.class);
			put("ShipMethod", ShipMethod.class);
			put("StatementCharge", StatementCharge.class);
			put("Status", Status.class);
			put("SyncActivity", SyncActivity.class);
			put("SyncObject", SyncObject.class);
			put("SyncError", SyncError.class);
			put("SyncErrorResponse", SyncErrorResponse.class);
			put("Task", Task.class);
			put("TaxAgency", TaxAgency.class);
			put("TaxClassification", TaxClassification.class);
			put("TaxCode", TaxCode.class);
			put("TaxRate", TaxRate.class);
			put("TaxService", TaxService.class);
			put("TemplateName", TemplateName.class);
			put("Term", Term.class);
			put("TimeActivity", TimeActivity.class);
			put("Transaction", Transaction.class);
			put("Transfer", Transfer.class);
			put("UOM", UOM.class);
			put("User", User.class);
			put("UserAlert", UserAlert.class);
			put("Vendor", Vendor.class);
			put("VendorCredit", VendorCredit.class);
			put("VendorType", VendorType.class);
			
		}
	};

	/**
	 * Method to get the look up type
	 * 
	 * @param entity the entity
	 * @return the class type
	 */
	public static Class<?> lookupType(String entity) {
		return typeRegistry.get(entity);
	}
}
