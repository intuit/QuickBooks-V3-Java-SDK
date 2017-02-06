package com.intuit.ipp.interceptors;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intuit.ipp.compression.CompressorFactory;
import com.intuit.ipp.compression.ICompressor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to decompress the HTTP response
 *
 */
public class DecompressionInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {
		LOG.debug("Enter DecompressInterceptor...");

		ResponseElements responseElements = intuitMessage.getResponseElements();
		
		// get the Header to check whether it has content-encoding.
		//Header encodingHeader = responseElements.getEncodingHeader();
		String contentEncoding = responseElements.getEncodingHeader();
		String decompressedData = null;
		boolean isCompressionEnabled = false;
		if (contentEncoding != null) {
			// if content-encoding exists then decompress the response data
			//String contentEncoding = encodingHeader.getValue();
			if (StringUtils.hasText(contentEncoding)) {
				isCompressionEnabled = true;
				LOG.info("compression format : " + contentEncoding);
				LOG.debug("compression format : " + contentEncoding);

				// get the compressor to decompress the response data
				ICompressor compressor = CompressorFactory.getCompressor(contentEncoding);
				try {
                    ByteArrayOutputStream bytes =  (ByteArrayOutputStream)compressor.decompress(responseElements.getResponseContent());
                    responseElements.setResponseBytes( new ByteArrayInputStream(bytes.toByteArray()) );
                    decompressedData = new String(bytes.toByteArray());
				} catch (IllegalStateException e) {
					LOG.error("IllegalStateException while get the content from HttpResponse.", e);
					throw new FMSException(e);
				} catch (Exception e) {
					LOG.error("IOException in DecompressInterceptor.", e);
					throw new FMSException(e);
				}
			}
		}
		
		if (!isCompressionEnabled) {
			// if content-encoding or compression format does not exist 
			BufferedReader br = null;
			String readLine = null;
			StringBuilder responseBody = new StringBuilder();
			try {
				br = new BufferedReader(new InputStreamReader(responseElements.getResponseContent()));

				//get the response body received from socket connection
				while (((readLine = br.readLine()) != null)) {
					responseBody.append(readLine).append(System.getProperty("line.separator"));
				}
			} catch (IllegalStateException e) {
				LOG.error("IllegalStateException while get the content from HttpResponse.", e);
				throw new FMSException(e);
			} catch (Exception e) {
				LOG.error("IOException in DecompressInterceptor.", e);
				throw new FMSException(e);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception e) {
						LOG.warn("Unable to close BufferedReader", e);
					}
				}
			}

            try {
                responseElements.getResponseContent().reset();
            }catch (IOException ex) {
                LOG.warn("Unable to reset ResponseContent for bytes without compression", ex);
            }

			decompressedData = responseBody.toString();
            responseElements.setResponseBytes( responseElements.getResponseContent() );

		}

		LOG.debug("Decompressed Response Body : " + decompressedData);
		LOG.debug("Exit DecompressInterceptor.");
		responseElements.setDecompressedData(decompressedData);
	}

}
