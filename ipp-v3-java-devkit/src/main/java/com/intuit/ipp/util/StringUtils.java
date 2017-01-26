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
package com.intuit.ipp.util;

/**
 * Util class to have common string operations
 * 
 * @author ilamparithi
 *
 */
public final class StringUtils {

	/**
	 * Constructor to have private modifier as it has only static methods
	 */
	private StringUtils() {
	}
	
	/**
	 * Method to get the string utils instance
	 * 
	 * @return StringUtils
	 */
	public static StringUtils getInstance() {
		return new StringUtils();
	}
	
	/**
	 * Method to validate whether the given string is null/empty or has value
	 * @param text
	 * @return boolean
	 */
	public static boolean hasText(final String text) {
		if (text != null && !"".equals(text.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method to validate whether the given byte array is null/empty or has value
	 * @param byte[] data
	 * @return boolean
	 */
	public static boolean hasBytes(final byte[] data) {
		if (data != null && data.length > 0) {
			return true;
		}
		return false;
	}
}
