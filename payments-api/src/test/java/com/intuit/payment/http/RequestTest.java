package com.intuit.payment.http;

import com.intuit.payment.config.RequestContext;
import org.testng.Assert;
import org.testng.annotations.Test;

/*******************************************************************************
 * Copyright (c) 2019 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author praveenadg
 *******************************************************************************/

public class RequestTest {
    private static final String ACCESS_TOKEN = "intuit123";
    private static final String PROD_BASE_URL = "https://api.intuit.com/quickbooks/v4/payments/";
    private static final String SANDBOX_BASE_URL = "https://sandbox.api.intuit.com/quickbooks/v4/payments/";
    private static final String HOST = "https://github.com";
    private static final String PORT = "8080";

    private Request init(MethodType methodType, String url, boolean ignoreAuthHeader) {
        Request.RequestBuilder builder = new Request.RequestBuilder(methodType, url);
        builder.ignoreAuthHeader(ignoreAuthHeader);
        RequestContext.Builder requestBuilder = new RequestContext.Builder(ACCESS_TOKEN, RequestContext.Environment.PRODUCTION);
        builder.context(requestBuilder.build());
        builder.requestObject(HOST);
        return builder.build();

    }

    @Test
    public void testAllGetters() {
        Request request = init(MethodType.GET, PROD_BASE_URL, Boolean.TRUE);
        Assert.assertEquals(request.getMethod(), MethodType.GET);
        Assert.assertEquals(request.getUrl(), PROD_BASE_URL);
        Assert.assertEquals(request.getRequestObject(), HOST);
        Assert.assertNotNull(request.getContext());
        Assert.assertTrue(request.isIgnoreAuthHeader());
        Assert.assertNull(request.getTypeReference());
    }

    @Test
    public void testAllSetters() {
        Request request = init(MethodType.POST, SANDBOX_BASE_URL, Boolean.FALSE);
        request.setPostJson(PORT);
        Assert.assertEquals(request.getPostJson(), PORT);
        Assert.assertFalse(request.isIgnoreAuthHeader());
        
    }
}
