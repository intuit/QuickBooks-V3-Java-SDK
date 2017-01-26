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
package com.intuit.ipp.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.util.Logger;

public class DeflateCompressorTest {

	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	@Test
	public void testCompressAndDecompress()  throws CompressionException {
		String data = "Hello World!";
		DeflateCompressor compressor = new DeflateCompressor();
		byte[] compressed = compressor.compress(data, null);
		LOG.debug("Deflate compression : " + compressed);
		
		String decompressed = new String(((ByteArrayOutputStream)compressor.decompress(new ByteArrayInputStream(compressed))).toByteArray());
		LOG.debug("Deflate decompression : " + decompressed);
		
		Assert.assertEquals(data, decompressed, "DeflateCompressor : Both the original data and compressed then decompressed data are not same.");
	}
	
	@Test
	public void testCompress() throws CompressionException {
		String data = "Hello World!";
		DeflateCompressor compressor = new DeflateCompressor();
		byte[] compressed = compressor.compress(data, null);
		Assert.assertNotEquals(data, compressed, "DeflateCompressor : given data did not compress.");
	}
	
	@Test
	public void testCompress_null() throws CompressionException {
		String data = null;
		DeflateCompressor compressor = new DeflateCompressor();
		byte[] compressed = compressor.compress(data, null);
		Assert.assertNull(compressed);
	}
	
	@Test
	public void testDecompress() throws CompressionException {
		String data = "Hello World!";
		DeflateCompressor compressor = new DeflateCompressor();
		byte[] compressed = compressor.compress(data, null);
		String decompressed = new String(((ByteArrayOutputStream)compressor.decompress(new ByteArrayInputStream(compressed))).toByteArray());
		Assert.assertEquals(data, decompressed, "DeflateCompressor : given data did not decompress.");
	}
	
	@Test
	public void testDecompress_invalid() {
		boolean isException = false;
		String data = "data";
		try {
			DeflateCompressor compressor = new DeflateCompressor();
			compressor.decompress(new ByteArrayInputStream(data.getBytes()));
		} catch (CompressionException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
}
