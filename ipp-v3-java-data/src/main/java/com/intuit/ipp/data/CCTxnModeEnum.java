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
 * <p>Java class for CCTxnModeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="CCTxnModeEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CardNotPresent"/&gt;
 *     &lt;enumeration value="CardPresent"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CCTxnModeEnum")
@XmlEnum
public enum CCTxnModeEnum {

    @XmlEnumValue("CardNotPresent")
    CARD_NOT_PRESENT("CardNotPresent"),
    @XmlEnumValue("CardPresent")
    CARD_PRESENT("CardPresent");
    private final String value;

    CCTxnModeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CCTxnModeEnum fromValue(String v) {
        for (CCTxnModeEnum c: CCTxnModeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
