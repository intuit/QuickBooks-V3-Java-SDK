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
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.testng.annotations.Test;

import com.intuit.oauth2.exception.ConnectionException;

public class ResponseTest {
	
	
	
	@Test
    public void testGetStream() throws IOException, ConnectionException {
        
        String content = "frobozz";
        byte[] bytes = content.getBytes("UTF-8");
        InputStream istream = new ByteArrayInputStream(bytes);
        String intuit_tid = "abcd-123-xyz";
        Response response = new Response(istream, 200, intuit_tid);
        assertEquals(bytes, response.getStream().readAllBytes());
        assertEquals(content, response.getContent());
        assertEquals(intuit_tid, response.getIntuit_tid());
    }

}
