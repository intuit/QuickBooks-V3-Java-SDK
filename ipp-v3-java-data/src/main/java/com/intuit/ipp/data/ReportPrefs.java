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
 * Defines Report Prefs details 
 * 
 * <p>Java class for ReportPrefs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportPrefs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReportBasis" type="{http://schema.intuit.com/finance/v3}ReportBasisEnum" minOccurs="0"/&gt;
 *         &lt;element name="CalcAgingReportFromTxnDate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportPrefs", propOrder = {
    "reportBasis",
    "calcAgingReportFromTxnDate"
})
public class ReportPrefs
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ReportBasis")
    @XmlSchemaType(name = "string")
    protected ReportBasisEnum reportBasis;
    @XmlElement(name = "CalcAgingReportFromTxnDate")
    protected Boolean calcAgingReportFromTxnDate;

    /**
     * Gets the value of the reportBasis property.
     * 
     * @return
     *     possible object is
     *     {@link ReportBasisEnum }
     *     
     */
    public ReportBasisEnum getReportBasis() {
        return reportBasis;
    }

    /**
     * Sets the value of the reportBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportBasisEnum }
     *     
     */
    public void setReportBasis(ReportBasisEnum value) {
        this.reportBasis = value;
    }

    /**
     * Gets the value of the calcAgingReportFromTxnDate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCalcAgingReportFromTxnDate() {
        return calcAgingReportFromTxnDate;
    }

    /**
     * Sets the value of the calcAgingReportFromTxnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCalcAgingReportFromTxnDate(Boolean value) {
        this.calcAgingReportFromTxnDate = value;
    }

}
