package com.intuit.ia.exception;

/**
 * 
 * @author Aditi
 * This represents a custom OpenId Exception
 *
 */
public class OpenIdException extends IAException {
	
	private static final long serialVersionUID = 12222115551L;
	
	public OpenIdException(String errorMessage,String errorCode){
		super(errorMessage,errorCode);
		
	}
	public OpenIdException(String errorMessage){
		super(errorMessage);
	}
	
	public OpenIdException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
	

}
