//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import java.io.Serializable;
import java.util.Date;
import com.intuit.sb.cdm.util.v3.DateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				Product: ALL
 * 				Description: Information about
 * 				Description.
 * 			
 * 
 * <p>Java class for DescriptionLineDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescriptionLineDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="TaxCodeRef" type="{http://schema.intuit.com/finance/v3}ReferenceType" minOccurs="0"/&gt;
 *         &lt;element name="DescriptionLineDetailEx" type="{http://schema.intuit.com/finance/v3}IntuitAnyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescriptionLineDetail", propOrder = {
    "serviceDate",
    "taxCodeRef",
    "descriptionLineDetailEx"
})
public class DescriptionLineDetail
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ServiceDate", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date serviceDate;
    @XmlElement(name = "TaxCodeRef")
    protected ReferenceType taxCodeRef;
    @XmlElement(name = "DescriptionLineDetailEx")
    protected IntuitAnyType descriptionLineDetailEx;

    /**
     * Gets the value of the serviceDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getServiceDate() {
        return serviceDate;
    }

    /**
     * Sets the value of the serviceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceDate(Date value) {
        this.serviceDate = value;
    }

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
     * Gets the value of the descriptionLineDetailEx property.
     * 
     * @return
     *     possible object is
     *     {@link IntuitAnyType }
     *     
     */
    public IntuitAnyType getDescriptionLineDetailEx() {
        return descriptionLineDetailEx;
    }

    /**
     * Sets the value of the descriptionLineDetailEx property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntuitAnyType }
     *     
     */
    public void setDescriptionLineDetailEx(IntuitAnyType value) {
        this.descriptionLineDetailEx = value;
    }

}
