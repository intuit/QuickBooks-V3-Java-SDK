//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import java.util.Date;

import com.intuit.ipp.core.IEntity;
import com.intuit.sb.cdm.util.v3.DateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * PurchaseOrder is a non-posting transaction
 * 				representing a request to purchase goods or services from a third
 * 				party.
 * 
 * <p>Java class for PurchaseOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schema.intuit.com/finance/v3}PurchaseByVendor"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxCodeRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="ClassRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="ReimbursableInfoRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="SalesTermRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="ExpectedDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="VendorAddr" type="{http://schema.intuit.com/finance/v3}PhysicalAddress" minOccurs="0"/&gt;
 *         &lt;element name="ShipTo" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="DropShipToEntity" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *           &lt;element name="InventorySiteRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="ShipAddr" type="{http://schema.intuit.com/finance/v3}PhysicalAddress" minOccurs="0"/&gt;
 *         &lt;element name="ShipMethodRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="FOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="POEmail" type="{http://schema.intuit.com/finance/v3}EmailAddress" minOccurs="0"/&gt;
 *         &lt;element name="TemplateRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="PrintStatus" type="{http://schema.intuit.com/finance/v3}PrintStatusEnum" minOccurs="0"/&gt;
 *         &lt;element name="EmailStatus" type="{http://schema.intuit.com/finance/v3}EmailStatusEnum" minOccurs="0"/&gt;
 *         &lt;element name="ManuallyClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="POStatus" type="{http://schema.intuit.com/finance/v3}PurchaseOrderStatusEnum" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderEx" type="{http://schema.intuit.com/finance/v3}IntuitAnyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrder", propOrder = {
    "taxCodeRef",
    "classRef",
    "reimbursableInfoRef",
    "salesTermRef",
    "dueDate",
    "expectedDate",
    "vendorAddr",
    "shipTo",
    "dropShipToEntity",
    "inventorySiteRef",
    "shipAddr",
    "shipMethodRef",
    "fob",
    "poEmail",
    "templateRef",
    "printStatus",
    "emailStatus",
    "manuallyClosed",
    "poStatus",
    "purchaseOrderEx"
})
public class PurchaseOrder
    extends PurchaseByVendor
    implements Serializable, IEntity
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TaxCodeRef")
    protected ReferenceType taxCodeRef;
    @XmlElement(name = "ClassRef")
    protected ReferenceType classRef;
    @XmlElement(name = "ReimbursableInfoRef")
    protected ReferenceType reimbursableInfoRef;
    @XmlElement(name = "SalesTermRef")
    protected ReferenceType salesTermRef;
    @XmlElement(name = "DueDate", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dueDate;
    @XmlElement(name = "ExpectedDate", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date expectedDate;
    @XmlElement(name = "VendorAddr")
    protected PhysicalAddress vendorAddr;
    @XmlElement(name = "ShipTo")
    protected ReferenceType shipTo;
    @XmlElement(name = "DropShipToEntity")
    protected ReferenceType dropShipToEntity;
    @XmlElement(name = "InventorySiteRef")
    protected ReferenceType inventorySiteRef;
    @XmlElement(name = "ShipAddr")
    protected PhysicalAddress shipAddr;
    @XmlElement(name = "ShipMethodRef")
    protected ReferenceType shipMethodRef;
    @XmlElement(name = "FOB")
    protected String fob;
    @XmlElement(name = "POEmail")
    protected EmailAddress poEmail;
    @XmlElement(name = "TemplateRef")
    protected ReferenceType templateRef;
    @XmlElement(name = "PrintStatus")
    @XmlSchemaType(name = "string")
    protected PrintStatusEnum printStatus;
    @XmlElement(name = "EmailStatus")
    @XmlSchemaType(name = "string")
    protected EmailStatusEnum emailStatus;
    @XmlElement(name = "ManuallyClosed")
    protected Boolean manuallyClosed;
    @XmlElement(name = "POStatus")
    @XmlSchemaType(name = "string")
    protected PurchaseOrderStatusEnum poStatus;
    @XmlElement(name = "PurchaseOrderEx")
    protected IntuitAnyType purchaseOrderEx;

    /**
     * Gets the value of the taxCodeRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getTaxCodeRef() {
        return taxCodeRef;
    }

    /**
     * Sets the value of the taxCodeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setTaxCodeRef(ReferenceType value) {
        this.taxCodeRef = value;
    }

    /**
     * Gets the value of the classRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getClassRef() {
        return classRef;
    }

    /**
     * Sets the value of the classRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setClassRef(ReferenceType value) {
        this.classRef = value;
    }

    /**
     * Gets the value of the reimbursableInfoRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getReimbursableInfoRef() {
        return reimbursableInfoRef;
    }

    /**
     * Sets the value of the reimbursableInfoRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setReimbursableInfoRef(ReferenceType value) {
        this.reimbursableInfoRef = value;
    }

    /**
     * Gets the value of the salesTermRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getSalesTermRef() {
        return salesTermRef;
    }

    /**
     * Sets the value of the salesTermRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setSalesTermRef(ReferenceType value) {
        this.salesTermRef = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDate(Date value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the expectedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getExpectedDate() {
        return expectedDate;
    }

    /**
     * Sets the value of the expectedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedDate(Date value) {
        this.expectedDate = value;
    }

    /**
     * Gets the value of the vendorAddr property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalAddress }
     *     
     */
    public PhysicalAddress getVendorAddr() {
        return vendorAddr;
    }

    /**
     * Sets the value of the vendorAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalAddress }
     *     
     */
    public void setVendorAddr(PhysicalAddress value) {
        this.vendorAddr = value;
    }

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setShipTo(ReferenceType value) {
        this.shipTo = value;
    }

    /**
     * Gets the value of the dropShipToEntity property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getDropShipToEntity() {
        return dropShipToEntity;
    }

    /**
     * Sets the value of the dropShipToEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setDropShipToEntity(ReferenceType value) {
        this.dropShipToEntity = value;
    }

    /**
     * Gets the value of the inventorySiteRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getInventorySiteRef() {
        return inventorySiteRef;
    }

    /**
     * Sets the value of the inventorySiteRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setInventorySiteRef(ReferenceType value) {
        this.inventorySiteRef = value;
    }

    /**
     * Gets the value of the shipAddr property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalAddress }
     *     
     */
    public PhysicalAddress getShipAddr() {
        return shipAddr;
    }

    /**
     * Sets the value of the shipAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalAddress }
     *     
     */
    public void setShipAddr(PhysicalAddress value) {
        this.shipAddr = value;
    }

    /**
     * Gets the value of the shipMethodRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getShipMethodRef() {
        return shipMethodRef;
    }

    /**
     * Sets the value of the shipMethodRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setShipMethodRef(ReferenceType value) {
        this.shipMethodRef = value;
    }

    /**
     * Gets the value of the fob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFOB() {
        return fob;
    }

    /**
     * Sets the value of the fob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFOB(String value) {
        this.fob = value;
    }

    /**
     * Gets the value of the poEmail property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddress getPOEmail() {
        return poEmail;
    }

    /**
     * Sets the value of the poEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setPOEmail(EmailAddress value) {
        this.poEmail = value;
    }

    /**
     * Gets the value of the templateRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getTemplateRef() {
        return templateRef;
    }

    /**
     * Sets the value of the templateRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setTemplateRef(ReferenceType value) {
        this.templateRef = value;
    }

    /**
     * Gets the value of the printStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PrintStatusEnum }
     *     
     */
    public PrintStatusEnum getPrintStatus() {
        return printStatus;
    }

    /**
     * Sets the value of the printStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintStatusEnum }
     *     
     */
    public void setPrintStatus(PrintStatusEnum value) {
        this.printStatus = value;
    }

    /**
     * Gets the value of the emailStatus property.
     * 
     * @return
     *     possible object is
     *     {@link EmailStatusEnum }
     *     
     */
    public EmailStatusEnum getEmailStatus() {
        return emailStatus;
    }

    /**
     * Sets the value of the emailStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailStatusEnum }
     *     
     */
    public void setEmailStatus(EmailStatusEnum value) {
        this.emailStatus = value;
    }

    /**
     * Gets the value of the manuallyClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isManuallyClosed() {
        return manuallyClosed;
    }

    /**
     * Sets the value of the manuallyClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setManuallyClosed(Boolean value) {
        this.manuallyClosed = value;
    }

    /**
     * Gets the value of the poStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderStatusEnum }
     *     
     */
    public PurchaseOrderStatusEnum getPOStatus() {
        return poStatus;
    }

    /**
     * Sets the value of the poStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderStatusEnum }
     *     
     */
    public void setPOStatus(PurchaseOrderStatusEnum value) {
        this.poStatus = value;
    }

    /**
     * Gets the value of the purchaseOrderEx property.
     * 
     * @return
     *     possible object is
     *     {@link IntuitAnyType }
     *     
     */
    public IntuitAnyType getPurchaseOrderEx() {
        return purchaseOrderEx;
    }

    /**
     * Sets the value of the purchaseOrderEx property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntuitAnyType }
     *     
     */
    public void setPurchaseOrderEx(IntuitAnyType value) {
        this.purchaseOrderEx = value;
    }

}
