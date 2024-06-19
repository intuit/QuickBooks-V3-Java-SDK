//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.intuit.ipp.core.IEntity;
import com.intuit.sb.cdm.util.v3.DateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Bill is an AP transaction representing a
 * 				request-for-payment from a third party for goods/services rendered
 * 				and/or received
 * 
 * <p>Java class for Bill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bill"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schema.intuit.com/finance/v3}PurchaseByVendor"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PayerRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="SalesTermRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="RemitToAddr" type="{http://schema.intuit.com/finance/v3}PhysicalAddress" minOccurs="0"/&gt;
 *         &lt;element name="ShipAddr" type="{http://schema.intuit.com/finance/v3}PhysicalAddress" minOccurs="0"/&gt;
 *         &lt;element name="VendorAddr" type="{http://schema.intuit.com/finance/v3}PhysicalAddress" minOccurs="0"/&gt;
 *         &lt;element name="Balance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="HomeBalance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="BillEx" type="{http://schema.intuit.com/finance/v3}IntuitAnyType" minOccurs="0"/&gt;
 *         &lt;element name="LessCIS" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="IncludeInAnnualTPAR" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bill", propOrder = {
    "payerRef",
    "salesTermRef",
    "dueDate",
    "remitToAddr",
    "shipAddr",
    "vendorAddr",
    "balance",
    "homeBalance",
    "billEx",
    "lessCIS",
    "includeInAnnualTPAR"
})
public class Bill
    extends PurchaseByVendor
    implements Serializable, IEntity
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PayerRef")
    protected ReferenceType payerRef;
    @XmlElement(name = "SalesTermRef")
    protected ReferenceType salesTermRef;
    @XmlElement(name = "DueDate", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dueDate;
    @XmlElement(name = "RemitToAddr")
    protected PhysicalAddress remitToAddr;
    @XmlElement(name = "ShipAddr")
    protected PhysicalAddress shipAddr;
    @XmlElement(name = "VendorAddr")
    protected PhysicalAddress vendorAddr;
    @XmlElement(name = "Balance")
    protected BigDecimal balance;
    @XmlElement(name = "HomeBalance")
    protected BigDecimal homeBalance;
    @XmlElement(name = "BillEx")
    protected IntuitAnyType billEx;
    @XmlElement(name = "LessCIS")
    protected BigDecimal lessCIS;
    @XmlElement(name = "IncludeInAnnualTPAR")
    protected Boolean includeInAnnualTPAR;

    /**
     * Gets the value of the payerRef property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceType }
     *     
     */
    public ReferenceType getPayerRef() {
        return payerRef;
    }

    /**
     * Sets the value of the payerRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceType }
     *     
     */
    public void setPayerRef(ReferenceType value) {
        this.payerRef = value;
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
     * Gets the value of the remitToAddr property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalAddress }
     *     
     */
    public PhysicalAddress getRemitToAddr() {
        return remitToAddr;
    }

    /**
     * Sets the value of the remitToAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalAddress }
     *     
     */
    public void setRemitToAddr(PhysicalAddress value) {
        this.remitToAddr = value;
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
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }

    /**
     * Gets the value of the homeBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHomeBalance() {
        return homeBalance;
    }

    /**
     * Sets the value of the homeBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHomeBalance(BigDecimal value) {
        this.homeBalance = value;
    }

    /**
     * Gets the value of the billEx property.
     * 
     * @return
     *     possible object is
     *     {@link IntuitAnyType }
     *     
     */
    public IntuitAnyType getBillEx() {
        return billEx;
    }

    /**
     * Sets the value of the billEx property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntuitAnyType }
     *     
     */
    public void setBillEx(IntuitAnyType value) {
        this.billEx = value;
    }

    /**
     * Gets the value of the lessCIS property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLessCIS() {
        return lessCIS;
    }

    /**
     * Sets the value of the lessCIS property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLessCIS(BigDecimal value) {
        this.lessCIS = value;
    }

    /**
     * Gets the value of the includeInAnnualTPAR property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeInAnnualTPAR() {
        return includeInAnnualTPAR;
    }

    /**
     * Sets the value of the includeInAnnualTPAR property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInAnnualTPAR(Boolean value) {
        this.includeInAnnualTPAR = value;
    }

    public void setLine(List<Line> lines1) {
        this.line = lines1;
    }
}
