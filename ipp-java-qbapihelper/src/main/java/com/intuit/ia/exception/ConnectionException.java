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
