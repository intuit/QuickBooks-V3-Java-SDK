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
package com.intuit.payment.services.base;

public class ServiceBaseTest {/*
  private ServiceBase serviceBase;
  private MockHttpRequestClient mockHttpRequestClient;

  @BeforeClass
  public void setup() {
    serviceBase = new ServiceBase();
  }

  @BeforeMethod
  public void setupMocks() {
    // Each test in this class will use the MockHttpRequestClient to mock the response for "makeRequest()" method
    mockHttpRequestClient = new MockHttpRequestClient();
  }

  @Test
  public void testSendRequest_successPostRequest() throws BaseException {
    String eCheckId = "5";
    String eCheckNumber = "1234";
    String eCheckAmount = "600.60";

    int httpStatusCode = 200;
    String tid = "test-tid-123";

    String expectedPostJsonContent = "{\n" +
        "  \"amount\" : " + eCheckAmount + ",\n" +
        "  \"checkNumber\" : \"" + eCheckNumber + "\"\n" +
        "}";

    String mockResponseContent = "{\"id\":\"" + eCheckId + "\",\"amount\":\"" + eCheckAmount + "\",\"checkNumber\":\"" + eCheckNumber + "\"}";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    ECheck eCheck = new ECheck.Builder()
        .checkNumber(eCheckNumber)
        .amount(new BigDecimal(eCheckAmount))
        .build();

    Request serviceRequest = new Request.RequestBuilder(MethodType.POST, "https://ap.intuit.com")
        .context(new RequestContext())
        .requestObject(eCheck)
        .typeReference(new TypeReference<ECheck>() {})
        .build();


    Response actualResponse = serviceBase.sendRequest(serviceRequest);
    ECheck actualResponseObject = (ECheck) actualResponse.getResponseObject();
    Request serviceRequestReceivedByClient = mockHttpRequestClient.getServiceRequestReceived();

    // Verify that the Echeck object is serialized and sent correctly to "HttpRequestClient"
    Assert.assertEquals(serviceRequestReceivedByClient.getPostJson(), expectedPostJsonContent);

    Assert.assertEquals(actualResponseObject.getAmount().toString(), eCheckAmount);
    Assert.assertEquals(actualResponseObject.getCheckNumber(), eCheckNumber);
    Assert.assertEquals(actualResponseObject.getId(), eCheckId);

    Assert.assertEquals(actualResponse.getContent(), mockResponseContent);
    Assert.assertEquals(actualResponse.getIntuit_tid(), tid);
  }

  *//**
   * Tests that the "sendRequest" function is able to serialize the Response content to the expected object (ECheck in this test)
   * during a GET request
   *//*
  @Test
  public void testSendRequest_successGetRequest() throws BaseException {
    String eCheckId = "5";
    String eCheckBalance = "505.50";

    int httpStatusCode = 200;
    String tid = "test-tid-123";

    String mockResponseContent = "{\"id\":\"" + eCheckId + "\",\"amount\":\"" + eCheckBalance + "\"}";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    Response actualResponse = serviceBase.sendRequest(serviceRequest);
    ECheck actualResponseObject = (ECheck) actualResponse.getResponseObject();

    Assert.assertEquals(actualResponseObject.getId(), eCheckId);
    Assert.assertEquals(actualResponseObject.getAmount().toString(), eCheckBalance);

    Assert.assertEquals(actualResponse.getContent(), mockResponseContent);
    Assert.assertEquals(actualResponse.getIntuit_tid(), tid);
  }

  *//**
   * Tests the case where response payload is empty
   *//*
  @Test
  public void testSendRequest_successEmptyResponsePayload() throws BaseException {
    int httpStatusCode = 200;
    String tid = "test-tid-123";

    String mockResponseContent = "";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .build();

    Response actualResponse = serviceBase.sendRequest(serviceRequest);

    Assert.assertNull(actualResponse.getResponseObject());
    Assert.assertEquals(actualResponse.getContent(), mockResponseContent);
    Assert.assertEquals(actualResponse.getIntuit_tid(), tid);
  }

  *//**
   * Tests that a BaseException is thrown when the response is null for the service request
   *//*
  @Test(expectedExceptions = BaseException.class,
      expectedExceptionsMessageRegExp = "Unexpected Error , service response object was null ")
  public void testSendRequest_nullResponse() throws BaseException {
    mockHttpRequestClient.setMockResponse(null);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "https://api.intuit.com/quickbooks/v4/payments/")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    serviceBase.sendRequest(serviceRequest);
  }

  *//**
   * Tests the case where the HTTP status code is 404 and the call to httpRequestClient.makeRequest()
   * sets the errors in the response content and deserialization of the error goes through fine
   *//*
  @Test
  public void testSendRequest_errorPageNotFound() throws BaseException {
    int httpStatusCode = 404;
    String tid = "test-tid-123";
    String errorDetail = "Could not find requested page";

    String mockErrorResponseContent = "{\"errors\":[{\"code\":\"" + httpStatusCode + "\",\"detail\":\"" + errorDetail + "\"}]}";
    Response mockResponse = new Response(httpStatusCode, mockErrorResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since BadRequestException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected BadRequestException to be thrown");
    } catch (BadRequestException e) {
      Errors errors = e.getErrors();
      Assert.assertNotNull(errors);
      Assert.assertEquals(errors.getErrorList().size(), 1);

      Error error = errors.getErrorList().get(0);
      Assert.assertEquals(error.getCode(), String.valueOf(httpStatusCode));
      Assert.assertEquals(error.getDetail(), errorDetail);
    }
  }

  *//**
   * Tests the case where the HTTP status code is 404 and the call to httpRequestClient.makeRequest()
   * sets the errors in the response content but the deserialization of the response error content fails
   *//*
  @Test
  public void testSendRequest_errorDeserializingErrorResponse() throws BaseException {
    int httpStatusCode = 404;
    String tid = "test-tid-123";
    String errorDetail = "Could not find requested page";

    // This mock error response content has an invalid format and fields, to test the scenario when deserialization fails
    String mockErrorResponseContent = "\"errorsInvalid\":[{\"codeInvalid\":\"" + httpStatusCode + "\",\"detailInvalid\":\"" + errorDetail + "\"}]";
    Response mockResponse = new Response(httpStatusCode, mockErrorResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since BadRequestException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected BadRequestException to be thrown");
    } catch (BadRequestException e) {
      Errors errors = e.getErrors();
      assertErrorsInException(errors, httpStatusCode, mockErrorResponseContent);
    }
  }

  *//**
   * Tests the case where the HTTP status code is 401 but there is no Error set in the response content from the
   * call to httpRequestClient.makeRequest()
   *//*
  @Test
  public void testSendRequest_errorUnauthorized() throws BaseException {
    int httpStatusCode = 401;
    String tid = "test-tid-123";

    String mockResponseContent = "";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since AuthorizationException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected AuthorizationException to be thrown");
    } catch (AuthorizationException authEx) {
      Errors errors = authEx.getErrors();
      assertErrorsInException(errors, httpStatusCode, mockResponseContent);}
  }

  *//**
   * Tests the case where the HTTP status code is 400 but there is no Error set in the response content from the
   * call to httpRequestClient.makeRequest()
   *//*
  @Test
  public void testSendRequest_errorBadRequest() throws BaseException {
    int httpStatusCode = 400;
    String tid = "test-tid-123";

    String mockResponseContent = "";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since BadRequestException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected BadRequestException to be thrown");
    } catch (BadRequestException badRequestEx) {
      Errors errors = badRequestEx.getErrors();
      assertErrorsInException(errors, httpStatusCode, mockResponseContent);
    }
  }

  *//**
   * Tests the case where the HTTP status code is 500 but there is no Error set in the response content from the
   * call to httpRequestClient.makeRequest()
   *//*
  @Test
  public void testSendRequest_errorInternalServerError() throws BaseException {
    int httpStatusCode = 500;
    String tid = "test-tid-123";

    String mockResponseContent = "";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since ServiceException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected ServiceException to be thrown");
    } catch (ServiceException serviceEx) {
      Errors errors = serviceEx.getErrors();
      assertErrorsInException(errors, httpStatusCode, mockResponseContent);
    }
  }

  *//**
   * Tests the case where the HTTP status code is 301 but there is no Error set in the response content from the
   * call to httpRequestClient.makeRequest()
   *//*
  @Test
  public void testSendRequest_errorMovedPermanently() {
    int httpStatusCode = 301;
    String tid = "test-tid-123";

    String mockResponseContent = "";
    Response mockResponse = new Response(httpStatusCode, mockResponseContent, tid);
    mockHttpRequestClient.setMockResponse(mockResponse);

    Request serviceRequest = new Request.RequestBuilder(MethodType.GET, "url")
        .context(new RequestContext())
        .typeReference(new TypeReference<ECheck>() {})
        .build();

    // Since BaseException is a custom exception and we need to assert the Errors
    // inside the exception object, we're not using "expectedExceptions"
    try {
      serviceBase.sendRequest(serviceRequest);
      fail("Expected BaseException to be thrown");
    } catch (BaseException baseEx) {
      Errors errors = baseEx.getErrors();
      assertErrorsInException(errors, httpStatusCode, mockResponseContent);
    }
  }

  *//**
   * Tests that intuit_tid and requestId is being set on the entity
   *//*
  @Test
  public void testPrepareResponse_success() {
    String expectedRequestId = "146-request-id";
    String expectedTid = "112-tid";

    RequestContext requestContext = new RequestContext();
    requestContext.setRequestId(expectedRequestId);

    Request request = new Request.RequestBuilder(MethodType.GET, "url")
        .context(requestContext)
        .build();

    Response response = new Response(200, "{}", expectedTid);

    Entity entity = new Entity() {};

    serviceBase.prepareResponse(request, response, entity);

    Assert.assertEquals(entity.getIntuit_tid(), expectedTid);
    Assert.assertEquals(entity.getRequestId(), expectedRequestId);
  }

  private void assertErrorsInException(Errors actualErrors, int expectedHttpStatusCode, String expectedErrorContent) {
    Assert.assertNotNull(actualErrors);
    Assert.assertEquals(actualErrors.getErrorList().size(), 1);

    Error error = actualErrors.getErrorList().get(0);
    Assert.assertEquals(error.getCode(), "HttpStatusCode-" + expectedHttpStatusCode);
    Assert.assertEquals(error.getDetail(), "ResponsePayload: " + expectedErrorContent);
  }

  *//**
   * This is a mock implementation of the HttpRequestClient to mock the response from this class
   * Only the "makeRequest()" method is mocked. Other methods can be mocked if necessary.
   *//*
  private final class MockHttpRequestClient extends MockUp<HttpRequestClient> {

    private Response mockResponse;
    private Request serviceRequestReceived; // Used for asserting the request that was received

    void setMockResponse(Response mockResponse) {
      this.mockResponse = mockResponse;
    }

    Request getServiceRequestReceived() {
      return serviceRequestReceived;
    }

    @Mock
    public Response makeRequest(Request serviceRequest) throws BadRequestException {
      this.serviceRequestReceived = serviceRequest; // Used for asserting the values in the request

      return this.mockResponse;
    }
  }
*/}
