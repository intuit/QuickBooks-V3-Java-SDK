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
package com.intuit.ipp.interceptors;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.Header;
import org.apache.http.StatusLine;

import com.intuit.ipp.core.Response;
import com.intuit.ipp.services.CallbackMessage;

/**
 * Class to hold the response elements which are used across the interceptor flow. 
 *
 */
public class ResponseElements {

	/**
	 * variable decompressedData
	 */
	private String decompressedData;
	
	/**
	 * variable response
	 */
	private Response response;
	
	/**
	 * variable callbackMessage
	 */
	private CallbackMessage callbackMessage;
	
	/**
	 * variable encodingHeader
	 */
	private String encodingHeader;
	
	/**
	 * variable contentTypeHeader
	 */
	private String contentTypeHeader;
	/**
	 * variable statusCode
	 */
	private int statusCode;
	
	/**
	 * variable statusLine
	 */
	private StatusLine statusLine;
	
	/**
	 * variable responseContent
	 */
	private InputStream responseContent;

    /**
     * contains bytes of the received content
     */
    private InputStream responseBytes;

	/**
	 * variable responseHeaders - all headers received on the response, keyed case-insensitively.
	 * Where a header is repeated, the last value received is retained, consistent with the use of
	 * HttpResponse#getLastHeader for the encoding and content type headers.
	 */
	private Map<String, String> responseHeaders = newHeaderMap();
	
	/**
	 * Gets decompressed data 
	 * 
	 * @return returns decompressed data
	 */
	public String getDecompressedData() {
		return decompressedData;
	}

	/**
	 * Sets decompressed data
	 * 
	 * @param decompressedData the decompressed data
	 */
	public void setDecompressedData(String decompressedData) {
		this.decompressedData = decompressedData;
	}

	/**
	 * Gets deserialized Response object
	 * 
	 * @return returns Intuit Response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * Sets intuit response
	 * 
	 * @param intuitResponse the intuit response
	 */
	public void setResponse(Response response) {
		this.response = response;
	}

	/**
	 * Gets callback Message
	 * 
	 * @return returns callback Message
	 */
	public CallbackMessage getCallbackMessage() {
		return callbackMessage;
	}

	/**
	 * Sets callback Message
	 * 
	 * @param callbackMessage the callback message
	 */
	public void setCallbackMessage(CallbackMessage callbackMessage) {
		this.callbackMessage = callbackMessage;
	}

	/**
	 * Gets encodingHeader
	 * 
	 * @return encodingHeader
	 */
	public String getEncodingHeader() {
		return encodingHeader;
	}

	/**
	 * Sets encodingHeader
	 * 
	 * @param encodingHeader
	 */
	public void setEncodingHeader(String encodingHeader) {
		this.encodingHeader = encodingHeader;
	}

	/**
	 * Gets contentTypeHeader
	 * 
	 * @return contentTypeHeader
	 */
	public String getContentTypeHeader() {
		return contentTypeHeader;
	}

	/**
	 * Sets contentTypeHeader
	 * 
	 * @param contentTypeHeader
	 */
	public void setContentTypeHeader(String contentTypeHeader) {
		this.contentTypeHeader = contentTypeHeader;
	}

	/**
	 * Gets statusLine
	 * 
	 * @return statusLine
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets statusLine
	 * 
	 * @param statusLine
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * Gets statusLine
	 * 
	 * @return statusLine
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * Sets statusLine
	 * 
	 * @param statusLine
	 */
	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	/**
	 * Gets responseContent
	 * 
	 * @return responseContent
	 */
	public InputStream getResponseContent() {
		return responseContent;
	}

    public InputStream getResponseBytes() { return responseBytes; }

	/**
	 * Sets responseContent
	 * 
	 * @param responseContent
	 */
	public void setResponseContent(InputStream responseContent) {
		this.responseContent = responseContent;
	}

    public void setResponseBytes(InputStream responseBytes) {this.responseBytes = responseBytes;}

	/**
	 * Gets all response headers, keyed case-insensitively. Never null.
	 *
	 * @return the response headers
	 */
	public Map<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	/**
	 * Sets the response headers. Keys are re-indexed case-insensitively.
	 *
	 * @param responseHeaders the response headers
	 */
	public void setResponseHeaders(Map<String, String> responseHeaders) {
		Map<String, String> headers = newHeaderMap();
		if (responseHeaders != null) {
			headers.putAll(responseHeaders);
		}
		this.responseHeaders = headers;
	}

	/**
	 * Sets the response headers from an Apache HttpResponse. Where a header is repeated, the last
	 * value received is retained.
	 *
	 * @param headers the headers as returned by HttpResponse#getAllHeaders
	 */
	public void setResponseHeaders(Header[] headers) {
		Map<String, String> headerMap = newHeaderMap();
		if (headers != null) {
			for (Header header : headers) {
				if (header != null && header.getName() != null) {
					headerMap.put(header.getName(), header.getValue());
				}
			}
		}
		this.responseHeaders = headerMap;
	}

	/**
	 * Sets the response headers from an HttpURLConnection. Where a header carries multiple values,
	 * the last one is retained.
	 *
	 * <p>Named separately from {@link #setResponseHeaders(Map)} because both would erase to the same
	 * signature.
	 *
	 * @param headerFields the header fields as returned by HttpURLConnection#getHeaderFields
	 */
	public void setResponseHeaderFields(Map<String, List<String>> headerFields) {
		Map<String, String> headerMap = newHeaderMap();
		if (headerFields != null) {
			for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
				// HttpURLConnection returns the status line under a null key
				if (entry.getKey() == null) {
					continue;
				}
				List<String> values = entry.getValue();
				if (values != null && !values.isEmpty()) {
					headerMap.put(entry.getKey(), values.get(values.size() - 1));
				}
			}
		}
		this.responseHeaders = headerMap;
	}

	/**
	 * Creates an empty header map with case-insensitive keys. Header names are case-insensitive per
	 * RFC 7230, and HTTP/2 lowercases them, so callers must not have to guess the casing.
	 *
	 * @return an empty case-insensitive map
	 */
	private static Map<String, String> newHeaderMap() {
		return new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
	}

}
