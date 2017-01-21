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
