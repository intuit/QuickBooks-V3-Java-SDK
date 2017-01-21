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
