package com.intuit.ia.exception;

public class ConnectionException extends IAException {
	
	private static final long serialVersionUID = 12222114441L;

public ConnectionException(String errorMessage){
	super(errorMessage);
	
}


public ConnectionException(String errorMessage,String errorCode){
	super(errorMessage,errorCode);
}
public ConnectionException(String errorMessage,Throwable e){
	super(errorMessage,e);
}


}
