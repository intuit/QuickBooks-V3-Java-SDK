package com.intuit.ipp.interceptors;

import java.io.InputStream;

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
	
}
