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

import java.io.InputStream;
import java.io.OutputStream;

import com.intuit.ipp.exception.CompressionException;

/**
 * Interface to have the compressor methods.
 * 
 */
public interface ICompressor {
	
	/**
	 * Method to compress the given data using the corresponding implementation algorithm.
	 * 
	 * @param data the input string
	 * @param uploadFile the content of the file to upload in InputStream
	 * @return byte[]
	 * @throws CompressionException the compression exception
	 */
	byte[] compress(final String data, byte[] uploadFile) throws CompressionException;
	
	/**
	 * Method to decompress the given data using the corresponding implementation algorithm.
	 * @param in the input stream
	 * @return String
	 * @throws CompressionException the compression exception
	 */
	OutputStream decompress(InputStream in) throws CompressionException;
	
}
