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

import com.intuit.ipp.compression.CompressorFactory;
import com.intuit.ipp.compression.GZIPCompressor;
import com.intuit.ipp.util.Config;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

import static com.intuit.ipp.compression.CompressorFactory.DEFLATE_COMPRESS_FORMAT;


public class CompressionInterceptorTest {

    private String originalCompression;
    private CompressionInterceptor instance = new CompressionInterceptor();
    private IntuitMessage message;

    @BeforeClass
    public void beforeClass() {
        originalCompression = Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT);
    }

    @AfterClass
    public void afterClass() {
        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, originalCompression);
    }

    @BeforeMethod
    public void setUp() {
        message = new IntuitMessage();
    }

    @Test
    public void testNoContent() throws Exception  {
        String test = null;

        message.getRequestElements().setSerializedData(test);
        instance.execute(message);
        Assert.assertNull(message.getRequestElements().getCompressedData());
    }

    @Test
    public void testEmptyContent() throws Exception  {
        String test = "";

        message.getRequestElements().setSerializedData(test);
        instance.execute(message);
        Assert.assertNull(message.getRequestElements().getCompressedData());
    }

    @Test
    public void testNoCompressionFormatAndSerializedData() throws Exception  {
        String test = "test data";

        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");

        message.getRequestElements().setSerializedData(test);
        instance.execute(message);
        Assert.assertEquals(new String(message.getRequestElements().getCompressedData()), test);
    }

    @Test
    public void testNoCompressionFormatAndUploadFile() throws Exception  {
        String test = "a";

        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");

        message.getRequestElements().setSerializedData(test);
        message.getRequestElements().setUploadFile(new byte[]{1, 2, 3, 4, 5});
        instance.execute(message);
        Assert.assertEquals(message.getRequestElements().getCompressedData(), new byte[]{'a', 1, 2, 3, 4, 5});
    }

    @Test
    public void testCallsCompressorForGivenFormatAndSerializedData() throws Exception  {
        String test = "test data";

        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, DEFLATE_COMPRESS_FORMAT);

        message.getRequestElements().setSerializedData(test);
        instance.execute(message);
        byte[] expectedCompressedData = CompressorFactory.getCompressor(DEFLATE_COMPRESS_FORMAT).compress(test, new byte[]{});
        Assert.assertEquals(message.getRequestElements().getCompressedData(), expectedCompressedData);
    }

    @Test
    public void testCallsCompressorForGivenFormatAndUploadFile() throws Exception  {
        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, DEFLATE_COMPRESS_FORMAT);

        message.getRequestElements().setUploadFile(new byte[]{1, 2, 3, 4, 5});
        instance.execute(message);
        byte[] expectedCompressedData = CompressorFactory.getCompressor(DEFLATE_COMPRESS_FORMAT).compress(null, new byte[]{1, 2, 3, 4, 5});
        Assert.assertEquals(message.getRequestElements().getCompressedData(), expectedCompressedData);
    }

}