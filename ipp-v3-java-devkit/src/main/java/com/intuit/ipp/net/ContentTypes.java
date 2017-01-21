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
