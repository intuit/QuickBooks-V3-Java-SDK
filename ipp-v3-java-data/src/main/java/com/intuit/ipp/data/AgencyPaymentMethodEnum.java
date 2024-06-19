//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgencyPaymentMethodEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="AgencyPaymentMethodEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ACH_CREDIT"/&gt;
 *     &lt;enumeration value="ACH_DEBIT"/&gt;
 *     &lt;enumeration value="CHECK"/&gt;
 *     &lt;enumeration value="WIRE"/&gt;
 *     &lt;enumeration value="OTHER"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AgencyPaymentMethodEnum")
@XmlEnum
public enum AgencyPaymentMethodEnum {

    ACH_CREDIT,
    ACH_DEBIT,
    CHECK,
    WIRE,
    OTHER;

    public String value() {
        return name();
    }

    public static AgencyPaymentMethodEnum fromValue(String v) {
        return valueOf(v);
    }

}
