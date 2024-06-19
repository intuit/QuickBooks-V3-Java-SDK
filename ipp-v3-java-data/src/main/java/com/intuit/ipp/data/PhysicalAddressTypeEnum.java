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
 * <p>Java class for PhysicalAddressTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="PhysicalAddressTypeEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Billing"/&gt;
 *     &lt;enumeration value="Shipping"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PhysicalAddressTypeEnum")
@XmlEnum
public enum PhysicalAddressTypeEnum {

    @XmlEnumValue("Billing")
    BILLING("Billing"),
    @XmlEnumValue("Shipping")
    SHIPPING("Shipping");
    private final String value;

    PhysicalAddressTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PhysicalAddressTypeEnum fromValue(String v) {
        for (PhysicalAddressTypeEnum c: PhysicalAddressTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
