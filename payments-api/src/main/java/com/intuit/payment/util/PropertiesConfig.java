/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.payment.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * Class to read the attributes from properties file payment.properties
 * 
 * @author dderose
 *
 */
public class PropertiesConfig {

	protected Logger logger = LoggerImpl.getInstance();
	private static Properties prop = new Properties();
	private static PropertiesConfig config = null;
	
	private static final String PROP_FILE_NAME = "payment.properties";

	//Prevent instantiation
	private PropertiesConfig() {

	}

	public static synchronized PropertiesConfig getInstance() {

		if (config == null) {
			config = new PropertiesConfig();
			config.readProperties();
		}
		return config;
	}

	/**
	 * Method to read propeties file and store the data
	 * 
	 */
	private void readProperties() {

		InputStream input = null;
		try {

			input = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			if(input==null){
				logger.info("Unnable to find " + PROP_FILE_NAME);
				return;
			}
			prop.load(input);

		} catch (Exception e) {
			logger.info("exception in PropertiesConfig readProperties");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					logger.info("exception in PropertiesConfig readProperties finally");
				}
			}
		}

	}

	/**
	 * Method to retrive data use property key
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
}
