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
package com.intuit.ipp.interceptors;


import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.exception.FMSException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DeserializeInterceptorTest {

    private DeserializeInterceptor instance = new DeserializeInterceptor();
    private IntuitMessage message = new IntuitMessage();


    @Test
    public void testCheckTouchlessExecution() throws FMSException  {
        message.getResponseElements().setContentTypeHeader("application/pdf");
        IntuitResponse response = new IntuitResponse();
        response.setStatus("example");
        message.getResponseElements().setResponse(response);
        instance.execute(message);
        Assert.assertEquals(message.getResponseElements().getResponse(), null);

    }


}