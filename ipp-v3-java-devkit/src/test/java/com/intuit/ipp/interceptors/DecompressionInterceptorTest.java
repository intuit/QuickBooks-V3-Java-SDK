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

import com.intuit.ipp.compression.GZIPCompressor;
import com.intuit.ipp.exception.FMSException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;


public class DecompressionInterceptorTest {

    private DecompressionInterceptor instance = new DecompressionInterceptor();
    private IntuitMessage message = new IntuitMessage();



    @Test
    public void testNoCompression() throws Exception  {

        String test = "some content";

        message.getResponseElements().setResponseContent(new ByteArrayInputStream(test.getBytes()));
        instance.execute(message);
        ByteArrayInputStream input =  (ByteArrayInputStream)message.getResponseElements().getResponseBytes();
        byte[] bytes = new byte[input.available()];
        input.read(bytes);
        Assert.assertEquals(bytes, test.getBytes());

    }


    @Test
    public void testWithCompression() throws Exception  {

        String test = "some content";
        GZIPCompressor compressor = new GZIPCompressor();
        byte[] compressed = compressor.compress(test, null);

        message.getResponseElements().setEncodingHeader("gzip");
        message.getResponseElements().setResponseContent(new ByteArrayInputStream(compressed));
        instance.execute(message);
        ByteArrayInputStream input =  (ByteArrayInputStream)message.getResponseElements().getResponseBytes();
        byte[] bytes = new byte[input.available()];
        input.read(bytes);
        Assert.assertEquals(bytes, test.getBytes());
        Assert.assertEquals(message.getResponseElements().getDecompressedData(),test);

    }


}