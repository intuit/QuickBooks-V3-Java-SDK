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
package com.intuit.ipp.services;


import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.interceptors.IntuitMessage;
import com.intuit.ipp.interceptors.RequestElements;
import com.intuit.ipp.net.OperationType;
import junit.framework.Assert;

import mockit.Mock;
import mockit.MockUp;
import org.testng.annotations.Test;


/**
 * Created by amatiushkin on 8/27/15.
 */
public class DataServiceUT {

    @Test
    public void testVoidRequest() throws Exception
    {

        DataService mock = new  MockUp<DataService>() {
            @Mock(minInvocations = 1)
            public void executeInterceptors(IntuitMessage intuitMessage) {
                Assert.assertTrue(intuitMessage.getRequestElements().getRequestParameters().containsKey(RequestElements.REQ_PARAM_INCLUDE));
                Assert.assertTrue(intuitMessage.getRequestElements().getRequestParameters().containsValue(OperationType.VOID.toString()));
            }



        }.getMockInstance();
        mock.voidRequest(new Purchase());


    }
}
