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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;

import com.intuit.oauth2.exception.InvalidRequestException;

/**
 * Class to hold the request attributes for making the http call
 * 
 * @author dderose
 *
 */
public class Request {

	private final MethodType method;
    private final String url;
    private final List<NameValuePair> postParams;
    private final boolean requiresAuthentication;
    private final String authString;
    private final String postJson;
    
    private Request(RequestBuilder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.requiresAuthentication = builder.requiresAuthentication;
        this.authString = builder.authString;
        this.postParams = builder.postParams;
        this.postJson = builder.postJson;
	}


	public MethodType getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public boolean isRequiresAuthentication() {
		return requiresAuthentication;
	}


	public String getAuthString() {
		return authString;
	}
	
	public List<NameValuePair> getPostParams() {
		return postParams;
	}
	
	public String getPostJson() {
		return postJson;
	}


	/**
	 * Prepares request URL
	 * 
	 * @return
	 * @throws InvalidRequestException
	 */
	public URL constructURL() throws InvalidRequestException {	
 
        String stringUri = url;

        try {
            URI uri = new URI(stringUri);
            return uri.toURL();
        } catch (final URISyntaxException e) {
            throw new InvalidRequestException("Bad URI: " + stringUri, e);
        } catch (final MalformedURLException e) {
            throw new InvalidRequestException("Bad URL: " + stringUri, e);
        }
    }
	
	public static class RequestBuilder {
		
		private final MethodType method;
	    private final String url;
	    private List<NameValuePair> postParams;
	    private boolean requiresAuthentication;
	    private String authString;
	    private String postJson;

		public RequestBuilder(MethodType method, String url) {
			this.method = method;
			this.url = url;
		}
		
		public RequestBuilder postParams(List<NameValuePair> postParams) {
			this.postParams = postParams;
			return this;
		}
		
		public RequestBuilder requiresAuthentication(boolean requiresAuthentication) {
			this.requiresAuthentication = requiresAuthentication;
			return this;
		}
		
		public RequestBuilder authString(String authString) {
			this.authString = authString;
			return this;
		}
		
		public RequestBuilder postJson(String postJson) {
			this.postJson = postJson;
			return this;
		}

		public Request build() {
			return new Request(this);
		}
		
	}
    
}
