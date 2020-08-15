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

import java.util.Date;

/**
 * @author tfung
 */
public class CvcVerificationTest {

    private String result;
    private Date date;

    private CvcVerification cvcVerification;

    @BeforeTest
    public void init() {
        this.result = "result";
        this.date = new Date(01/31/2019);
    }

    @BeforeMethod
    public void setUp() {
        cvcVerification = new CvcVerification.Builder()
                .result(result)
                .date(date)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(cvcVerification.getResult(), result);
        Assert.assertEquals(cvcVerification.getDate(), date);
    }

    @Test
    public void testAllSetters() {
        String newResult = "new result";
        Date newDate = new Date(02/28/2019);

        cvcVerification.setResult(newResult);
        cvcVerification.setDate(newDate);

        Assert.assertEquals(cvcVerification.getResult(), newResult);
        Assert.assertEquals(cvcVerification.getDate(), newDate);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(cvcVerification);
        String actualResult = cvcVerification.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
