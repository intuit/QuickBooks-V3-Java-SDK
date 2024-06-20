//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.Response;
import com.intuit.sb.cdm.util.v3.DateTimeAdapter;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * IntuitResponse is a holder of all types of entities that come as part of response
 * 
 * <p>Java class for IntuitResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntuitResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Warnings" type="{http://schema.intuit.com/finance/v3}Warnings" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{http://schema.intuit.com/finance/v3}IntuitObject"/&gt;
 *           &lt;element name="Fault" type="{http://schema.intuit.com/finance/v3}Fault" minOccurs="0"/&gt;
 *           &lt;element name="Report" type="{http://schema.intuit.com/finance/v3}Report" minOccurs="0"/&gt;
 *           &lt;element name="QueryResponse" type="{http://schema.intuit.com/finance/v3}QueryResponse" minOccurs="0"/&gt;
 *           &lt;element name="BatchItemResponse" type="{http://schema.intuit.com/finance/v3}BatchItemResponse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="CDCResponse" type="{http://schema.intuit.com/finance/v3}CDCResponse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="AttachableResponse" type="{http://schema.intuit.com/finance/v3}AttachableResponse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="SyncErrorResponse" type="{http://schema.intuit.com/finance/v3}SyncErrorResponse" minOccurs="0"/&gt;
 *           &lt;element name="OLBTransaction" type="{http://schema.intuit.com/finance/v3}OLBTransaction" minOccurs="0"/&gt;
 *           &lt;element name="OLBStatus" type="{http://schema.intuit.com/finance/v3}OLBStatus" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="requestId" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntuitResponse", propOrder = {
    "warnings",
    "intuitObject",
    "fault",
    "report",
    "queryResponse",
    "batchItemResponse",
    "cdcResponse",
    "attachableResponse",
    "syncErrorResponse",
    "olbTransaction",
    "olbStatus"
})
public class IntuitResponse
    implements Serializable, IEntity, Response
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Warnings")
    protected Warnings warnings;
    @XmlElementRef(name = "IntuitObject", namespace = "http://schema.intuit.com/finance/v3", type = JAXBElement.class, required = false)
    protected JAXBElement<? extends IntuitEntity> intuitObject;
    @XmlElement(name = "Fault")
    protected Fault fault;
    @XmlElement(name = "Report")
    protected Report report;
    @XmlElement(name = "QueryResponse")
    protected QueryResponse queryResponse;
    @XmlElement(name = "BatchItemResponse")
    protected List<BatchItemResponse> batchItemResponse;
    @XmlElement(name = "CDCResponse")
    protected List<CDCResponse> cdcResponse;
    @XmlElement(name = "AttachableResponse")
    protected List<AttachableResponse> attachableResponse;
    @XmlElement(name = "SyncErrorResponse")
    protected SyncErrorResponse syncErrorResponse;
    @XmlElement(name = "OLBTransaction")
    protected OLBTransaction olbTransaction;
    @XmlElement(name = "OLBStatus")
    protected OLBStatus olbStatus;
    @XmlAttribute(name = "requestId")
    protected String requestId;
    @XmlAttribute(name = "time")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date time;
    @XmlAttribute(name = "status")
    protected String status;

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link Warnings }
     *     
     */
    public Warnings getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Warnings }
     *     
     */
    public void setWarnings(Warnings value) {
        this.warnings = value;
    }

    /**
     * Any IntuitEntity derived entity like Customer, Invoice can be part of response
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Account }{@code >}
     *     {@link JAXBElement }{@code <}{@link Attachable }{@code >}
     *     {@link JAXBElement }{@code <}{@link Bill }{@code >}
     *     {@link JAXBElement }{@code <}{@link BillPayment }{@code >}
     *     {@link JAXBElement }{@code <}{@link BooleanTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Budget }{@code >}
     *     {@link JAXBElement }{@code <}{@link ChargeCredit }{@code >}
     *     {@link JAXBElement }{@code <}{@link Class }{@code >}
     *     {@link JAXBElement }{@code <}{@link Company }{@code >}
     *     {@link JAXBElement }{@code <}{@link CompanyCurrency }{@code >}
     *     {@link JAXBElement }{@code <}{@link CompanyInfo }{@code >}
     *     {@link JAXBElement }{@code <}{@link CreditCardPaymentTxn }{@code >}
     *     {@link JAXBElement }{@code <}{@link CreditMemo }{@code >}
     *     {@link JAXBElement }{@code <}{@link CustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Customer }{@code >}
     *     {@link JAXBElement }{@code <}{@link CustomerType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DateTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Department }{@code >}
     *     {@link JAXBElement }{@code <}{@link Deposit }{@code >}
     *     {@link JAXBElement }{@code <}{@link EmailDeliveryInfo }{@code >}
     *     {@link JAXBElement }{@code <}{@link Employee }{@code >}
     *     {@link JAXBElement }{@code <}{@link Estimate }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExchangeRate }{@code >}
     *     {@link JAXBElement }{@code <}{@link FixedAsset }{@code >}
     *     {@link JAXBElement }{@code <}{@link IntuitEntity }{@code >}
     *     {@link JAXBElement }{@code <}{@link InventoryAdjustment }{@code >}
     *     {@link JAXBElement }{@code <}{@link InventorySite }{@code >}
     *     {@link JAXBElement }{@code <}{@link Invoice }{@code >}
     *     {@link JAXBElement }{@code <}{@link Item }{@code >}
     *     {@link JAXBElement }{@code <}{@link JournalCode }{@code >}
     *     {@link JAXBElement }{@code <}{@link JournalEntry }{@code >}
     *     {@link JAXBElement }{@code <}{@link MasterAccount }{@code >}
     *     {@link JAXBElement }{@code <}{@link NumberTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link OtherName }{@code >}
     *     {@link JAXBElement }{@code <}{@link Payment }{@code >}
     *     {@link JAXBElement }{@code <}{@link PaymentMethod }{@code >}
     *     {@link JAXBElement }{@code <}{@link Preferences }{@code >}
     *     {@link JAXBElement }{@code <}{@link PriceLevel }{@code >}
     *     {@link JAXBElement }{@code <}{@link Purchase }{@code >}
     *     {@link JAXBElement }{@code <}{@link PurchaseOrder }{@code >}
     *     {@link JAXBElement }{@code <}{@link QbdtEntityIdMapping }{@code >}
     *     {@link JAXBElement }{@code <}{@link RecurringTransaction }{@code >}
     *     {@link JAXBElement }{@code <}{@link RefundReceipt }{@code >}
     *     {@link JAXBElement }{@code <}{@link ReimburseCharge }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesOrder }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesReceipt }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesRep }{@code >}
     *     {@link JAXBElement }{@code <}{@link StatementCharge }{@code >}
     *     {@link JAXBElement }{@code <}{@link Status }{@code >}
     *     {@link JAXBElement }{@code <}{@link StringTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link SyncActivity }{@code >}
     *     {@link JAXBElement }{@code <}{@link TDSMetadata }{@code >}
     *     {@link JAXBElement }{@code <}{@link Tag }{@code >}
     *     {@link JAXBElement }{@code <}{@link Task }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxAgency }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxClassification }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxCode }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxPayment }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxRate }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxReturn }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxService }{@code >}
     *     {@link JAXBElement }{@code <}{@link Term }{@code >}
     *     {@link JAXBElement }{@code <}{@link TimeActivity }{@code >}
     *     {@link JAXBElement }{@code <}{@link Transfer }{@code >}
     *     {@link JAXBElement }{@code <}{@link UserAlert }{@code >}
     *     {@link JAXBElement }{@code <}{@link Vendor }{@code >}
     *     {@link JAXBElement }{@code <}{@link VendorCredit }{@code >}
     *     {@link JAXBElement }{@code <}{@link VendorType }{@code >}
     *     
     */
    public JAXBElement<? extends IntuitEntity> getIntuitObject() {
        return intuitObject;
    }

    /**
     * Sets the value of the intuitObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Account }{@code >}
     *     {@link JAXBElement }{@code <}{@link Attachable }{@code >}
     *     {@link JAXBElement }{@code <}{@link Bill }{@code >}
     *     {@link JAXBElement }{@code <}{@link BillPayment }{@code >}
     *     {@link JAXBElement }{@code <}{@link BooleanTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Budget }{@code >}
     *     {@link JAXBElement }{@code <}{@link ChargeCredit }{@code >}
     *     {@link JAXBElement }{@code <}{@link Class }{@code >}
     *     {@link JAXBElement }{@code <}{@link Company }{@code >}
     *     {@link JAXBElement }{@code <}{@link CompanyCurrency }{@code >}
     *     {@link JAXBElement }{@code <}{@link CompanyInfo }{@code >}
     *     {@link JAXBElement }{@code <}{@link CreditCardPaymentTxn }{@code >}
     *     {@link JAXBElement }{@code <}{@link CreditMemo }{@code >}
     *     {@link JAXBElement }{@code <}{@link CustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Customer }{@code >}
     *     {@link JAXBElement }{@code <}{@link CustomerType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DateTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link Department }{@code >}
     *     {@link JAXBElement }{@code <}{@link Deposit }{@code >}
     *     {@link JAXBElement }{@code <}{@link EmailDeliveryInfo }{@code >}
     *     {@link JAXBElement }{@code <}{@link Employee }{@code >}
     *     {@link JAXBElement }{@code <}{@link Estimate }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExchangeRate }{@code >}
     *     {@link JAXBElement }{@code <}{@link FixedAsset }{@code >}
     *     {@link JAXBElement }{@code <}{@link IntuitEntity }{@code >}
     *     {@link JAXBElement }{@code <}{@link InventoryAdjustment }{@code >}
     *     {@link JAXBElement }{@code <}{@link InventorySite }{@code >}
     *     {@link JAXBElement }{@code <}{@link Invoice }{@code >}
     *     {@link JAXBElement }{@code <}{@link Item }{@code >}
     *     {@link JAXBElement }{@code <}{@link JournalCode }{@code >}
     *     {@link JAXBElement }{@code <}{@link JournalEntry }{@code >}
     *     {@link JAXBElement }{@code <}{@link MasterAccount }{@code >}
     *     {@link JAXBElement }{@code <}{@link NumberTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link OtherName }{@code >}
     *     {@link JAXBElement }{@code <}{@link Payment }{@code >}
     *     {@link JAXBElement }{@code <}{@link PaymentMethod }{@code >}
     *     {@link JAXBElement }{@code <}{@link Preferences }{@code >}
     *     {@link JAXBElement }{@code <}{@link PriceLevel }{@code >}
     *     {@link JAXBElement }{@code <}{@link Purchase }{@code >}
     *     {@link JAXBElement }{@code <}{@link PurchaseOrder }{@code >}
     *     {@link JAXBElement }{@code <}{@link QbdtEntityIdMapping }{@code >}
     *     {@link JAXBElement }{@code <}{@link RecurringTransaction }{@code >}
     *     {@link JAXBElement }{@code <}{@link RefundReceipt }{@code >}
     *     {@link JAXBElement }{@code <}{@link ReimburseCharge }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesOrder }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesReceipt }{@code >}
     *     {@link JAXBElement }{@code <}{@link SalesRep }{@code >}
     *     {@link JAXBElement }{@code <}{@link StatementCharge }{@code >}
     *     {@link JAXBElement }{@code <}{@link Status }{@code >}
     *     {@link JAXBElement }{@code <}{@link StringTypeCustomFieldDefinition }{@code >}
     *     {@link JAXBElement }{@code <}{@link SyncActivity }{@code >}
     *     {@link JAXBElement }{@code <}{@link TDSMetadata }{@code >}
     *     {@link JAXBElement }{@code <}{@link Tag }{@code >}
     *     {@link JAXBElement }{@code <}{@link Task }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxAgency }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxClassification }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxCode }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxPayment }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxRate }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxReturn }{@code >}
     *     {@link JAXBElement }{@code <}{@link TaxService }{@code >}
     *     {@link JAXBElement }{@code <}{@link Term }{@code >}
     *     {@link JAXBElement }{@code <}{@link TimeActivity }{@code >}
     *     {@link JAXBElement }{@code <}{@link Transfer }{@code >}
     *     {@link JAXBElement }{@code <}{@link UserAlert }{@code >}
     *     {@link JAXBElement }{@code <}{@link Vendor }{@code >}
     *     {@link JAXBElement }{@code <}{@link VendorCredit }{@code >}
     *     {@link JAXBElement }{@code <}{@link VendorType }{@code >}
     *     
     */
    public void setIntuitObject(JAXBElement<? extends IntuitEntity> value) {
        this.intuitObject = value;
    }

    /**
     * Gets the value of the fault property.
     * 
     * @return
     *     possible object is
     *     {@link Fault }
     *     
     */
    public Fault getFault() {
        return fault;
    }

    /**
     * Sets the value of the fault property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fault }
     *     
     */
    public void setFault(Fault value) {
        this.fault = value;
    }

    /**
     * Gets the value of the report property.
     * 
     * @return
     *     possible object is
     *     {@link Report }
     *     
     */
    public Report getReport() {
        return report;
    }

    /**
     * Sets the value of the report property.
     * 
     * @param value
     *     allowed object is
     *     {@link Report }
     *     
     */
    public void setReport(Report value) {
        this.report = value;
    }

    /**
     * Gets the value of the queryResponse property.
     * 
     * @return
     *     possible object is
     *     {@link QueryResponse }
     *     
     */
    public QueryResponse getQueryResponse() {
        return queryResponse;
    }

    /**
     * Sets the value of the queryResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryResponse }
     *     
     */
    public void setQueryResponse(QueryResponse value) {
        this.queryResponse = value;
    }

    /**
     * Gets the value of the batchItemResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the batchItemResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBatchItemResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BatchItemResponse }
     * 
     * 
     */
    public List<BatchItemResponse> getBatchItemResponse() {
        if (batchItemResponse == null) {
            batchItemResponse = new ArrayList<BatchItemResponse>();
        }
        return this.batchItemResponse;
    }

    /**
     * Gets the value of the cdcResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the cdcResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCDCResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CDCResponse }
     * 
     * 
     */
    public List<CDCResponse> getCDCResponse() {
        if (cdcResponse == null) {
            cdcResponse = new ArrayList<CDCResponse>();
        }
        return this.cdcResponse;
    }

    /**
     * Gets the value of the attachableResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the attachableResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachableResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachableResponse }
     * 
     * 
     */
    public List<AttachableResponse> getAttachableResponse() {
        if (attachableResponse == null) {
            attachableResponse = new ArrayList<AttachableResponse>();
        }
        return this.attachableResponse;
    }

    /**
     * Gets the value of the syncErrorResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SyncErrorResponse }
     *     
     */
    public SyncErrorResponse getSyncErrorResponse() {
        return syncErrorResponse;
    }

    /**
     * Sets the value of the syncErrorResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyncErrorResponse }
     *     
     */
    public void setSyncErrorResponse(SyncErrorResponse value) {
        this.syncErrorResponse = value;
    }

    /**
     * Gets the value of the olbTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link OLBTransaction }
     *     
     */
    public OLBTransaction getOLBTransaction() {
        return olbTransaction;
    }

    /**
     * Sets the value of the olbTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link OLBTransaction }
     *     
     */
    public void setOLBTransaction(OLBTransaction value) {
        this.olbTransaction = value;
    }

    /**
     * Gets the value of the olbStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OLBStatus }
     *     
     */
    public OLBStatus getOLBStatus() {
        return olbStatus;
    }

    /**
     * Sets the value of the olbStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OLBStatus }
     *     
     */
    public void setOLBStatus(OLBStatus value) {
        this.olbStatus = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(Date value) {
        this.time = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    public void setAttachableResponse(List<AttachableResponse> attachableResponse) {
        this.attachableResponse = attachableResponse;
    }

    public void setBatchItemResponse(List<BatchItemResponse> batchItemResponse) {
        this.batchItemResponse = batchItemResponse;
    }

    /**
     * Sets the value of the cdcResponse property.
     *
     * @param cdcResponse
     *     allowed object is
     *     {@link CDCResponse }
     *
     */
    public void setCDCResponse(List<CDCResponse> cdcResponse) {
        this.cdcResponse = cdcResponse;
    }

}
