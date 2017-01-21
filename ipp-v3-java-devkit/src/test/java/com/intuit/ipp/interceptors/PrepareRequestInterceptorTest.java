package com.intuit.ipp.interceptors;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class PrepareRequestInterceptorTest {

    private PrepareRequestInterceptor instance = new PrepareRequestInterceptor();
    private IntuitMessage message = new IntuitMessage();
    private Context context;

    @BeforeClass
    public void setUp() throws FMSException {
        context = new Context(new OAuthAuthorizer("fakeTicket","fakeToken", "fakeToken", "fakeToken"), ServiceType.QBO, "fakeRealm");
        context.setRequestID("anyRequestID");
        message.getRequestElements().setAction("fakeAction");
        message.getRequestElements().setContext(context);

    }

    @AfterMethod
    public void tearDown()  {
        message.getRequestElements().getRequestParameters().remove(RequestElements.REQ_PARAM_SENDTO);
        message.getRequestElements().getRequestParameters().remove(RequestElements.REQ_PARAM_ENTITY_SELECTOR);
    }

    @Test
    // Unit-like test which verifies URL generation
    public void testExecute_QBO_URI() throws FMSException  {
        instance.execute(message);
        String actual = message.getRequestElements().getRequestParameters().get("uri");
        Assert.assertEquals(actual, Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction?requestid=anyRequestID&minorversion=5&");
        //change by nidhi for testing 
        //Assert.assertEquals(actual, Config.getProperty(Config.BASE_URL_QBO) + "/fakeRealm/fakeAction?requestid=anyRequestID&include=updateaccountontxnsminorversion=5&");
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