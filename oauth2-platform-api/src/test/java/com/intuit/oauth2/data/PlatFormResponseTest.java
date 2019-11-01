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
package com.intuit.oauth2.data;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;


public class PlatFormResponseTest {
    @Test
    public void testGetErrorCode()
    {
        PlatformResponse resp = new PlatformResponse();
        resp.setErrorCode("200");
        assertEquals(resp.getErrorCode(),"200");
    }

    @Test
    public void testGetErrorMesssage()
    {
        PlatformResponse resp = new PlatformResponse();
        resp.setErrorMessage("Error Message");
        assertEquals(resp.getErrorMessage(),"Error Message");
    }

    @Test
    public void testGetStatus()
    {
        PlatformResponse resp = new PlatformResponse();
        resp.setStatus("Status");
        assertEquals(resp.getStatus(),"Status");
    }


}
