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

package com.intuit.oauth2.http;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class MethodTypeTest {

    @Test
    public void testGetMethodName() {
        assertEquals("GET", MethodType.GET.name());
    }

    @Test
    public void testPostMethodName() {
        assertEquals("POST", MethodType.POST.name());
    }

    @Test
    public void testMethodValue(){
        assertEquals(MethodType.fromValue("POST").name(),"POST");
    }

    @Test
    public void testOtherMethodValue(){
        try {
            MethodType.fromValue("DUMMYMETHODTYPE").name();
        }
        catch (Exception e)
        {
            assertEquals(e.getClass(),IllegalArgumentException.class);
        }
    }

}
