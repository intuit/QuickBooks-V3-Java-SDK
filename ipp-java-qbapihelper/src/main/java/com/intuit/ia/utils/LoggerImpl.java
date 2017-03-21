/*******************************************************************************
 * Copyright (c) 2010 Intuit, Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intuit, Inc. - initial API and implementation
 ******************************************************************************/
package com.intuit.ia.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Wrapper class for our chosen log provider. Currently java.util.logging.  If you want to route logging through
 * another logger, check the documentation of that logger on how to intercept java.util.logging.
 * 
 * @see http://www.oracle.com/technology/pub/articles/hunter_logging.html.
 * @see http://www.slf4j.org/legacy.html
 */
public class LoggerImpl {
	
	private volatile Logger slf4jLogger;

	private static Logger sInstance;

	public static Logger getInstance() {
		if (null == sInstance) {
			sInstance = new LoggerImpl().getLogger();
			
		}
		return sInstance;
	}

	/**
	 * Initializes the Logger using the ipp-logging.properties file (as a resource from classpath). If
	 * this fails it attempts to reset the configuration to JVM defaults.
	 */
	private LoggerImpl() {}
	
	public Logger getLogger() {
	    if (null == slf4jLogger) {
	        synchronized (this) {
	            if (null == slf4jLogger) {
	            	slf4jLogger = LoggerFactory.getLogger("com.intuit.iahelper");
	            }
	        }
    	}
	    return slf4jLogger;
	}

}
