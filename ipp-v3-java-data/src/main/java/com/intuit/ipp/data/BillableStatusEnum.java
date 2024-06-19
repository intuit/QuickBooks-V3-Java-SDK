//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.18 at 04:20:25 PM IST 
//


package com.intuit.ipp.data;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillableStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="BillableStatusEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Billable"/&gt;
 *     &lt;enumeration value="NotBillable"/&gt;
 *     &lt;enumeration value="HasBeenBilled"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BillableStatusEnum")
@XmlEnum
public enum BillableStatusEnum {

    @XmlEnumValue("Billable")
    BILLABLE("Billable"),
    @XmlEnumValue("NotBillable")
    NOT_BILLABLE("NotBillable"),
    @XmlEnumValue("HasBeenBilled")
    HAS_BEEN_BILLED("HasBeenBilled");
    private final String value;

    BillableStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillableStatusEnum fromValue(String v) {
        for (BillableStatusEnum c: BillableStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
