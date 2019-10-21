package com.intuit.ipp.interceptors;

import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Config;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.intuit.ipp.interceptors.RequestElements.REQ_PARAM_METHOD_TYPE;
import static com.intuit.ipp.net.OperationType.DOWNLOAD;
import static com.intuit.ipp.net.OperationType.UPLOAD;
import static com.intuit.ipp.util.Config.SERIALIZATION_REQUEST_FORMAT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SerializeInterceptorTest {

    private SerializeInterceptor serializeInterceptor = new SerializeInterceptor();

    @Test(description = "Given a POST request with object for serialization, " +
            "the serialized data should be present in the serializedData object")
    public void execute_positiveCase1() throws FMSException {
        IntuitMessage message = new IntuitMessage();
        TestJson json = new TestJson();
        json.setFoo("bar");
        JAXBElement<TestJson> jaxbElement = new JAXBElement(new QName(TestJson.class.getSimpleName()), TestJson.class, json);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        message.getRequestElements().setObjectToSerialize(jaxbElement);
        serializeInterceptor.execute(message);
        assertEquals(message.getRequestElements().getSerializedData(), "{\"foo\":\"bar\"}");
    }

    @Test(description = "Given a POST request with post body for serialization, " +
            "the serialized data should be present in the serializedData object")
    public void execute_positiveCase2() throws FMSException {
        IntuitMessage message = new IntuitMessage();
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        String jsonInput = "{\"foo\":\"bar\"}";
        message.getRequestElements().setPostString(jsonInput);
        serializeInterceptor.execute(message);
        assertEquals(message.getRequestElements().getSerializedData(), jsonInput);
    }

    @Test(description = "Given a GET request for serialization, the serialized data should be null")
    public void execute_positiveCase3() throws FMSException {
        IntuitMessage message = new IntuitMessage();
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "GET");
        message.getRequestElements().setRequestParameters(requestParams);
        serializeInterceptor.execute(message);
        assertEquals(message.getRequestElements().getSerializedData(), null);
    }

    @Test(description = "Given a POST request with object for serialization " +
            " along with an operations that is supported for serialization and " +
            "a file to be uploaded. The serialized data should be present in the " +
            "serializedData object along with the file that would be uploaded.")
    public void execute_positiveCase4() throws FMSException {
        IntuitMessage message = new IntuitMessage();
        TestJson json = new TestJson();
        json.setFoo("bar");
        JAXBElement<TestJson> jaxbElement = new JAXBElement(new QName(TestJson.class.getSimpleName()), TestJson.class, json);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        message.getRequestElements().setObjectToSerialize(jaxbElement);
        message.getRequestElements().setAction(UPLOAD.toString());
        message.getRequestElements().getUploadRequestElements().setBoundaryForEntity("Entity");
        message.getRequestElements().getUploadRequestElements().setBoundaryForContent("Content");
        Attachable attachable = new Attachable();
        attachable.setFileName("test.txt");
        message.getRequestElements().setEntity(attachable);
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());
        message.getRequestElements().getUploadRequestElements().setDocContent(inputStream);
        serializeInterceptor.execute(message);
        assertTrue(message
                .getRequestElements()
                .getSerializedData()
                .equalsIgnoreCase("Entity{\"foo\":\"bar\"}Content"));
        assertTrue(Arrays.equals("test data".getBytes(), message.getRequestElements().getUploadFile()));
    }

    @Test(description = "Given a POST request with object for serialization " +
            " along with an operations that's not supported for serialization. " +
            "The serialized data should be present in the serializedData object.")
    public void execute_positiveCase5() throws FMSException {
        IntuitMessage message = new IntuitMessage();
        TestJson json = new TestJson();
        json.setFoo("bar");
        JAXBElement<TestJson> jaxbElement = new JAXBElement(new QName(TestJson.class.getSimpleName()), TestJson.class, json);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(REQ_PARAM_METHOD_TYPE, "POST");
        message.getRequestElements().setRequestParameters(requestParams);
        message.getRequestElements().setObjectToSerialize(jaxbElement);
        message.getRequestElements().setAction(DOWNLOAD.toString());
        serializeInterceptor.execute(message);
        assertEquals(message.getRequestElements().getSerializedData(), "{\"foo\":\"bar\"}");
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