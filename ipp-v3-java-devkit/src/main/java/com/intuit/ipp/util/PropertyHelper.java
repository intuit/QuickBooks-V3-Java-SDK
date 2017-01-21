package com.intuit.ipp.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Helper class to read the ippdevkit.properties file
 *
 */
public final class PropertyHelper {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	/**
	 * variable propertHelper
	 */
	private static PropertyHelper propertHelper;
	
	/**
	 * variable version
	 */
	private String version;
	
	/**
	 * variable requestSource
	 */
	private String requestSource;
	
	/**
	 * variable requestSourceHeader
	 */
	private String requestSourceHeader;
	
	/**
	 * Constructor PropertyHelper
	 */
	private PropertyHelper() {
	}
	
	/**
	 * Method to instantiate the PropertyHelper object
	 * 
	 * @return PropertyHelper
	 */
	public static synchronized PropertyHelper getInstance() {
		if (propertHelper == null) {
			return init();
		}
		return propertHelper;
	}
	
	/**
	 * Method to init the PropertyHelper by loading the ippdevkit.properties file
	 * 
	 * @return PropertyHelper
	 */
	private static PropertyHelper init() {
		propertHelper = new PropertyHelper();
		try
		{
		ResourceBundle bundle = ResourceBundle.getBundle("ippdevkit");
		
		propertHelper.setVersion(bundle.getString("version"));
		propertHelper.setRequestSource(bundle.getString("request.source"));
		propertHelper.setRequestSourceHeader(bundle.getString("request.source.header"));
		}
		catch(MissingResourceException e){
			LOG.debug("no value found for key", e);
		}
		return propertHelper;
	}
	
	/**
	 * Gets verison
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets version
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Sets requestSource
	 * @return requestSource
	 */
	public String getRequestSource() {
		return requestSource;
	}

	/**
	 * Gets requestSource
	 * @param requestSource
	 */
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	
	/**
	 * Gets requestSourceHeader
	 * 
	 * @param requestSourceHeader
	 */
	public String getRequestSourceHeader() {
		return requestSourceHeader;
	}

	/**
	 * Sets requestSourceHeader
	 * 
	 * @return requestSourceHeader
	 */
	public void setRequestSourceHeader(String requestSourceHeader) {
		this.requestSourceHeader = requestSourceHeader;
	}
}
