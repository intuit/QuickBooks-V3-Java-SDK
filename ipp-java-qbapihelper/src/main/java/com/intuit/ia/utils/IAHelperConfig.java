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
package com.intuit.ia.utils;

import java.io.InputStream;
import java.util.Properties;

public class IAHelperConfig {

	private static final String PROP_FILE_NAME = "ia.properties";
	private static Properties prop = new Properties();
	private static IAHelperConfig configObj = null;

	private IAHelperConfig() {

	}

	public static synchronized IAHelperConfig getInstance() {

		if (configObj == null) {
			configObj = new IAHelperConfig();
			configObj.readProperties();
		}
		return configObj;
	}

	private void readProperties() {

		InputStream input = null;
		try {

			input = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			prop.load(input);

		} catch (Exception e) {

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {

				}
			}
		}

	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
