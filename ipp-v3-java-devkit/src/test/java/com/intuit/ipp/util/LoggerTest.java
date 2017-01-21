package com.intuit.ipp.util;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.util.Logger;

public class LoggerTest {
	
	private org.slf4j.Logger logger;
	@BeforeClass
	public void init(){
		logger = Logger.getLogger();
	}

	@Test
	public void testDebug(){
		logger.debug("called Debug");
		logger.debug("called debug with String param : {}", "message");
		logger.debug("called debug with Object param : {} {}", new Object[] { "message1", "message2"});
	}
	
	@Test
	public void testerror(){
		logger.error("called Error");
		logger.debug("called Error with String param : {}", "message");
		logger.debug("called Error with Object param : {} {}", new Object[] { "message1", "message2"});
	}
	
	@Test
	public void testInfo(){
		logger.info("called info");
		logger.debug("called info with String param : {}", "message");
		logger.debug("called info with Object param : {} {}", new Object[] { "message1", "message2"});
	}
	
	@Test
	public void testwarn(){
		logger.warn("called warn");
		logger.debug("called warn with String param : {}", "message");
		logger.debug("called warn with Object param : {} {}", new Object[] { "message1", "message2"});
	}
	
	@Test
	public void testTrace(){
		logger.trace("called trace");
		logger.debug("called trace with String param : {}", "message");
		logger.debug("called trace with Object param : {} {}", new Object[] { "message1", "message2"});
	}
}
