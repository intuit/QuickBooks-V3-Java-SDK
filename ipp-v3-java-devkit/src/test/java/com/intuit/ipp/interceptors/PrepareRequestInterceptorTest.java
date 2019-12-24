/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.interceptors;

import com.intuit.ipp.compression.CompressorFactory;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;

import java.util.Map;
import java.util.UUID;

import static com.intuit.ipp.interceptors.RequestElements.HEADER_PARAM_ACCEPT;
import static com.intuit.ipp.interceptors.RequestElements.HEADER_PARAM_CONTENT_TYPE;
import static com.intuit.ipp.util.Config.COMPRESSION_REQUEST_FORMAT;


public class PrepareRequestInterceptorTest {

    private PrepareRequestInterceptor instance = new PrepareRequestInterceptor();
    private IntuitMessage message = new IntuitMessage();
    private Context context;

    @BeforeMethod
    public void setUp() throws FMSException {
        context = new Context(new OAuthAuthorizer("fakeTicket","fakeToken", "fakeToken", "fakeToken"), ServiceType.QBO, "fakeRealm");
        context.setRequestID("anyRequestID");
        message = new IntuitMessage();
        message.getRequestElements().setAction("fakeAction");
        message.getRequestElements().setContext(context);

    }

    @Test
    // Unit-like test which verifies URL generation
    public void testExecute_QBO_URI() throws FMSException  {
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertEquals(actual, Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction?requestid=anyRequestID&minorversion=43&");
     }

    @Test
    public void testExecuteWithPlatformService() throws FMSException {
        message.setPlatformService(true);
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get(RequestElements.REQ_PARAM_RESOURCE_URL);
        Assert.assertEquals(actual, Config.getProperty(Config.BASE_URL_PLATFORMSERVICE) + "/null?act=fakeAction&token=null");

    }

    @Test
    public void testExecuteWithEntitlementService() throws FMSException {
        String originalCompression = Config.getProperty(COMPRESSION_REQUEST_FORMAT);
        try {
            Config.setProperty(COMPRESSION_REQUEST_FORMAT, CompressorFactory.GZIP_COMPRESS_FORMAT);
            message.setEntitlementService(true);
            instance.execute(message);

            Assert.assertEquals(message.getRequestElements().getRequestParameters().get(RequestElements.REQ_PARAM_RESOURCE_URL), Config.getProperty(Config.BASE_URL_ENTITLEMENTSERVICE) + "/entitlements/v3/fakeRealm?requestid=anyRequestID&minorversion=43&");
            Assert.assertEquals(message.getRequestElements().getRequestHeaders().get(RequestElements.HEADER_PARAM_CONTENT_TYPE), "application/xml");
            Assert.assertEquals(message.getRequestElements().getRequestHeaders().get(RequestElements.HEADER_PARAM_ACCEPT), "application/xml");
            Assert.assertNull(message.getRequestElements().getRequestHeaders().get(Config.COMPRESSION_REQUEST_FORMAT));
            Assert.assertEquals(message.getRequestElements().getRequestHeaders().get(RequestElements.HEADER_PARAM_CONTENT_ENCODING), Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT));
        } finally {
            Config.setProperty(COMPRESSION_REQUEST_FORMAT, originalCompression);
        }
    }

    @Test
    public void testPrepareDataServiceRequestForPDF_SmallCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR,"pdf");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/pdf?requestid"));
    }

    @Test
    public void testPrepareDataServiceRequestForPDF_BigCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR,"PDF");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/PDF?requestid"));
        Assert.assertFalse(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/pdf?requestid"));
    }

    @Test
    public void testPrepareDataServiceRequestForSendMail_SmallCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR, "send");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/send?requestid"));
    }


    @Test
    public void testPrepareDataServiceRequestForSendMailViaAddress_SmallCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR,"send");
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_SENDTO,"fake@fake.domain");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/send?sendTo=fake@fake.domain&requestid"));
    }

    @Test
    public void testPrepareDataServiceRequestForSendMailViaAddress_CamelCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR,"send");
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_SENDTO,"FaKe@FaKe.domain");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/send?sendTo=FaKe@FaKe.domain&requestid"));
    }

    @Test
    public void testPrepareDataServiceRequestForSendMail_BigCaps() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_ENTITY_SELECTOR,"SEND");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/SEND?requestid"));
        Assert.assertFalse(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction/send?requestid"));
    }


    @Test
    public void testPrepareDataServiceRequestForVoid() throws FMSException{
        message.getRequestElements().getRequestParameters().put(RequestElements.REQ_PARAM_INCLUDE, "void");
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertTrue(actual.contains(Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction?include=void&requestid"));
    }

}