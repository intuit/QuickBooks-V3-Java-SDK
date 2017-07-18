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

import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.testng.annotations.Test;

import com.intuit.oauth2.exception.ConnectionException;

public class ResponseTest {
	
	
	
	@Test
    public void testGetStream() throws UnsupportedEncodingException, ConnectionException {
        
        String content = "frobozz";
        InputStream istream = new ByteArrayInputStream(content.getBytes("UTF-8"));
        Response response = new Response(istream, 200);
        assertEquals(istream, response.getStream());
        assertEquals(content, response.getContent());

    }

}
