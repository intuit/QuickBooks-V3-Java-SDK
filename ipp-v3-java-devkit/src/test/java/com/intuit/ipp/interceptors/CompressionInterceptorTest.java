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
import com.intuit.ipp.compression.DeflateCompressor;
import com.intuit.ipp.compression.GZIPCompressor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Config;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CompressionInterceptorTest {

    private CompressionInterceptor instance;
    private IntuitMessage message;
    private String compressorConfigPropertyPriorTest;

    @BeforeTest
    public void GetCurrentCompressionProperty() {
        compressorConfigPropertyPriorTest = Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT);
    }

    @AfterTest
    public void ResetCompressionProperty(){
        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT,compressorConfigPropertyPriorTest);
    }

    @BeforeMethod
    public void InitTest() {
        // Fresh message and compressor to ensure tests runs are completely independent
        message = new IntuitMessage();
        instance = new CompressionInterceptor();
    }

    @Test
    public void CompressionNoFormatTest() throws FMSException {
        String testMessage = "Test No Format Serialized";

        // No Compression format test case
        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");

        message.getRequestElements().setSerializedData(testMessage);
        instance.execute(message);

        Assert.assertEquals(message.getRequestElements().getCompressedData(), testMessage.getBytes());
    }

    @Test
    public void CompressionNoFormatAndUploadFileTest() throws FMSException {
        String testMessage = "Test No Format and Upload Serialized";
        String testUploadFile = "Test Upload File";
        byte[] allBytes = (testMessage + testUploadFile).getBytes();

        // No Compression format test case
        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");

        message.getRequestElements().setSerializedData(testMessage);
        message.getRequestElements().setUploadFile(testUploadFile.getBytes());
        instance.execute(message);

        Assert.assertEquals(message.getRequestElements().getCompressedData(), allBytes);
    }

    @Test
    public void CompressionGZipFormatCompressionTest() throws FMSException {
        String testMessage = "Test Gzip Serialized";
        GZIPCompressor compressor = new GZIPCompressor();
        byte[] compressed = compressor.compress(testMessage, null);

        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, CompressorFactory.GZIP_COMPRESS_FORMAT);

        message.getRequestElements().setSerializedData(testMessage);
        instance.execute(message);

        Assert.assertEquals(message.getRequestElements().getCompressedData(), compressed);
    }

    @Test
    public void CompressionDeflateFormatCompressionTest() throws FMSException {
        String testMessage = "Test Deflate Serialized";
        DeflateCompressor compressor = new DeflateCompressor();
        byte[] compressed = compressor.compress(testMessage, null);

        Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, CompressorFactory.DEFLATE_COMPRESS_FORMAT);

        message.getRequestElements().setSerializedData(testMessage);
        instance.execute(message);

        Assert.assertEquals(message.getRequestElements().getCompressedData(), compressed);
    }
}
