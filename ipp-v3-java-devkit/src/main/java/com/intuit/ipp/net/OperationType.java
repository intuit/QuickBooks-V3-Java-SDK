package com.intuit.ipp.net;

/**
 * Enum to have operation type
 *
 */
public enum OperationType {
	
	/**
	 * the ADD operation
	 */
	ADD("add"),
	
	/**
	 * the UPDATE operation
	 */
	UPDATE("update"),
	
	/**
	 * the UPDATEACCOUNTONTXNS operation
	 */
   UPDATEACCOUNTONTXNS("updateAccountOnTxns"),

	/**
	 * the DONOTUPDATEACCOUNTONTXNS operation
	 */
  DONOTUPDATEACCOUNTONTXNS("donotUpdateAccountOnTxns"),
	
	/**
	 * the DELETE operation
	 */
	DELETE("delete"),
	
	/**
	 * the VOID operation
	 */
	VOID("void"),
	
	/**
	 * the UPLOAD operation
	 */
	UPLOAD("upload"),
	
	/**
	 * the DOWNLOAD operation
	 */
	DOWNLOAD("download"),
	
	/**
	 * the QUERY operation
	 */
	QUERY("query"),
	
	/**
	 * the REPORTS operation
	 */
	REPORTS("reports"),
	
	/**
	 * the CDCQUERY operation
	 */
	CDCQUERY("cdc"),
	
	/**
	 * the BATCH operation
	 */
	BATCH("batch"),
	
	/**
	 * the REMOVECLOUD operation
	 */
	REMOVECLOUD("removeCloud"),
	
	/**
	 * the RETAINCLOUD operation
	 */
	RETAINCLOUD("retainCloud"),
	
	/**
	 * the SYNCERRORCOUNT operation
	 */
	SYNCERRORCOUNT("syncerrorcount"),
	
	/**
	 * the SYNCERRORS operation
	 */
	SYNCERRORS("syncerrors");
	
	/**
	 * variable type
	 */
	private String type;
	
	/**
	 * Constructor operation type
	 * 
	 * @param type the operation type
	 */
	private OperationType(String type) {
		this.type = type;
	}
	
	/**
	 * Method to get the string value of operation type
	 */
	@Override
	public String toString() {
		return type;
	}
}
