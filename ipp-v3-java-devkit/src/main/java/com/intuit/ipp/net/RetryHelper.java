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
package com.intuit.ipp.net;

import com.intuit.ipp.exception.ConfigurationException;

/**
 * Class to help the retry mechanism
 *
 */
public final class RetryHelper {

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private RetryHelper() {
	}
	
	/**
	 * Checks whether the given argument value is -ve or not
	 * 
	 * @param argumentValue the value
	 * @param argumentName the name
	 * @throws NegativeValueException
	 */
	public static void argumentNotNegativeValue(long argumentValue, String argumentName) throws ConfigurationException {
		if (argumentValue < 0) {
			throw new ConfigurationException("The value for argument name [" + argumentName + "] is negative [" 
					+ argumentValue + "]. Please check the configuration.");
		}
	}
}
