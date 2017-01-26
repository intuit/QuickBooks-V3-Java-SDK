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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Class to compress/decompress the given data using GZIP compression algorithm
 * 
 */
public class GZIPCompressor implements ICompressor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable LENGTH_256
	 */
	private static final int LENGTH_256 = 256;

	/** {@inheritDoc} */
	public byte[] compress(final String data, byte[] uploadFile) throws CompressionException {

		if (!StringUtils.hasText(data)) {
			return null;
		}

		ByteArrayOutputStream baos = null;
		OutputStream gzout = null;
		byte[] compressedData = null;
		try {
			baos = new ByteArrayOutputStream();
			gzout = new GZIPOutputStream(baos);
			gzout.write(data.getBytes());
			if (uploadFile != null) {
				gzout.write(uploadFile);
			}
			gzout.close();
			compressedData = baos.toByteArray();
			return compressedData;
		} catch (IOException ioe) {
			LOG.error("IOException while compress the data using GZIP compression.", ioe);
			throw new CompressionException(ioe);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					LOG.error("Unable to close ByteArrayOutputStream.");
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public OutputStream decompress(final InputStream in) throws CompressionException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream inflater = new GZIPInputStream(in);
			byte[] bbuf = new byte[LENGTH_256];
			while (true) {
				int r = inflater.read(bbuf);
				if (r < 0) {
					break;
				}
				baos.write(bbuf, 0, r);
			}
			return baos;
		} catch (IOException ioe) {
			LOG.error("IOException while decompress the data using GZIP compression.", ioe);
			throw new CompressionException(ioe);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					LOG.error("Unable to close ByteArrayOutputStream.");
				}
			}
		}
	}

}
