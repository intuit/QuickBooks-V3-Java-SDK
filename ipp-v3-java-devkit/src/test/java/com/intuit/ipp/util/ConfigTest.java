package com.intuit.ipp.util;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConfigTest {
	

	@BeforeClass
	public void init(){

	}

	@Test
	public void testGetBooleanProperty_Null(){
        Config.setProperty("test","null");
        Assert.assertFalse(Config.getBooleanProperty("test"));
	}

    @Test
    public void testGetBooleanProperty_False(){
        Config.setProperty("test","false");
        Assert.assertFalse(Config.getBooleanProperty("test"));
    }

    @Test
    public void testGetBooleanProperty_Empty(){
        Config.setProperty("test","");
        Assert.assertFalse(Config.getBooleanProperty("test"));
    }

    @Test
    public void testGetBooleanProperty_Zero(){
        Config.setProperty("test","0");
        Assert.assertFalse(Config.getBooleanProperty("test"));
    }


    @Test
    public void testGetBooleanProperty_One(){
        Config.setProperty("test","1");
        Assert.assertFalse(Config.getBooleanProperty("test"));
    }

    @Test
    public void testGetBooleanProperty_True(){
        Config.setProperty("test","true");
        Assert.assertTrue(Config.getBooleanProperty("test"));
    }

    @Test
    public void testGetBooleanProperty_XXX(){
        Config.setProperty("test","xxx");
        Assert.assertFalse(Config.getBooleanProperty("test"));
    }

    @Test
    public void testGetBooleanPropertyDefault_Null()
    {
        Config.setProperty("test","null");
        Assert.assertTrue(Config.getBooleanProperty("test", true));
        Assert.assertFalse(Config.getBooleanProperty("test", false));
    }
    @Test
    public void testGetBooleanPropertyDefault_False()
    {
        Config.setProperty("test","false");
        Assert.assertFalse(Config.getBooleanProperty("test", true));
        Assert.assertFalse(Config.getBooleanProperty("test", false));
    }

    @Test
    public void testGetBooleanPropertyDefault_True()
    {
        Config.setProperty("test","true");
        Assert.assertTrue(Config.getBooleanProperty("test", true));
        Assert.assertTrue(Config.getBooleanProperty("test", false));
    }


}
