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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;
import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.*;
import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.serialization.custom.*;
import com.intuit.ipp.util.Logger;
import jakarta.xml.bind.JAXBElement;
import java.lang.Class;

/**
 * class to serialize/deserialize the given data using JSON serialization algorithm
 */

public class JSONSerializer implements IEntitySerializer {

    /**
     * the logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();
    private static final ObjectMapper serializeMapper = getSerializeMapper();
    private static final ObjectMapper deserializeMapper = getDeserializeMapper();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> String serialize(T object) throws SerializationException {

        if (object == null) {
            return null;
        }
        String json = null;
        try {
            if (object instanceof TaxService) {
                json = serializeMapper.writeValueAsString(((TaxService) object));
            } else {
                json = serializeMapper.writeValueAsString(((JAXBElement<T>) object).getValue());
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
        try {
            if (cl.getName().equalsIgnoreCase("com.intuit.ipp.data.TaxService")) {
                intuitResponse = (Response) deserializeMapper.readValue(json, TaxService.class);
            } else {
                intuitResponse = deserializeMapper.readValue(json, IntuitResponse.class);
            }
        } catch (Exception e) {
            LOG.error("Exception while json deserialize", e);
            throw new SerializationException(e);
        }
        return intuitResponse;
    }

    /**
     * Deserialize mapper for {@link #deserialize(String, Class)}}
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getDeserializeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule("IntuitResponseDeserializer", new Version(1, 0, 0, null));
        simpleModule.addDeserializer(IntuitResponse.class, new IntuitResponseDeserializer());
        mapper.registerModule(simpleModule);
        simpleModule = new SimpleModule("TaxServiceDeserializer", new Version(1, 0, 0, null));
        simpleModule.addDeserializer(TaxService.class, new TaxServiceDeserializer());
        mapper.registerModule(simpleModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Serialize mapper for {@link #serialize(Object)}}
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getSerializeMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
        AnnotationIntrospector secondary = new JakartaXmlBindAnnotationIntrospector(objectMapper.getTypeFactory());
        AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);

        objectMapper.setAnnotationIntrospector(pair);
        objectMapper.setSerializationInclusion(Include.NON_NULL);

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

        module = new SimpleModule("AgencyPaymentMethodEnum", new Version(1, 0, 0, null));
        module.addSerializer(AgencyPaymentMethodEnum.class, new AgencyPaymentMethodEnumJsonSerializer());
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

        module = new SimpleModule("BudgetEntryTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(BudgetEntryTypeEnum.class, new BudgetEntryTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("BudgetTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(BudgetTypeEnum.class, new BudgetTypeEnumJsonSerializer());
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

        module = new SimpleModule("CISRateEnum", new Version(1, 0, 0, null));
        module.addSerializer(CISRateEnum.class, new CISRateEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ColumnTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ColumnTypeEnum.class, new ColTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ContactTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ContactTypeEnum.class, new ContactTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ConvenienceFeeTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ConvenienceFeeTypeEnum.class, new ConvenienceFeeTypeEnumJsonSerializer());
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

        module = new SimpleModule("DeliveryErrorTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(DeliveryErrorTypeEnum.class, new DeliveryErrorTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("DeliveryTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(DeliveryTypeEnum.class, new DeliveryTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("DesktopEntityTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(DesktopEntityTypeEnum.class, new DesktopEntityTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("DiscountTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(DiscountTypeEnum.class, new DiscountTypeEnumJsonSerializer());
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

        module = new SimpleModule("ETransactionEnabledStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(ETransactionEnabledStatusEnum.class, new ETransactionEnabledStatusEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ETransactionStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(ETransactionStatusEnum.class, new ETransactionStatusEnumJsonSerializer());
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

        module = new SimpleModule("ItemCategoryTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ItemCategoryTypeEnum.class, new ItemCategoryTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ItemTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ItemTypeEnum.class, new ItemTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("JobStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(JobStatusEnum.class, new JobStatusEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("JournalCodeTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(JournalCodeTypeEnum.class, new JournalCodeTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("LineDetailTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(LineDetailTypeEnum.class, new LineDetailTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("MonthEnum", new Version(1, 0, 0, null));
        module.addSerializer(MonthEnum.class, new MonthEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("objectNameEnumType", new Version(1, 0, 0, null));
        module.addSerializer(ObjectNameEnumType.class, new ObjectNameEnumTypeJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("OLBTxnStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(OLBTxnStatusEnum.class, new OLBTxnStatusEnumJsonSerializer());
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

        module = new SimpleModule("QboEntityTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(QboEntityTypeEnum.class, new QboEntityTypeEnumJsonSerializer());
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

        module = new SimpleModule("RowTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(RowTypeEnum.class, new RowTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SalesRepTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(SalesRepTypeEnum.class, new SalesRepTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SalesTermTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(SalesTermTypeEnum.class, new SalesTermTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("ServiceTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(ServiceTypeEnum.class, new ServiceTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SpecialItemTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(SpecialItemTypeEnum.class, new SpecialItemTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SpecialTaxTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(SpecialTaxTypeEnum.class, new SpecialTaxTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SubcontractorTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(SubcontractorTypeEnum.class, new SubcontractorTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SummarizeColumnsByEnum", new Version(1, 0, 0, null));
        module.addSerializer(SummarizeColumnsByEnum.class, new SummarizeColumnsByEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("SymbolPositionEnum", new Version(1, 0, 0, null));
        module.addSerializer(SymbolPositionEnum.class, new SymbolPositionEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxApplicableOnEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxApplicableOnEnum.class, new TaxApplicableOnEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxFormTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxFormTypeEnum.class, new TaxFormTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxRateApplicableOnEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxRateApplicableOnEnum.class, new TaxRateApplicableOnEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxRateDisplayTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxRateDisplayTypeEnum.class, new TaxRateDisplayTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxReportBasisTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxReportBasisTypeEnum.class, new TaxReportBasisTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxReturnStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxReturnStatusEnum.class, new TaxReturnStatusEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TaxReviewStatusEnum", new Version(1, 0, 0, null));
        module.addSerializer(TaxReviewStatusEnum.class, new TaxReviewStatusEnumJsonSerializer());
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

        module = new SimpleModule("TransactionLocationTypeEnum", new Version(1, 0, 0, null));
        module.addSerializer(TransactionLocationTypeEnum.class, new TransactionLocationTypeEnumJsonSerializer());
        objectMapper.registerModule(module);

        module = new SimpleModule("TxnSourceEnum", new Version(1, 0, 0, null));
        module.addSerializer(TxnSourceEnum.class, new TxnSourceEnumJsonSerializer());
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

        SimpleModule testModule = new SimpleModule("BatchItemRequest", new Version(1, 0, 0, null));
        testModule.addSerializer(BatchItemRequest.class, new BatchItemRequestSerializer());
        objectMapper.registerModule(testModule);

        testModule = new SimpleModule("RecurringTransaction", new Version(1, 0, 0, null));
        testModule.addSerializer(RecurringTransaction.class, new RecurringTransactionSerializer());
        objectMapper.registerModule(testModule);
        return objectMapper;
    }

    @Override
    public Response deserializeEntitlements(String decompressedData, Class<EntitlementsResponse> cl)
            throws SerializationException {
        // TODO Auto-generated method stub
        return null;
    }
}