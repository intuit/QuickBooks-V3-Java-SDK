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
package com.intuit.oauth2.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;

import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.utils.LoggerImpl;
import com.intuit.oauth2.utils.PropertiesConfig;

/**
 * Client class to make http request calls
 * 
 * @author dderose
 *
 */
public class HttpRequestClient {
	
	private final org.apache.http.client.HttpClient client;
	
	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int SOCKET_TIMEOUT = 30000;
	
	private static final Logger logger = LoggerImpl.getInstance();
	
    /**
     * Build the HttpClient
     *
     */
    public HttpRequestClient() {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(CONNECTION_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
        
        //add default headers
        List<BasicHeader> headers = new ArrayList<BasicHeader>();
        headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8"));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "V3JavaSDK-OAuth2-" + PropertiesConfig.getInstance().getProperty("version")));
     
        //build the client
        client = HttpClientBuilder.create()
            .setConnectionManager(new PoolingHttpClientConnectionManager())
            .setDefaultRequestConfig(config)
            .setDefaultHeaders(headers)
            .setMaxConnPerRoute(10)
            .build();
    }
	
	/**
	 * Method to make the HTTP request call using the request attributes supplied
	 * 
	 * @param request
	 * @return
	 * @throws InvalidRequestException
	 */
	public Response makeRequest(Request request) throws InvalidRequestException {
		
		logger.debug("Enter HttpRequestClient::makeRequest");	
		
		//prepare request
		RequestBuilder builder = RequestBuilder.create(request.getMethod().value())
	            .setUri(request.constructURL().toString())
	            .setVersion(HttpVersion.HTTP_1_1)
	            .setCharset(StandardCharsets.UTF_8);
			
		//add auth header
		if (request.isRequiresAuthentication()) {
            builder.addHeader(HttpHeaders.AUTHORIZATION, request.getAuthString());
        }
		
		MethodType method = request.getMethod();
        if (method == MethodType.POST) {
        	//add post header
        	builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        
        	//add post params
            for (NameValuePair nvp : request.getPostParams()) {
            	builder.addParameter(nvp);
            }
        } 
        
        logger.debug("Request URI : " + builder.getUri());
        logger.debug("Http Method : " + builder.getMethod());

        try {
        	//make the call
            HttpResponse response = client.execute(builder.build());
            //prepare response
            return new Response(
                response.getEntity() == null ? null : response.getEntity().getContent(),
                response.getStatusLine().getStatusCode()
            );
        } catch (IOException e) {
        	logger.error("Exception while making httpRequest", e);
            throw new InvalidRequestException(e.getMessage());
        }
		
	}

}
