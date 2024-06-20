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
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				Product: ALL
 * 				Description: Information about a
 * 				payment received by credit card.
 * 			
 * 
 * <p>Java class for CreditCardPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardPayment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CreditChargeInfo" type="{http://schema.intuit.com/finance/v3}CreditChargeInfo" minOccurs="0"/&gt;
 *         &lt;element name="CreditChargeResponse" type="{http://schema.intuit.com/finance/v3}CreditChargeResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardPayment", propOrder = {
    "creditChargeInfo",
    "creditChargeResponse"
})
public class CreditCardPayment
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CreditChargeInfo")
    protected CreditChargeInfo creditChargeInfo;
    @XmlElement(name = "CreditChargeResponse")
    protected CreditChargeResponse creditChargeResponse;

    /**
     * Gets the value of the creditChargeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreditChargeInfo }
     *     
     */
    public CreditChargeInfo getCreditChargeInfo() {
        return creditChargeInfo;
    }

    /**
     * Sets the value of the creditChargeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditChargeInfo }
     *     
     */
    public void setCreditChargeInfo(CreditChargeInfo value) {
        this.creditChargeInfo = value;
    }

    /**
     * Gets the value of the creditChargeResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CreditChargeResponse }
     *     
     */
    public CreditChargeResponse getCreditChargeResponse() {
        return creditChargeResponse;
    }

    /**
     * Sets the value of the creditChargeResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditChargeResponse }
     *     
     */
    public void setCreditChargeResponse(CreditChargeResponse value) {
        this.creditChargeResponse = value;
    }

}
