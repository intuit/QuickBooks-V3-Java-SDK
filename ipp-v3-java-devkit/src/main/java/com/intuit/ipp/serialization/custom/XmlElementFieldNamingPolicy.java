package com.intuit.ipp.serialization.custom;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlElement;

import com.google.gson.FieldNamingStrategy;

public class XmlElementFieldNamingPolicy implements FieldNamingStrategy {
    /**
     * @param   f       field
     * @return name of XmlElement annotation if field has XmlElement annotation
     */
    @Override
    public String translateName(Field f) {
        XmlElement v_annotation = f.getAnnotation(XmlElement.class);
        return v_annotation != null ? v_annotation.name() : f.getName();
    }
}
