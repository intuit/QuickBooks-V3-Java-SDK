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
