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
