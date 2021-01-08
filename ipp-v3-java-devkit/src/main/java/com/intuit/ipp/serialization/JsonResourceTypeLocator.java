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

import java.lang.Class;
import java.util.HashMap;
import java.util.Map;

import com.intuit.ipp.data.*;

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
			put("CreditCardPaymentTxn", CreditCardPaymentTxn.class);
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
			put("RecurringTransaction", RecurringTransaction.class);
			put("RefundReceipt", RefundReceipt.class);
			put("ReimburseCharge", ReimburseCharge.class);
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
			put("TaxPayment", TaxPayment.class);
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
