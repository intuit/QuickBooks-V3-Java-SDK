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
 * Enum mapping the differnt Scopes supported by OAuth2 framework
 * 
 * @author dderose
 *
 */
public enum Scope {
	
	Accounting("ACCOUNTING"), 
	Payments("PAYMENTS"),
	AccountingPayments("ACCOUNTING_PAYMENTS"), 	// accounting + payments
	OpenId("OPENID"), 				
	Profile("PROFILE"),				
	Phone("PHONE"),					
	Address("ADDRESS"),				
	Email("EMAIL"),					
	OpenIdAll("OPENID_ALL"), 					// openid profile phone address email
	All("ALL"),									// openid profile email phone address accounting payments
	Payroll("PAYROLL"),
	Timetracking("TIMETRACKING"),
	Benefits("BENEFITS"),
	IntuitName("INTUIT_NAME");
	
	private final String value;

	Scope(final String value) {
        this.value = value;
    }

	public String value() {
        return value;
    }
    
    public static Scope fromValue(String value) {
        for (Scope scope: Scope.values()) {
            if (scope.value.equals(value)) {
                return scope;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
