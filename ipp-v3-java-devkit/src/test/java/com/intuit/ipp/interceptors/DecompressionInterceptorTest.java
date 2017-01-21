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