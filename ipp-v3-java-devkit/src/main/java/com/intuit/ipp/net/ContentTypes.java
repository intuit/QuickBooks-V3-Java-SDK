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

import com.intuit.ipp.util.StringUtils;

/**
 * Enum to have content types
 *
 */
public enum ContentTypes {
	
	/**
	 * content type XML
	 */
	XML("application/xml"),
	
	/**
	 * content type JSON
	 */
	JSON("application/json"),
	
	/**
	 * content type TEXT
	 */
	TEXT("application/text"),
	
	/**
	 * content type TEXT_PLAIN
	 */
	TEXT_PLAIN("text/plain"),
	
	/**
	 * content type MULTIPART_FORMDATA
	 */
	MULTIPART_FORMDATA("multipart/form-data"),
	
	OCTECT_STREAM("application/octet-stream"),


    /**
     * content type PDF
     *
     */
    PDF("application/pdf");
	
	/**
	 * variable type
	 */
	private String type = null;
	
	/**
	 * Constructor ContentTypes
	 * 
	 * @param type the content type
	 */
	private ContentTypes(String type) {
		this.type = type;
	}
	
	/**
	 * Method to get the string value of content type
	 * 
	 * @return the content type
	 */
	@Override
	public String toString() {
		return type;
	}
	
	/**
	 * Method to retrieve the corresponding content type for the given format
	 * 
	 * @param format the format
	 * @return the content type
	 */
	public static String getContentType(String format) {
		if (StringUtils.hasText(format)) {
			if (format.equalsIgnoreCase(XML.name())) {
				return ContentTypes.XML.toString();
			} else if (format.equalsIgnoreCase(JSON.name())) {
				return ContentTypes.JSON.toString();
			} else if (format.equalsIgnoreCase(PDF.name())) {
                return ContentTypes.PDF.toString();
            }
		}
		return null;
	}
}
