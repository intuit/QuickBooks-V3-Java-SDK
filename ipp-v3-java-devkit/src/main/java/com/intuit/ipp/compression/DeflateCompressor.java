package com.intuit.ipp.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import com.intuit.ipp.exception.CompressionException;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Class to compress/decompress the given data using Deflate compression algorithm
 *
 */
public class DeflateCompressor implements ICompressor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable LENGTH_256
	 */
	private static final int LENGTH_256 = 256;
	
	/**
	 * {@inheritDoc}}
	 */
	public byte[] compress(final String data, byte[] uploadFile) throws CompressionException {

		if (!StringUtils.hasText(data)) {
			return null;
		}

		ByteArrayOutputStream baos = null;
		OutputStream deflater = null;
		byte[] compressedData = null;
		try {
			baos = new ByteArrayOutputStream();
			deflater = new DeflaterOutputStream(baos);
			deflater.write(data.getBytes());
			if (uploadFile != null) {
				deflater.write(uploadFile);
			}
			deflater.close();
			compressedData = baos.toByteArray();
			return compressedData;
		} catch (IOException ioe) {
			throw new CompressionException("IOException while compress the data using Deflate compression.", ioe);
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
			InputStream inflater = new InflaterInputStream(in);
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
			throw new CompressionException("IOException while decompress the data using Deflate compression.", ioe);
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
