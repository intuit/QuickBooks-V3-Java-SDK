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
package com.intuit.oauth2.http;

/**
 * Enum mapping the supported HTTP Method types 
 * 
 * @author dderose
 *
 */
public enum MethodType {
	
	GET("GET"),
    POST("POST");

    private final String value;

    MethodType(final String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }
    
    public static MethodType fromValue(String value) {
        for (MethodType method: MethodType.values()) {
            if (method.value.equals(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
