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
