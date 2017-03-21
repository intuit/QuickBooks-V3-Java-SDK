package com.intuit.ia.exception;

public class OAuthException extends IAException {
	
	private static final long serialVersionUID = 12222113331L;
	
	public OAuthException(String errorMessage,String errorCode){
		super(errorMessage,errorCode);
		
	}
	public OAuthException(String errorMessage){
		super(errorMessage);
	}
	
	
	public OAuthException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
	
}
