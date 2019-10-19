package com.intuit.ipp.interceptors;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.serialization.JSONSerializerTest;
import com.intuit.ipp.util.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

import static com.intuit.ipp.interceptors.RequestElements.REQ_PARAM_METHOD_TYPE;
import static com.intuit.ipp.util.Config.SERIALIZATION_REQUEST_FORMAT;
import static org.testng.Assert.assertTrue;

public class SerializeInterceptorTest {

    private SerializeInterceptor serializeInterceptor = new SerializeInterceptor();
    private IntuitMessage message = new IntuitMessage();

    @Test(description = "Given a POST request with object for serialization, " +
            "the serialized data should be present in the serializedData object")
    public void execute_positiveCase1() throws FMSException {
        JSONSerializerTest test1 = new JSONSerializerTest();
        TestJson json = new TestJson();
        json.setFoo("bar");
        JAXBElement<TestJson> jaxbElement = new JAXBElement(new QName(TestJson.class.getSimpleName()), TestJson.class, json);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        message.getRequestElements().setObjectToSerialize(jaxbElement);
        serializeInterceptor.execute(message);
        Assert.assertEquals(message.getRequestElements().getSerializedData(), "{\"foo\":\"bar\"}");
    }

    @Test(description = "Given a POST request with post body for serialization, " +
            "the serialized data should be present in the serializedData object")
    public void execute_positiveCase2() throws FMSException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        String jsonInput = "{\"foo\":\"bar\"}";
        message.getRequestElements().setPostString(jsonInput);
        serializeInterceptor.execute(message);
        Assert.assertEquals(message.getRequestElements().getSerializedData(), jsonInput);
    }

    @Test(description = "Given a GET request for serialization, the serialized data should be null")
    public void execute_positiveCase3() throws FMSException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "GET");
        message.getRequestElements().setRequestParameters(requestParams);
        serializeInterceptor.execute(message);
        Assert.assertEquals(message.getRequestElements().getSerializedData(), null);
    }

    @Test(description = "Serialization request format returned should be of " +
            "the form: message.request.serialization")
    public void getSerializationRequestFormat() {
        SerializeInterceptor interceptor = new SerializeInterceptor();
        assertTrue(interceptor
                .getSerializationRequestFormat()
                .equalsIgnoreCase(Config.getProperty(SERIALIZATION_REQUEST_FORMAT)));
    }

    @XmlRootElement
    class TestJson {

        String foo;

        public String getFoo() {
            return foo;
        }

        @XmlElement
        public void setFoo(String foo) {
            this.foo = foo;
        }
    }
}