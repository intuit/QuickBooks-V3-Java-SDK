/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ia.exception;
/**
 * This class represents the connection exception thrown by the jar
 */
public class IAException extends Exception {

	
	private static final long serialVersionUID = 1222211112L;
	
	private String errorMessage;
	private String errorCode;
	
	public IAException(String errorMessage,String errorCode){
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	public IAException(String errorMessage){
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public IAException(String errorMessage,Throwable cause){
		super(errorMessage,cause);
		this.errorMessage = errorMessage;
		
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}
	

   @Override
   public String toString(){
	   
	   return "Error Code: " + errorCode + ",Error Message: "+ errorMessage;
	   
   }
   
	
}
