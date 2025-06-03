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
package com.intuit.payment.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author harry0123
 */
public class CheckContextTest {

    private DeviceInfo deviceInfo;

    private CheckContext checkContext;

    @BeforeTest
    public void init() {
        deviceInfo = new DeviceInfo();
    }

    @BeforeMethod
    public void setUpWithBuilder() {
        checkContext = new CheckContext.Builder(deviceInfo).build();
    }

    @Test
    public void testConstructor() {
        CheckContext newCheckContext = new CheckContext();
        Assert.assertNotNull(newCheckContext);
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(checkContext.getDeviceInfo(), deviceInfo);
    }

    @Test
    public void testAllSetters() {
        DeviceInfo newDeviceInfo = new DeviceInfo();
        checkContext.setDeviceInfo(newDeviceInfo);

        Assert.assertEquals(checkContext.getDeviceInfo(), newDeviceInfo);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(checkContext);
        String actualResult = checkContext.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
