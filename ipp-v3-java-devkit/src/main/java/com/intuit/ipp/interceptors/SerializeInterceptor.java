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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.MethodType;
import com.intuit.ipp.net.OperationType;
import com.intuit.ipp.serialization.IEntitySerializer;
import com.intuit.ipp.serialization.SerializerFactory;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to serialize the input data 
 *
 */
public class SerializeInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	/**
	 * variable imageMimes contains mime types of image
	 */
	private static final String[] IMAGE_MIMES = {"jpg", "jpeg", "tif", "tiff", "gif", "png", "bmp"};
	
	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {
		
		LOG.debug("Enter SerializeInterceptor...");
		String serializedData = null;
		RequestElements requestElements = intuitMessage.getRequestElements();
		String methodType = requestElements.getRequestParameters().get(RequestElements.REQ_PARAM_METHOD_TYPE);
		
		// if POST then do the serialization
		if (methodType.equals(MethodType.POST.toString())) {
			String serializeFormat = getSerializationRequestFormat();
			LOG.info("serialization format : " + serializeFormat);

			if (StringUtils.hasText(requestElements.getPostString())) {
				// setting the content string so that compressor will use this
				serializedData = requestElements.getPostString();
			} else if (StringUtils.hasText(serializeFormat)) {
				IEntitySerializer serializer = SerializerFactory.getSerializer(serializeFormat);
				serializedData = serializer.serialize(requestElements.getObjectToSerialize());
				String action = requestElements.getAction();
				if (action != null && action.equals(OperationType.UPLOAD.toString())) {
					UploadRequestElements uploadRequestElements = requestElements.getUploadRequestElements();
					StringBuilder input = new StringBuilder();
					// constructs the boundary
					input.append(uploadRequestElements.getBoundaryForEntity()).append(serializedData);
					input.append(uploadRequestElements.getBoundaryForContent());
					serializedData = input.toString();
					
					requestElements.setUploadFile(getUploadFileContent(requestElements));
				}
			} else {
				// TODO
				LOG.debug("Serialization doesn't applicable for this request");
			}
		} else {
			// TODO
			LOG.debug("Serialization doesn't applicable for the GET request");
		}
		LOG.debug("Exit SerializeInterceptor.");
		requestElements.setSerializedData(serializedData);
	}

    /**
     * Returns serialization format from config for sending objects
     * @return
     */
    protected String getSerializationRequestFormat() {
        return Config.getProperty(Config.SERIALIZATION_REQUEST_FORMAT);
    }

    /**
	 * Method to get the file content of the upload file based on the mime type
	 * 
	 * @param requestElements the request elements
	 * @return byte[] the upload file content 
	 * @throws FMSException
	 */
	private byte[] getUploadFileContent(RequestElements requestElements) throws FMSException {
		Attachable attachable = (Attachable) requestElements.getEntity();
		InputStream docContent = requestElements.getUploadRequestElements().getDocContent();
		
		// gets the mime value form the filename
		String mime = getMime(attachable.getFileName(), ".");
		// if null then gets the mime value from content-type of the file
		mime = (mime != null) ? mime : getMime(attachable.getContentType(), "/");
		if (isImageType(mime)) {
			return getImageContent(docContent, mime);
		} else {
			return getContent(docContent);
		}
	}
	
	/**
	 * Method to return the byte[] value of the given file input stream
	 * 
	 * @param in the input stream
	 * @return byte[] the file content
	 * @throws FMSException
	 */
	private byte[] getContent(InputStream in) throws FMSException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] content = null;
		try {
			int nRead;
			byte[] data = new byte[1028];
			while ((nRead = in.read(data, 0, data.length)) != -1) {
			  baos.write(data, 0, nRead);
			}
			baos.flush();
			content = baos.toByteArray();
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		} finally {
			close(baos);
			close(in);
		}
		return content;
	}
	
	/**
	 * Method to return the byte[] value of the given image input stream
	 *  
	 * @param in the input stream
	 * @param mime the mime type of the file
	 * @return byte[] the file content
	 * @throws FMSException
	 */
	private byte[] getImageContent(InputStream in, String mime) throws FMSException {
		byte[] imageInByte = null;
		ByteArrayOutputStream baos = null;
		try {
			BufferedImage originalImage = ImageIO.read(in);
			baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, mime, baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		} finally {
			close(baos);
			close(in);
		}
		return imageInByte;
	}
	
	/**
	 * Method to validate whether the given mime is an image file
	 * 
	 * @param mime the mime type
	 * @return boolean returns true if the file is image
	 */
	private boolean isImageType(String mime) {
		if (StringUtils.hasText(mime)) {
			for (String imageMime : IMAGE_MIMES) {
				if (mime.equalsIgnoreCase(imageMime)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method get the mime value from the given file name
	 * @param name the filename
	 * @param delimiter the delimiter
	 * @return String the mime value 
	 */
	private String getMime(String name, String delimiter) {
		if (StringUtils.hasText(name)) {
			return name.substring(name.lastIndexOf(delimiter), name.length());
		}
		return null;
	}
	
	private void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				LOG.error("Exception while closing the InputStream.", e);
			}
		}
	}
	
	private void close(ByteArrayOutputStream baos) {
		if (baos != null) {
			try {
				baos.close();
			} catch (IOException e) {
				LOG.error("Exception while closing the ByteArrayOutputStream.", e);
			}
		}
	}
}
