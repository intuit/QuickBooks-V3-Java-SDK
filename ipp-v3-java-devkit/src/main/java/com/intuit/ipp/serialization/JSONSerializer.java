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

import javax.xml.bind.JAXBElement;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.APCreditCardOperationEnum;
import com.intuit.ipp.data.AccountClassificationEnum;
import com.intuit.ipp.data.AccountSubTypeEnum;
import com.intuit.ipp.data.AccountTypeEnum;
import com.intuit.ipp.data.AcquiredAsEnum;
import com.intuit.ipp.data.AttachableCategoryEnum;
import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.BillPaymentTypeEnum;
import com.intuit.ipp.data.BillableStatusEnum;
import com.intuit.ipp.data.CCAVSMatchEnum;
import com.intuit.ipp.data.CCPaymentStatusEnum;
import com.intuit.ipp.data.CCSecurityCodeMatchEnum;
import com.intuit.ipp.data.CCTxnModeEnum;
import com.intuit.ipp.data.CCTxnTypeEnum;
import com.intuit.ipp.data.ColumnTypeEnum;
import com.intuit.ipp.data.ContactTypeEnum;
import com.intuit.ipp.data.CreditCardTypeEnum;
import com.intuit.ipp.data.CustomFieldTypeEnum;
import com.intuit.ipp.data.CustomerTypeEnum;
import com.intuit.ipp.data.DayOfWeekEnum;
import com.intuit.ipp.data.EmailAddressTypeEnum;
import com.intuit.ipp.data.EmailStatusEnum;
import com.intuit.ipp.data.EmployeeTypeEnum;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.data.EntityStatusEnum;
import com.intuit.ipp.data.EntityTypeEnum;
import com.intuit.ipp.data.EstimateStatusEnum;
import com.intuit.ipp.data.FaultTypeEnum;
import com.intuit.ipp.data.Gender;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import com.intuit.ipp.data.IdDomainEnum;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.JobStatusEnum;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.MonthEnum;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.data.PaySalesTaxEnum;
import com.intuit.ipp.data.PaymentMethodEnum;
import com.intuit.ipp.data.PaymentStatusEnum;
import com.intuit.ipp.data.PaymentTypeEnum;
import com.intuit.ipp.data.PerItemAdjustEnum;
import com.intuit.ipp.data.PhysicalAddressTypeEnum;
import com.intuit.ipp.data.PostingTypeEnum;
import com.intuit.ipp.data.PriceLevelTypeEnum;
import com.intuit.ipp.data.PrintStatusEnum;
import com.intuit.ipp.data.PurchaseOrderStatusEnum;
import com.intuit.ipp.data.QboEstimateStatusEnum;
import com.intuit.ipp.data.ReimbursableTypeEnum;
import com.intuit.ipp.data.ReportBasisEnum;
import com.intuit.ipp.data.TaxRateApplicableOnEnum;
import com.intuit.ipp.data.TaxService;
//import com.intuit.ipp.data.ReportNameEnum;
import com.intuit.ipp.data.RoundingMethodEnum;
import com.intuit.ipp.data.SalesRepTypeEnum;
import com.intuit.ipp.data.SalesTermTypeEnum;
import com.intuit.ipp.data.SpecialItemTypeEnum;
import com.intuit.ipp.data.SpecialTaxTypeEnum;
import com.intuit.ipp.data.SummarizeColumnsByEnum;
import com.intuit.ipp.data.SymbolPositionEnum;
import com.intuit.ipp.data.TaxApplicableOnEnum;
import com.intuit.ipp.data.TaxRateDisplayTypeEnum;
import com.intuit.ipp.data.TaxTypeApplicablityEnum;
import com.intuit.ipp.data.TelephoneDeviceTypeEnum;
import com.intuit.ipp.data.TelephoneNumberTypeEnum;
import com.intuit.ipp.data.TemplateTypeEnum;
import com.intuit.ipp.data.TimeActivityTypeEnum;
import com.intuit.ipp.data.TimeEntryUsedForPaychecksEnum;
import com.intuit.ipp.data.TxnTypeEnum;
import com.intuit.ipp.data.UOMBaseTypeEnum;
import com.intuit.ipp.data.UOMFeatureTypeEnum;
import com.intuit.ipp.data.WeekEnum;
import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.serialization.custom.APCreditCardOperationEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.AccountClassificationEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.AccountSubTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.AccountTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.AcquiredAsEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.AttachableCategoryEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.BillPaymentTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.BillableStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CCAVSMatchEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CCPaymentStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CCSecurityCodeMatchEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CCTxnModeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CCTxnTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.ColTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.ContactTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CreditCardTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CustomFieldTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.CustomerTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.DayOfWeekEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EmailAddressTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EmailStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EmployeeTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EntityStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EntityTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.EstimateStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.FaultTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.GenderEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.GlobalTaxCalculationEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.IdDomainEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.ItemTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.JobStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.LineDetailTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.MonthEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.OperationEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PaySalesTaxEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PaymentMethodEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PaymentStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PaymentTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PerItemAdjustEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PhysicalAddressTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PostingTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PriceLevelTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PrintStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.PurchaseOrderStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.QboEstimateStatusEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.ReimbursableTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.ReportBasisEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TaxRateApplicableOnEnumJsonSerializer;
//import com.intuit.ipp.serialization.custom.ReportNameEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.RoundingMethodEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SalesRepTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SalesTermTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SpecialItemTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SpecialTaxTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SummarizeColumnsByEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.SymbolPositionEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TaxApplicableOnEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TaxRateDisplayTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TaxTypeApplicablityEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TelephoneDeviceTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TelephoneNumberTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TemplateTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TimeActivityTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TimeEntryUsedForPaychecksEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.TxnTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.UOMBaseTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.UOMFeatureTypeEnumJsonSerializer;
import com.intuit.ipp.serialization.custom.WeekEnumJsonSerializer;
import com.intuit.ipp.util.Logger;

/**
 * class to serialize/deserialize the given data using JSON serialization algorithm
 * 
 */

public class JSONSerializer implements IEntitySerializer {

	/**
	 * the logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> String serialize(T object) throws SerializationException {

		if (object == null) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);

		mapper.setAnnotationIntrospector(pair);
		mapper.setSerializationInclusion(Include.NON_NULL);

		registerModulesForEnum(mapper);

		SimpleModule testModule = new SimpleModule("BatchItemRequest", new Version(1, 0, 0, null));
		testModule.addSerializer(BatchItemRequest.class, new BatchItemRequestSerializer());
		mapper.registerModule(testModule);

		String json = null;
		try {
			if(object instanceof TaxService)
			{
				json = mapper.writeValueAsString(((TaxService) object));
			}
			else
			{
			json = mapper.writeValueAsString(((JAXBElement<T>) object).getValue());
			}
		} catch (Exception e) {
			LOG.error("Exception while json serialize", e);
			throw new SerializationException(e);
		}
		return json;
	}

	/**
	 * {@inheritDoc}
	 */
	public Response deserialize(String json, Class<?> cl) throws SerializationException {

		Response intuitResponse = null;
		ObjectMapper mapper = new ObjectMapper();

		SimpleModule simpleModule = new SimpleModule("IntuitResponseDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(IntuitResponse.class, new IntuitResponseDeserializer());
		mapper.registerModule(simpleModule);

		simpleModule = new SimpleModule("TaxServiceDeserializer", new Version(1, 0, 0, null));
		simpleModule.addDeserializer(TaxService.class, new TaxServiceDeserializer());
		mapper.registerModule(simpleModule);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		


		try {
			if(cl.getName().equalsIgnoreCase("com.intuit.ipp.data.TaxService"))
			{
				intuitResponse = (Response) mapper.readValue(json, TaxService.class);
			}
			else
			{
			intuitResponse = mapper.readValue(json, IntuitResponse.class);
			}
		} catch (Exception e) {
			LOG.error("Exception while json deserialize", e);
			throw new SerializationException(e);
		}
		return intuitResponse;
	}

	/**
	 * Method to add custom Json serializers for all Enum classes
	 * 
	 * @param objectMapper the Jackson object mapper
	 */
	private void registerModulesForEnum(ObjectMapper objectMapper) {
		SimpleModule module = new SimpleModule("AccountClassificationEnum", new Version(1, 0, 0, null));
	    module.addSerializer(AccountClassificationEnum.class, new AccountClassificationEnumJsonSerializer());
	    objectMapper.registerModule(module);
	    
	    module = new SimpleModule("AccountSubTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(AccountSubTypeEnum.class, new AccountSubTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("AccountTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(AccountTypeEnum.class, new AccountTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("AcquiredAsEnum", new Version(1, 0, 0, null));
	    module.addSerializer(AcquiredAsEnum.class, new AcquiredAsEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("APCreditCardOperationEnum", new Version(1, 0, 0, null));
	    module.addSerializer(APCreditCardOperationEnum.class, new APCreditCardOperationEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("AttachableCategoryEnum", new Version(1, 0, 0, null));
	    module.addSerializer(AttachableCategoryEnum.class, new AttachableCategoryEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("BillableStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(BillableStatusEnum.class, new BillableStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("BillPaymentTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(BillPaymentTypeEnum.class, new BillPaymentTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CCAVSMatchEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CCAVSMatchEnum.class, new CCAVSMatchEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CCPaymentStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CCPaymentStatusEnum.class, new CCPaymentStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CCSecurityCodeMatchEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CCSecurityCodeMatchEnum.class, new CCSecurityCodeMatchEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CCTxnModeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CCTxnModeEnum.class, new CCTxnModeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CCTxnTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CCTxnTypeEnum.class, new CCTxnTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("ColumnTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(ColumnTypeEnum.class, new ColTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("ContactTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(ContactTypeEnum.class, new ContactTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CreditCardTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CreditCardTypeEnum.class, new CreditCardTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CustomerTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CustomerTypeEnum.class, new CustomerTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("CustomFieldTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(CustomFieldTypeEnum.class, new CustomFieldTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("DayOfWeekEnum", new Version(1, 0, 0, null));
	    module.addSerializer(DayOfWeekEnum.class, new DayOfWeekEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EmailAddressTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EmailAddressTypeEnum.class, new EmailAddressTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EmailStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EmailStatusEnum.class, new EmailStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EmployeeTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EmployeeTypeEnum.class, new EmployeeTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EntityStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EntityStatusEnum.class, new EntityStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EntityTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EntityTypeEnum.class, new EntityTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("EstimateStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(EstimateStatusEnum.class, new EstimateStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("FaultTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(FaultTypeEnum.class, new FaultTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("GenderEnum", new Version(1, 0, 0, null));
	    module.addSerializer(Gender.class, new GenderEnumJsonSerializer());
	    objectMapper.registerModule(module);
	    module = new SimpleModule("GlobalTaxCalculationEnum", new Version(1, 0, 0, null));
	    module.addSerializer(GlobalTaxCalculationEnum.class, new GlobalTaxCalculationEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("IdDomainEnum", new Version(1, 0, 0, null));
	    module.addSerializer(IdDomainEnum.class, new IdDomainEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("ItemTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(ItemTypeEnum.class, new ItemTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("JobStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(JobStatusEnum.class, new JobStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("LineDetailTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(LineDetailTypeEnum.class, new LineDetailTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("MonthEnum", new Version(1, 0, 0, null));
	    module.addSerializer(MonthEnum.class, new MonthEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("OperationEnum", new Version(1, 0, 0, null));
	    module.addSerializer(OperationEnum.class, new OperationEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PaymentMethodEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PaymentMethodEnum.class, new PaymentMethodEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PaymentStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PaymentStatusEnum.class, new PaymentStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PaymentTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PaymentTypeEnum.class, new PaymentTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PaySalesTaxEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PaySalesTaxEnum.class, new PaySalesTaxEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PerItemAdjustEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PerItemAdjustEnum.class, new PerItemAdjustEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PhysicalAddressTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PhysicalAddressTypeEnum.class, new PhysicalAddressTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PostingTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PostingTypeEnum.class, new PostingTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PriceLevelTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PriceLevelTypeEnum.class, new PriceLevelTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PrintStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PrintStatusEnum.class, new PrintStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("PurchaseOrderStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(PurchaseOrderStatusEnum.class, new PurchaseOrderStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("QboEstimateStatusEnum", new Version(1, 0, 0, null));
	    module.addSerializer(QboEstimateStatusEnum.class, new QboEstimateStatusEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("ReimbursableTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(ReimbursableTypeEnum.class, new ReimbursableTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("ReportBasisEnum", new Version(1, 0, 0, null));
	    module.addSerializer(ReportBasisEnum.class, new ReportBasisEnumJsonSerializer());
	    objectMapper.registerModule(module);

//	    module = new SimpleModule("ReportNameEnum", new Version(1, 0, 0, null));
//	    module.addSerializer(ReportNameEnum.class, new ReportNameEnumJsonSerializer());
//	    objectMapper.registerModule(module);

	    module = new SimpleModule("RoundingMethodEnum", new Version(1, 0, 0, null));
	    module.addSerializer(RoundingMethodEnum.class, new RoundingMethodEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SalesRepTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SalesRepTypeEnum.class, new SalesRepTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SalesTermTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SalesTermTypeEnum.class, new SalesTermTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SpecialItemTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SpecialItemTypeEnum.class, new SpecialItemTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SpecialTaxTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SpecialTaxTypeEnum.class, new SpecialTaxTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SummarizeColumnsByEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SummarizeColumnsByEnum.class, new SummarizeColumnsByEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("SymbolPositionEnum", new Version(1, 0, 0, null));
	    module.addSerializer(SymbolPositionEnum.class, new SymbolPositionEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TaxRateDisplayTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TaxRateDisplayTypeEnum.class, new TaxRateDisplayTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TaxTypeApplicablityEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TaxTypeApplicablityEnum.class, new TaxTypeApplicablityEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TelephoneDeviceTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TelephoneDeviceTypeEnum.class, new TelephoneDeviceTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TelephoneNumberTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TelephoneNumberTypeEnum.class, new TelephoneNumberTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TemplateTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TemplateTypeEnum.class, new TemplateTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TimeActivityTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TimeActivityTypeEnum.class, new TimeActivityTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TimeEntryUsedForPaychecksEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TimeEntryUsedForPaychecksEnum.class, new TimeEntryUsedForPaychecksEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TxnTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TxnTypeEnum.class, new TxnTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("UOMBaseTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(UOMBaseTypeEnum.class, new UOMBaseTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("UOMFeatureTypeEnum", new Version(1, 0, 0, null));
	    module.addSerializer(UOMFeatureTypeEnum.class, new UOMFeatureTypeEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("WeekEnum", new Version(1, 0, 0, null));
	    module.addSerializer(WeekEnum.class, new WeekEnumJsonSerializer());
	    objectMapper.registerModule(module);
	    module = new SimpleModule("TaxRateApplicableOnEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TaxRateApplicableOnEnum.class, new TaxRateApplicableOnEnumJsonSerializer());
	    objectMapper.registerModule(module);

	    module = new SimpleModule("TaxApplicableOnEnum", new Version(1, 0, 0, null));
	    module.addSerializer(TaxApplicableOnEnum.class, new TaxApplicableOnEnumJsonSerializer());
	    objectMapper.registerModule(module);
	}

	@Override
	public Response deserializeEntitlements(String decompressedData, Class<EntitlementsResponse> cl)
			throws SerializationException {
		// TODO Auto-generated method stub
		return null;
	}
}
