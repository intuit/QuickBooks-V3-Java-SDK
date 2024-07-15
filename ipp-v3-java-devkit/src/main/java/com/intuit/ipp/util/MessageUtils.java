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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.intuit.ipp.data.*;

/**
 * The MessageUtils helps to manage marshaling and unmarshaling of objects.
 * 
 */
public final class MessageUtils {

	/**
	 * constructor MessageUtils
	 */
	private MessageUtils() {
	}

	/**
	 * Inner class to enforce the proper initialization of the static variables
	 * in this utility class. This relies on the fact that inner classes are not
	 * loaded until referenced. See:
	 * http://en.wikipedia.org/wiki/Double-checked_locking
	 */
	private static class MessageUtilsHelper {
		
		/**
		 * variable privContext
		 */
		private static JAXBContext privContext = null;
		private static List<Object> enumList = null;
		private static List<Object> entityList = null;

		/**
		 * Create new or return existing JAXB context for IntuitEntity classes.
		 * 
		 * @return JAXBContext to Marshal or Unmarshal object
		 */
		public static JAXBContext getContext() throws JAXBException {
			if (privContext == null) {
				privContext = JAXBContext.newInstance(IntuitEntity.class.getPackage().getName());
			}
			return privContext;
		}
		
		public static List<Object> getWhitelistedEnums() {

			if (enumList == null) {
				enumList = new ArrayList<>();
				enumList.add(AccountClassificationEnum.class);
				enumList.add(AccountSubTypeEnum.class);
				enumList.add(AccountTypeEnum.class);
				enumList.add(AcquiredAsEnum.class);
				enumList.add(AgencyPaymentMethodEnum.class);
				enumList.add(APCreditCardOperationEnum.class);
				enumList.add(AttachableCategoryEnum.class);
				enumList.add(BillableStatusEnum.class);
				enumList.add(BillPaymentTypeEnum.class);
				enumList.add(BudgetEntryTypeEnum.class);
				enumList.add(BudgetTypeEnum.class);
				enumList.add(CCAVSMatchEnum.class);
				enumList.add(CCPaymentStatusEnum.class);
				enumList.add(CCSecurityCodeMatchEnum.class);
				enumList.add(CCTxnModeEnum.class);
				enumList.add(CCTxnTypeEnum.class);
				enumList.add(CISRateEnum.class);
				enumList.add(ColumnTypeEnum.class);
				enumList.add(ContactTypeEnum.class);
				enumList.add(ConvenienceFeeTypeEnum.class);
				enumList.add(CreditCardTypeEnum.class);
				enumList.add(CustomerTypeEnum.class);
				enumList.add(CustomFieldTypeEnum.class);
				enumList.add(DayOfWeekEnum.class);
				enumList.add(DeliveryErrorTypeEnum.class);
				enumList.add(DeliveryTypeEnum.class);
				enumList.add(DesktopEntityTypeEnum.class);
				enumList.add(DiscountTypeEnum.class);
				enumList.add(EmailAddressTypeEnum.class);
				enumList.add(EmailStatusEnum.class);
				enumList.add(EmployeeTypeEnum.class);
				enumList.add(EntityStatusEnum.class);
				enumList.add(EntityTypeEnum.class);
				enumList.add(EstimateStatusEnum.class);
				enumList.add(ETransactionEnabledStatusEnum.class);
				enumList.add(ETransactionStatusEnum.class);
				enumList.add(FaultTypeEnum.class);
				enumList.add(FinancingProductTypeEnum.class);
				enumList.add(Gender.class);
				enumList.add(GlobalTaxCalculationEnum.class);
				enumList.add(GTMConfigTypeEnum.class);
				enumList.add(IdDomainEnum.class);
				enumList.add(ItemCategoryTypeEnum.class);
				enumList.add(ItemTypeEnum.class);
				enumList.add(JobStatusEnum.class);
				enumList.add(JournalCodeTypeEnum.class);
				enumList.add(LineDetailTypeEnum.class);
				enumList.add(MonthEnum.class);
				enumList.add(ObjectNameEnumType.class);
				enumList.add(OLBTxnStatusEnum.class);
				enumList.add(OperationEnum.class);
				enumList.add(PaymentMethodEnum.class);
				enumList.add(PaymentStatusEnum.class);
				enumList.add(PaymentTypeEnum.class);
				enumList.add(PaySalesTaxEnum.class);
				enumList.add(PerItemAdjustEnum.class);
				enumList.add(PhysicalAddressTypeEnum.class);
				enumList.add(PostingTypeEnum.class);
				enumList.add(PriceLevelTypeEnum.class);
				enumList.add(PrintStatusEnum.class);
				enumList.add(PurchaseOrderStatusEnum.class);
				enumList.add(QboEntityTypeEnum.class);
				enumList.add(QboEstimateStatusEnum.class);
				enumList.add(ReimbursableTypeEnum.class);
				enumList.add(ReportBasisEnum.class);
				enumList.add(RoundingMethodEnum.class);
				enumList.add(RowTypeEnum.class);
				enumList.add(SalesRepTypeEnum.class);
				enumList.add(SalesTermTypeEnum.class);
				enumList.add(ServiceTypeEnum.class);
				enumList.add(SpecialItemTypeEnum.class);
				enumList.add(SpecialTaxTypeEnum.class);
				enumList.add(SubcontractorTypeEnum.class);
				enumList.add(SubscriptionPaymentsSettingEnum.class);
				enumList.add(SummarizeColumnsByEnum.class);
				enumList.add(SymbolPositionEnum.class);
				enumList.add(TaxApplicableOnEnum.class);
				enumList.add(TaxFormTypeEnum.class);
				enumList.add(TaxRateApplicableOnEnum.class);
				enumList.add(TaxRateDisplayTypeEnum.class);
				enumList.add(TaxReportBasisTypeEnum.class);
				enumList.add(TaxReturnStatusEnum.class);
				enumList.add(TaxReviewStatusEnum.class);
				enumList.add(TaxTypeApplicablityEnum.class);
				enumList.add(TelephoneDeviceTypeEnum.class);
				enumList.add(TelephoneNumberTypeEnum.class);
				enumList.add(TemplateTypeEnum.class);
				enumList.add(TimeActivityTypeEnum.class);
				enumList.add(TimeEntryUsedForPaychecksEnum.class);
				enumList.add(TransactionLocationTypeEnum.class);
				enumList.add(TxnSourceEnum.class);
				enumList.add(TxnTypeEnum.class);
				enumList.add(UOMBaseTypeEnum.class);
				enumList.add(UOMFeatureTypeEnum.class);
				enumList.add(WeekEnum.class);
				enumList.add(SourceTypeEnum.class);
			}
			return enumList;
		}
		
		public static List<Object> getWhitelistedEntities() {

			if (entityList == null) {
				entityList = new ArrayList<>();
				entityList.add(Account.class);
				entityList.add(Attachable.class);
				entityList.add(Bill.class);
				entityList.add(BillPayment.class);
				entityList.add(BooleanTypeCustomFieldDefinition.class);
				entityList.add(Budget.class);
				entityList.add(ChargeCredit.class);
				entityList.add(com.intuit.ipp.data.Class.class);
				entityList.add(Company.class);
				entityList.add(CompanyCurrency.class);
				entityList.add(CompanyInfo.class);
				entityList.add(ConvenienceFeeDetail.class);
				entityList.add(CreditCardPaymentTxn.class);
				entityList.add(CreditMemo.class);
				entityList.add(Currency.class);
				entityList.add(Customer.class);
				entityList.add(CustomerMsg.class);
				entityList.add(CustomerType.class);
				entityList.add(CustomFieldDefinition.class);
				entityList.add(DateTypeCustomFieldDefinition.class);
				entityList.add(Department.class);
				entityList.add(Deposit.class);
				entityList.add(EmailDeliveryInfo.class);
				entityList.add(Employee.class);
				entityList.add(Entity.class);
				entityList.add(Estimate.class);
				entityList.add(ExchangeRate.class);
				entityList.add(FixedAsset.class);
				entityList.add(InventorySite.class);
				entityList.add(Invoice.class);
				entityList.add(Item.class);
				entityList.add(JobType.class);
				entityList.add(JournalCode.class);
				entityList.add(JournalEntry.class);
				entityList.add(MasterAccount.class);
				entityList.add(MXGlobalInfo.class);
				entityList.add(NameBase.class);
				entityList.add(NameValue.class);
				entityList.add(NumberTypeCustomFieldDefinition.class);
				entityList.add(OLBAccount.class);
				entityList.add(OLBStatus.class);
				entityList.add(OLBTransaction.class);
				entityList.add(OtherName.class);
				entityList.add(Payment.class);
				entityList.add(PaymentMethod.class);
				entityList.add(Preferences.class);
				entityList.add(PriceLevel.class);
				entityList.add(PriceLevelPerItem.class);
				entityList.add(Purchase.class);
				entityList.add(PurchaseByVendor.class);
				entityList.add(PurchaseOrder.class);
				entityList.add(QbdtEntityIdMapping.class);
				entityList.add(RecurringTransaction.class);
				entityList.add(RefundReceipt.class);
				entityList.add(ReimburseCharge.class);
				entityList.add(SalesOrder.class);
				entityList.add(SalesReceipt.class);
				entityList.add(SalesRep.class);
				entityList.add(SalesTransaction.class);
				entityList.add(ShipMethod.class);
				entityList.add(StatementCharge.class);
				entityList.add(Status.class);
				entityList.add(StringTypeCustomFieldDefinition.class);
				entityList.add(SyncActivity.class);
				entityList.add(Task.class);
				entityList.add(TaxAgency.class);
				entityList.add(TaxClassification.class);
				entityList.add(TaxCode.class);
				entityList.add(TaxPayment.class);
				entityList.add(TaxRate.class);
				entityList.add(TaxReturn.class);
				entityList.add(TaxService.class);
				entityList.add(TDSMetadata.class);
				entityList.add(TemplateName.class);
				entityList.add(Term.class);
				entityList.add(TimeActivity.class);
				entityList.add(Transaction.class);
				entityList.add(Transfer.class);
				entityList.add(UOM.class);
				entityList.add(User.class);
				entityList.add(UserAlert.class);
				entityList.add(Vendor.class);
				entityList.add(VendorCredit.class);
				entityList.add(VendorType.class);
				entityList.add(RecurringScheduleInfo.class);
			}
			return entityList;
		}
	}

	/**
	 * Create Marshaller from the JAXB context.
	 * 
	 * @return Marshaller
	 */
	public static Marshaller createMarshaller() throws JAXBException {
		Marshaller marshaller = MessageUtilsHelper.getContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return marshaller;
	}

	/**
	 * Create UnMarshaller from the JAXB context.
	 * 
	 * @return UnMarshaller
	 */
	public static Unmarshaller createUnmarshaller() throws JAXBException {
		return MessageUtilsHelper.getContext().createUnmarshaller();
	}

	/**
	 * return Gson Object
	 * 
	 * @return Gson
	 */
	public static Gson getGson() {
		return new Gson();
	}
	
	public static List<Object> getWhitelistedEnums() {
		return MessageUtilsHelper.getWhitelistedEnums();
	}
	
	public static List<Object> getWhitelistedEntities() {
		return MessageUtilsHelper.getWhitelistedEntities();
	}
	
	
}
