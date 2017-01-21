package com.intuit.ipp.net;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.ConfigurationException;
import com.intuit.ipp.net.RetryHelper;

public class RetryHelperTest {
	
     @Test
	 public void testArgumentNegativeValue()
     {
         long argumentValue = -5;
         String argumentName = "argumentValue";
         boolean isValid = true;
         try
         {
             RetryHelper.argumentNotNegativeValue(argumentValue, argumentName);
         }
         catch (ConfigurationException nve)
         {
        	isValid = false;
         }
         
         Assert.assertFalse(isValid);
     }
     
     @Test
	 public void testArgumentNotNegativeValue()
     {
         long argumentValue = 5;
         String argumentName = "argumentValue";
         boolean isValid = true;
         try
         {
             RetryHelper.argumentNotNegativeValue(argumentValue, argumentName);
         }
         catch (ConfigurationException nve)
         {
        	isValid = false;
         }
         
         Assert.assertTrue(isValid);
     }
}