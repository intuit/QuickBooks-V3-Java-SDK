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
package com.intuit.ipp.util;

/**
 * Wrapper class for our chosen log provider. Currently java.util.logging. If
 * you want to route logging through another logger, check the documentation of
 * that logger on how to intercept java.util.logging.
 * 
 * @see http://www.oracle.com/technology/pub/articles/hunter_logging.html.
 * @see http://www.slf4j.org/legacy.html
 */
public final class Logger {

	/**
	 * variable sInstance
	 */
	private static org.slf4j.Logger sInstance;

	/**
	 * Initializes the Logger using the ipp-logging.properties file (as a
	 * resource from classpath). If this fails it attempts to reset the
	 * configuration to JVM defaults.
	 */
	private Logger() {
	}

	public static org.slf4j.Logger getLogger() {
		if (null == sInstance) {
				sInstance = org.slf4j.LoggerFactory
							.getLogger("com.intuit.logger");
				}
		return sInstance;
	}

}
