//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				Product: QBO
 * 				Description: Specifies various fields
 * 				required for emailing different transaction
 * 			
 * 
 * <p>Java class for EmailDeliveryInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmailDeliveryInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schema.intuit.com/finance/v3}IntuitEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DeliveryAddress" type="{http://schema.intuit.com/finance/v3}EmailAddress" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryAddressCc" type="{http://schema.intuit.com/finance/v3}EmailAddress" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryAddressBcc" type="{http://schema.intuit.com/finance/v3}EmailAddress" minOccurs="0"/&gt;
 *         &lt;element name="EmailMessage" type="{http://schema.intuit.com/finance/v3}EmailMessage" minOccurs="0"/&gt;
 *         &lt;element name="AllowOnlinePayment" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="AllowOnlineCreditCardPayment" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="AllowOnlineACHPayment" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryInfo" type="{http://schema.intuit.com/finance/v3}TransactionDeliveryInfo" minOccurs="0"/&gt;
 *         &lt;element name="ETransactionStatus" type="{http://schema.intuit.com/finance/v3}ETransactionStatusEnum" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailDeliveryInfo", propOrder = {
    "deliveryAddress",
    "deliveryAddressCc",
    "deliveryAddressBcc",
    "emailMessage",
    "allowOnlinePayment",
    "allowOnlineCreditCardPayment",
    "allowOnlineACHPayment",
    "deliveryInfo",
    "eTransactionStatus"
})
public class EmailDeliveryInfo
    extends IntuitEntity
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DeliveryAddress")
    protected EmailAddress deliveryAddress;
    @XmlElement(name = "DeliveryAddressCc")
    protected EmailAddress deliveryAddressCc;
    @XmlElement(name = "DeliveryAddressBcc")
    protected EmailAddress deliveryAddressBcc;
    @XmlElement(name = "EmailMessage")
    protected EmailMessage emailMessage;
    @XmlElement(name = "AllowOnlinePayment")
    protected Boolean allowOnlinePayment;
    @XmlElement(name = "AllowOnlineCreditCardPayment")
    protected Boolean allowOnlineCreditCardPayment;
    @XmlElement(name = "AllowOnlineACHPayment")
    protected Boolean allowOnlineACHPayment;
    @XmlElement(name = "DeliveryInfo")
    protected TransactionDeliveryInfo deliveryInfo;
    @XmlElement(name = "ETransactionStatus")
    @XmlSchemaType(name = "string")
    protected ETransactionStatusEnum eTransactionStatus;

    /**
     * Gets the value of the deliveryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the value of the deliveryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setDeliveryAddress(EmailAddress value) {
        this.deliveryAddress = value;
    }

    /**
     * Gets the value of the deliveryAddressCc property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddress getDeliveryAddressCc() {
        return deliveryAddressCc;
    }

    /**
     * Sets the value of the deliveryAddressCc property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setDeliveryAddressCc(EmailAddress value) {
        this.deliveryAddressCc = value;
    }

    /**
     * Gets the value of the deliveryAddressBcc property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddress getDeliveryAddressBcc() {
        return deliveryAddressBcc;
    }

    /**
     * Sets the value of the deliveryAddressBcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setDeliveryAddressBcc(EmailAddress value) {
        this.deliveryAddressBcc = value;
    }

    /**
     * Gets the value of the emailMessage property.
     * 
     * @return
     *     possible object is
     *     {@link EmailMessage }
     *     
     */
    public EmailMessage getEmailMessage() {
        return emailMessage;
    }

    /**
     * Sets the value of the emailMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailMessage }
     *     
     */
    public void setEmailMessage(EmailMessage value) {
        this.emailMessage = value;
    }

    /**
     * Gets the value of the allowOnlinePayment property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOnlinePayment() {
        return allowOnlinePayment;
    }

    /**
     * Sets the value of the allowOnlinePayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOnlinePayment(Boolean value) {
        this.allowOnlinePayment = value;
    }

    /**
     * Gets the value of the allowOnlineCreditCardPayment property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOnlineCreditCardPayment() {
        return allowOnlineCreditCardPayment;
    }

    /**
     * Sets the value of the allowOnlineCreditCardPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOnlineCreditCardPayment(Boolean value) {
        this.allowOnlineCreditCardPayment = value;
    }

    /**
     * Gets the value of the allowOnlineACHPayment property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOnlineACHPayment() {
        return allowOnlineACHPayment;
    }

    /**
     * Sets the value of the allowOnlineACHPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOnlineACHPayment(Boolean value) {
        this.allowOnlineACHPayment = value;
    }

    /**
     * Gets the value of the deliveryInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionDeliveryInfo }
     *     
     */
    public TransactionDeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    /**
     * Sets the value of the deliveryInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionDeliveryInfo }
     *     
     */
    public void setDeliveryInfo(TransactionDeliveryInfo value) {
        this.deliveryInfo = value;
    }

    /**
     * Gets the value of the eTransactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ETransactionStatusEnum }
     *     
     */
    public ETransactionStatusEnum getETransactionStatus() {
        return eTransactionStatus;
    }

    /**
     * Sets the value of the eTransactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ETransactionStatusEnum }
     *     
     */
    public void setETransactionStatus(ETransactionStatusEnum value) {
        this.eTransactionStatus = value;
    }

}
