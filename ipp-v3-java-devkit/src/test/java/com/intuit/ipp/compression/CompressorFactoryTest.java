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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.CompressionException;

public class CompressorFactoryTest {

	@Test
	public void testGetCompressor_gzip() throws CompressionException {
		ICompressor compressor = CompressorFactory.getCompressor("gzip");
		Assert.assertTrue(compressor instanceof GZIPCompressor, "Object compressor is not instance of GZIPCompressor");
	}
	
	@Test
	public void testGetCompressor_deflate() throws CompressionException {
		ICompressor compressor = CompressorFactory.getCompressor("deflate");
		Assert.assertTrue(compressor instanceof DeflateCompressor, "Object compressor is not instance of DeflateCompressor");
	}

	@Test
	public void testGetCompressor_others() {
		boolean isException = false;
		try {
			CompressorFactory.getCompressor("others");
		} catch (CompressionException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test
	public void testGetCompressor_null() {
		boolean isException = false;
		try {
			CompressorFactory.getCompressor(null);
		} catch (CompressionException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test
	public void testIsValidCompressFormat_gzip() throws CompressionException {
		boolean isValid = CompressorFactory.isValidCompressFormat("gzip");
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void testIsValidCompressFormat_deflate() throws CompressionException {
		boolean isValid = CompressorFactory.isValidCompressFormat("deflate");
		Assert.assertTrue(isValid);
	}
	
	@Test
	public void testIsValidCompressFormat_others() {
		boolean isValid = true;
		try {
			isValid = CompressorFactory.isValidCompressFormat("others");
		} catch (CompressionException e) {
			isValid = false;
		}
		
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testIsValidCompressFormat_null() {
		boolean isValid = true;
		try {
			isValid = CompressorFactory.isValidCompressFormat(null);
		} catch (CompressionException e) {
			isValid = false;
		}
		
		Assert.assertFalse(isValid);
	}
	
	@Test
	public void testCompressorFactory() {
		CompressorFactory factory = CompressorFactory.getInstance();
		Assert.assertNotNull(factory);
	}
}
