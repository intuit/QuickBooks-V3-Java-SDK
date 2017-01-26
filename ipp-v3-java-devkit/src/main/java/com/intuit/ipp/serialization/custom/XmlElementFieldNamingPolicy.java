/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
