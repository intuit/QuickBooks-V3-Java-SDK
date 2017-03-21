package com.intuit.ia.exception;
/**
 * @author Aditi
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
