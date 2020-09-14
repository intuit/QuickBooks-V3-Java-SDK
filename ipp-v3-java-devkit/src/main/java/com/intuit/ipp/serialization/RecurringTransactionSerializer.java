/*******************************************************************************
 * Copyright (c) 2020 Intuit
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
package com.intuit.ipp.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.intuit.ipp.data.RecurringTransaction;
import com.intuit.ipp.exception.SerializationException;
import com.intuit.ipp.util.Logger;

/**
 * Custom Json serializer class to handle RecurringTransaction while marshalling
 * 
 */
/**
 * @author dderose
 *
 */
public class RecurringTransactionSerializer extends JsonSerializer<RecurringTransaction> {

	/**
	 * logger instance
	 */
	private static final org.slf4j.Logger LOG = Logger.getLogger();



	@Override
	public void serialize(RecurringTransaction recurringTransaction, JsonGenerator jgen, SerializerProvider provider) throws IOException {

		jgen.writeStartObject();

		// for IntuitObject
		if (recurringTransaction.getIntuitObject() != null) {
			jgen.writeFieldName(recurringTransaction.getIntuitObject().getValue().getClass().getSimpleName());
			try {
				jgen.writeNumber(new JSONSerializer().serialize(recurringTransaction.getIntuitObject()));
			} catch (SerializationException e) {
				LOG.error("SerializationException while generating Json for IntuitObject in RecurringTransactionSerializer.", e);
				throw new IOException(e);
			}
		}

		

		jgen.writeEndObject();
	}




}
