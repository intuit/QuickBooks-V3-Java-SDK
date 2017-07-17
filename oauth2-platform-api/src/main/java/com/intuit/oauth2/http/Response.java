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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;

import com.intuit.oauth2.exception.ConnectionException;
import com.intuit.oauth2.utils.LoggerImpl;

/**
 * Class to hold the http response attributes
 * 
 * @author dderose
 *
 */
public class Response {
	
	private final InputStream stream;
    private final int statusCode;
    private String content;
    
    private static final Logger logger = LoggerImpl.getInstance();
    
	public Response(final InputStream stream, final int statusCode) {
        this.stream = stream;
        this.statusCode = statusCode;
    }
	
    /**
     * Returns the json content from http response
     * 
     * @return
     * @throws ConnectionException
     */
    public String getContent() throws ConnectionException {
    	
    	logger.debug("Enter Response::getContent");	
    	
    	if (content != null) {
    		logger.debug("content already available ");
            return content;
        }

    	BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
			    result.append(line);
			}
		} catch (IOException e) {
			logger.error("Exception while retrieving content", e);
            throw new ConnectionException(e.getMessage());
		}
		content = result.toString();
		logger.debug("End Response::getContent");	
		return content;
        
    }
    
    /**
     * Returns the http status code
     * 
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

	/**
	 * Returns the response data as stream
	 * 
	 * @return
	 */
	public InputStream getStream() {
		return stream;
	}
    
    
    

}
