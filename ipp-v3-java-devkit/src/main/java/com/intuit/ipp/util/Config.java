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

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.EnvironmentConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Class to have all the configuration details.
 * This class uses ThreadLocal, so that each thread maintains its own scope.
 *
 */
public final class Config {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable CompositeConfiguration
	 */
	private CompositeConfiguration cc = null;
	
	/**
	 * end point key name in the configuration for Intuit API
	 */
	
	public static final String BASE_URL_QB_API = "baseURL.quickbooks-api";

	/**
	 * variable BASE_URL_QBO
	 */
	public static final String BASE_URL_QBO = "baseURL.qbo";

	/**
	 * variable BASE_URL_PLATFORMSERVICE
	 */
	public static final String BASE_URL_PLATFORMSERVICE = "baseURL.platformService";
	public static final String BASE_URL_ENTITLEMENTSERVICE = "baseURL.entitlementService";

	/**
	 * variable PROXY_HOST
	 */
	public static final String PROXY_HOST = "proxy.host";

	/**
	 * variable PROXY_PORT
	 */
	public static final String PROXY_PORT = "proxy.port";

	/**
	 * variable PROXY_USERNAME
	 */
	public static final String PROXY_USERNAME = "proxy.username";
	
	/**
	 * variable PROXY_PASSWORD
	 */
	public static final String PROXY_PASSWORD = "proxy.password";
	
	public static final String PROXY_DOMAIN = "proxy.domain";
	
	/**
	 * variable PROXY_KEYSTORE_PATH
	 */
	public static final String PROXY_KEYSTORE_PATH = "proxy.keystore.path";

	/**
	 * variable PROXY_KEYSTORE_PASSWORD
	 */
	public static final String PROXY_KEYSTORE_PASSWORD = "proxy.keystore.password";

	/**
	 * variable RETRY_MODE
	 */
	public static final String RETRY_MODE = "retry.mode";
	
	/**
	 * variable RETRY_FIXED_COUNT
	 */
	public static final String RETRY_FIXED_COUNT = "retry.fixed.count";

	/**
	 * variable RETRY_FIXED_INTERVAL
	 */
	public static final String RETRY_FIXED_INTERVAL = "retry.fixed.interval";
	
	/**
	 * variable RETRY_INCREMENTAL_COUNT
	 */
	public static final String RETRY_INCREMENTAL_COUNT = "retry.incremental.count";

	/**
	 * variable RETRY_INCREMENTAL_INTERVAL
	 */
	public static final String RETRY_INCREMENTAL_INTERVAL = "retry.incremental.interval";

	/**
	 * variable RETRY_INCREMENTAL_INCREMENT
	 */
	public static final String RETRY_INCREMENTAL_INCREMENT = "retry.incremental.increment";

	/**
	 * variable RETRY_EXPONENTIAL_COUNT
	 */
	public static final String RETRY_EXPONENTIAL_COUNT = "retry.exponential.count";
	
	/**
	 * variable RETRY_EXPONENTIAL_MIN_BACKOFF
	 */
	public static final String RETRY_EXPONENTIAL_MIN_BACKOFF = "retry.exponential.minBackoff";

	/**
	 * variable RETRY_EXPONENTIAL_MAX_BACKOFF
	 */
	public static final String RETRY_EXPONENTIAL_MAX_BACKOFF = "retry.exponential.maxBackoff";

	/**
	 * variable RETRY_EXPONENTIAL_DELTA_BACKOFF
	 */
	public static final String RETRY_EXPONENTIAL_DELTA_BACKOFF = "retry.exponential.deltaBackoff";

	/**
	 * variable COMPRESSION_REQUEST_FORMAT
	 */
	public static final String COMPRESSION_REQUEST_FORMAT = "message.request.compression";

	/**
	 * variable COMPRESSION_RESPONSE_FORMAT
	 */
	public static final String COMPRESSION_RESPONSE_FORMAT = "message.response.compression";

	/**
	 * variable SERIALIZATION_REQUEST_FORMAT
	 */
	public static final String SERIALIZATION_REQUEST_FORMAT = "message.request.serialization";

	/**
	 * variable SERIALIZATION_RESPONSE_FORMAT
	 */
	public static final String SERIALIZATION_RESPONSE_FORMAT = "message.response.serialization";
	
	/**
	 * variable SERIALIZATION_RESPONSE_FORMAT
	 */
	public static final String TIMEOUT_CONNECTION = "timeout.connectionTimeout";
	
	/**
	 * variable SERIALIZATION_RESPONSE_FORMAT
	 */
	public static final String TIMEOUT_REQUEST = "timeout.requestTimeout";
	
	/**
	 * variable ENVIRONMENT_VAR_QBO_BASE_URL
	 */
	public static final String ENVIRONMENT_VAR_QBO_BASE_URL = "IPP_QBO_BASE_URL";
		
	/**
	 * Set to HTTP_URL_CONNECTION if required. Default is Apache HTTP Client if not set. In XML config you can set as <httpTransport>HTTP_URL_CONNECTION</httpTransport>
	 */
	public static final String HTTP_TRANSPORT = "httpTransport";
	
	public static final String TLS_VERSION = "tls.version";


    public static final String BIGDECIMAL_SCALE_SHIFT = "feature.bigDecimalScaleShift";
    
    public static final String WEBHOOKS_VERIFIER_TOKEN = "webhooks.verifier.token";
	
	/**
	 * variable ThreadLocal
	 */
	private static ThreadLocal<Config> local = new ThreadLocal<Config>() {
		public Config initialValue() {
			return new Config();
		}

		public Config get() {
			return super.get();
		}
	};

	/**
	 * Constructor config
	 */
	private Config() {
		try {
			XMLConfiguration config = null;
			XMLConfiguration devConfig = null;
			
			cc = new CompositeConfiguration();
			
			try {
				devConfig = new XMLConfiguration("intuit-config.xml");
				cc.addConfiguration(devConfig);
			} catch (ConfigurationException e) {
				LOG.warn("issue reading config.xml");
				LOG.debug("issue reading config.xml");
			}
			
			config = new XMLConfiguration("intuit-default-config.xml");
			cc.addConfiguration(config);
			
			try {
				EnvironmentConfiguration envConfig = new EnvironmentConfiguration();
				String envQBOBaseUrl = envConfig.getString(ENVIRONMENT_VAR_QBO_BASE_URL);
				if (StringUtils.hasText(envQBOBaseUrl)) {
					cc.setProperty(BASE_URL_QBO, envQBOBaseUrl);
				}
			} catch (Exception e) {
				LOG.warn("ConfigurationException while reading environment configuration.", e);
			}
			
		} catch (ConfigurationException e) {
			LOG.error("ConfigurationException while loading configuration xml file.", e);
		}
	}

	/**
	 * Gets the property value for the given property name
	 * @param key
	 * @return returns value
	 */
	public static String getProperty(String key) {
		return local.get().cc.getString(key);
	}

	/**
	 * Sets the property to the configuration
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		local.get().cc.setProperty(key, value);
	}


    /**
     * Returns boolean for specific setting
     * @param key
     * @return
     */
    public static Boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, null);
    }


    /**
     * Returns boolean value for specified property and default value
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBooleanProperty(String key, Boolean defaultValue) {
        String value = getProperty(key);
        if((null == value) || value.isEmpty() ) {
            return (null == defaultValue) ? false : defaultValue;
        }
        if("null".equals(value.toLowerCase()) && (null != defaultValue) ) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

	/**
	 * Returns a copy of manual configuration overrides. This implementation will create a deep
	 * clone, i.e. all manual configurations contained in this composite will also be
	 * cloned.
	 *
	 * @return the copy
	 */
    public static Configuration cloneConfigurationOverrides(){
		return ConfigurationUtils
				.cloneConfiguration(local.get().cc.getInMemoryConfiguration());
	}

	/**
	 * Adds given manual configuration overrides to the {@link CompositeConfiguration} stored in ThreadLocal.
	 * @param configuration The configuration to add.
	 */
	public static void addConfigurationOverrides(Configuration configuration){
		ConfigurationUtils.copy(configuration, local.get().cc);
	}

}
