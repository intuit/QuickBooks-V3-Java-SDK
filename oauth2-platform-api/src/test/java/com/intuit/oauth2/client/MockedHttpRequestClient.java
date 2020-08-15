/*******************************************************************************
 * Copyright (c) 2019 Intuit
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
package com.intuit.oauth2.client;

import com.intuit.oauth2.data.OAuthMigrationRequest;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.http.HttpRequestClient;
import com.intuit.oauth2.http.Request;
import com.intuit.oauth2.http.Response;
import mockit.Mock;
import mockit.MockUp;

public class MockedHttpRequestClient extends MockUp<HttpRequestClient> {

    private Response mockResponse;
    private Request serviceRequestReceived; // Used for asserting the request that was received
    private OAuthMigrationRequest oAuthMigrationRequest;

    void setMockResponse(Response mockResponse) {
        this.mockResponse = mockResponse;
    }

    Request getServiceRequestReceived() {
        return serviceRequestReceived;
    }

    @Mock
    public Response makeJsonRequest(Request request, OAuthMigrationRequest oAuthMigrationRequest) throws InvalidRequestException {
        serviceRequestReceived = request;
        this.oAuthMigrationRequest = oAuthMigrationRequest;
        return mockResponse;
    }

    @Mock
    public Response makeRequest(Request request) throws InvalidRequestException {
        serviceRequestReceived = request;
        return mockResponse;
    }
}
