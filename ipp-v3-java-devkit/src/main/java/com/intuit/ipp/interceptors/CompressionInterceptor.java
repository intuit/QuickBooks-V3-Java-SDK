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
import com.intuit.ipp.compression.ICompressor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to compress the input data
 *
 */
public class CompressionInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {
		
		LOG.debug("Enter CompressionInterceptor...");
		
		String serializedData = intuitMessage.getRequestElements().getSerializedData();
		
		if (StringUtils.hasText(serializedData)) {
			byte[] compressedData = null;
			
			String compressFormat = Config.getProperty(Config.COMPRESSION_REQUEST_FORMAT);
			LOG.info("compression format : " + compressFormat);
			
			byte[] uploadFile = intuitMessage.getRequestElements().getUploadFile();
			if (StringUtils.hasText(compressFormat)) {
				// compress the body data
				ICompressor compressor = CompressorFactory.getCompressor(compressFormat);
				compressedData = compressor.compress(serializedData, uploadFile);
			} else {
				if (uploadFile != null) {
					byte[] serializedDateByte = serializedData.getBytes();
					compressedData = new byte[serializedDateByte.length + uploadFile.length];
					// construct the new byte[] by concatenating serializedData and uploadFile data
					System.arraycopy(serializedDateByte, 0, compressedData, 0, serializedDateByte.length);
					System.arraycopy(uploadFile, 0, compressedData, serializedDateByte.length, uploadFile.length);
				} else {
					compressedData = serializedData.getBytes();
				}
			}
			
			// set the compressed data
			intuitMessage.getRequestElements().setCompressedData(compressedData);
		}
		
		LOG.debug("Exit CompressionInterceptor.");
	}

}
