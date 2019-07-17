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
package com.intuit.payment.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intuit.payment.config.RequestContext;

/**
 * Class to hold the request attributes for making the http call
 * method - defines the httpmethod type
 * url - defines the api url
 * ignoreAuthHeader - flag to determine if authorization header should be applied
 * postJson - serialized object string used for post data
 * requestObject - object sent by the client, this is serialized and sent as post data via postJson
 * typeReference - indicates the Class type that should be used during deserialization
 * context - holds the requestContext attributes
 * 
 * @author dderose
 *
 */
public class Request {

	private final MethodType method;
	private final String url;
	private final boolean ignoreAuthHeader;
	private String postJson;
	private final Object requestObject;
	private final TypeReference<?> typeReference;
	private final RequestContext context;

	private Request(RequestBuilder builder) {
		this.method = builder.method;
		this.url = builder.url;
		this.ignoreAuthHeader = builder.ignoreAuthHeader;
		this.requestObject = builder.requestObject;
		this.typeReference = builder.typeReference;
		this.context = builder.context;
	}

	public MethodType getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public boolean isIgnoreAuthHeader() {
		return ignoreAuthHeader;
	}

	public String getPostJson() {
		return postJson;
	}

	public Object getRequestObject() {
		return requestObject;
	}

	public TypeReference<?> getTypeReference() {
		return typeReference;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setPostJson(String postJson) {
		this.postJson = postJson;
	}

	/**
	 * Builder class for Request
	 * 
	 * @author dderose
	 *
	 */
	public static class RequestBuilder {

		private final MethodType method;
		private final String url;
		private boolean ignoreAuthHeader;
		private Object requestObject;
		private TypeReference<?> typeReference;
		private RequestContext context;

		public RequestBuilder(MethodType method, String url) {
			this.method = method;
			this.url = url;
		}

		public RequestBuilder ignoreAuthHeader(boolean ignoreAuthHeader) {
			this.ignoreAuthHeader = ignoreAuthHeader;
			return this;
		}

		public RequestBuilder requestObject(Object requestObject) {
			this.requestObject = requestObject;
			return this;
		}

		public RequestBuilder typeReference(TypeReference<?> typeReference) {
			this.typeReference = typeReference;
			return this;
		}

		public RequestBuilder context(RequestContext context) {
			this.context = context;
			return this;
		}

		public Request build() {
			return new Request(this);
		}

	}

}
