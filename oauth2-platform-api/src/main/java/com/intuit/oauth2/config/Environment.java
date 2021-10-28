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
package com.intuit.oauth2.config;

/**
 * Enum mapping the environments for DiscoverAPI URL
 * 
 * @author dderose
 *
 */
public enum Environment {
	
	PRODUCTION("PRODUCTION"),
	SANDBOX("SANDBOX"),
    E2E("E2E");
	
	private final String value;

	Environment(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
    
    public static Environment fromValue(String value) {
        for (Environment env: Environment.values()) {
            if (env.value.equals(value)) {
                return env;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
