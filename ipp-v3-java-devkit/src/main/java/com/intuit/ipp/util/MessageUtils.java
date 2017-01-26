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
package com.intuit.ipp.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.intuit.ipp.data.IntuitEntity;

/**
 * The MessageUtils helps to manage marshaling and unmarshaling of objects.
 * 
 */
public final class MessageUtils {

	/**
	 * constructor MessageUtils
	 */
	private MessageUtils() {
	}

	/**
	 * Inner class to enforce the proper initialization of the static variables
	 * in this utility class. This relies on the fact that inner classes are not
	 * loaded until referenced. See:
	 * http://en.wikipedia.org/wiki/Double-checked_locking
	 */
	private static class MessageUtilsHelper {
		
		/**
		 * variable privContext
		 */
		private static JAXBContext privContext = null;

		/**
		 * Create new or return existing JAXB context for IntuitEntity classes.
		 * 
		 * @return JAXBContext to Marshal or Unmarshal object
		 */
		public static JAXBContext getContext() throws JAXBException {
			if (privContext == null) {
				privContext = JAXBContext.newInstance(IntuitEntity.class.getPackage().getName());
			}
			return privContext;
		}
	}

	/**
	 * Create Marshaller from the JAXB context.
	 * 
	 * @return Marshaller
	 */
	public static Marshaller createMarshaller() throws JAXBException {
		Marshaller marshaller = MessageUtilsHelper.getContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return marshaller;
	}

	/**
	 * Create UnMarshaller from the JAXB context.
	 * 
	 * @return UnMarshaller
	 */
	public static Unmarshaller createUnmarshaller() throws JAXBException {
		return MessageUtilsHelper.getContext().createUnmarshaller();
	}

	/**
	 * return Gson Object
	 * 
	 * @return Gson
	 */
	public static Gson getGson() {
		return new Gson();
	}
}
