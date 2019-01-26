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

import com.intuit.ipp.core.Response;
import com.intuit.ipp.data.EntitlementsResponse;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.TaxService;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.serialization.IEntitySerializer;
import com.intuit.ipp.serialization.SerializerFactory;
import com.intuit.ipp.util.Logger;
import com.intuit.ipp.util.StringUtils;

/**
 * Interceptor to deserialize the Http response
 * 
 */
public class DeserializeInterceptor implements Interceptor {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(IntuitMessage intuitMessage) throws FMSException {

		LOG.debug("Enter DeserializeInterceptor...");
		Response response = null;

		ResponseElements responseElements = intuitMessage.getResponseElements();
		// get the Header to check whether it has content-type.
		//Header contentTypeHeader = responseElements.getContentTypeHeader();
		String contentType = responseElements.getContentTypeHeader();

		if (contentType != null) {
			//String contentType = contentTypeHeader.getValue();
			// get content-type alone. 
			// read only xml from application/xml

			String[] contentTypes = contentType.split(";");
			for (String contentTypePart : contentTypes) {
				if (contentTypePart.matches("(.*)/(.*)")) {
					String serializeFormat = contentTypePart.substring(contentTypePart.lastIndexOf("/") + 1, 
							contentTypePart.length());
					LOG.info("HttpResponse content-type (deserialization format) : " + serializeFormat);
					LOG.debug("HttpResponse content-type (deserialization format) : " + serializeFormat);

					if (StringUtils.hasText(serializeFormat)
							&& (serializeFormat.equalsIgnoreCase(ContentTypes.XML.name()) 
									|| serializeFormat.equalsIgnoreCase(ContentTypes.JSON.name()))) {
                        IEntitySerializer serializer = SerializerFactory.getSerializer(serializeFormat);
                        if (intuitMessage.getRequestElements().getObjectToSerialize() instanceof TaxService && serializeFormat.equalsIgnoreCase(ContentTypes.JSON.name())) {
                            response = serializer.deserialize(responseElements.getDecompressedData(), TaxService.class);
                        } else if (intuitMessage.isEntitlementService()) {
                        	if (StringUtils.hasText(responseElements.getDecompressedData())) {
                            response = serializer.deserializeEntitlements(responseElements.getDecompressedData(), EntitlementsResponse.class);
                        	} else {
                        		response = responseElements.getResponse();
                        	}
                        } else {
                            response = serializer.deserialize(responseElements.getDecompressedData(), IntuitResponse.class);
                        }
                    } else if (serializeFormat.equalsIgnoreCase(ContentTypes.PDF.name()) || serializeFormat.equalsIgnoreCase("plain") || serializeFormat.equalsIgnoreCase("octet-stream")) {
                        LOG.info("PDF or plain content has been received");
                        // do nothing, since we will use binary of the response as is

					} else {
						LOG.info("Invalid Content Type" + serializeFormat);
						LOG.debug("Decompressed Data" + responseElements.getDecompressedData());
						LOG.error("Decompressed Data" + responseElements.getDecompressedData());
						throw new FMSException(responseElements.getDecompressedData());
						
					}
				}
			}

		}

		responseElements.setResponse(response);

		LOG.debug("Exit DeserializeInterceptor.");
	}

}
