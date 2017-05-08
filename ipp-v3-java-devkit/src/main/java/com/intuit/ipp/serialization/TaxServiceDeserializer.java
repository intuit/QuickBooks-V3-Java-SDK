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
package com.intuit.ipp.serialization;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.DeserializationFeature;

import com.intuit.ipp.data.Fault;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.TaxRateApplicableOnEnum;
import com.intuit.ipp.data.TaxRateDetails;
import com.intuit.ipp.data.TaxService;

public class TaxServiceDeserializer extends JsonDeserializer<TaxService>{
	
	
	/**
	 * variable FAULT
	 */
	private static final String FAULT = "Fault";
	

	
	
	/**
	 * {@inheritDoc}}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public TaxService deserialize(JsonParser jp, DeserializationContext desContext) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		TaxService taxService = new TaxService();

		//Make the mapper JAXB annotations aware
				AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
				AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
				AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
				mapper.setAnnotationIntrospector(pair);
				
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		//mapper.setPropertyNamingStrategy(PascalCaseStrategy);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Iterate over the field names
		Iterator<String> ite = jn.fieldNames();
		
		//Create the QueryResponse to be returned
		IntuitResponse qr = new IntuitResponse();
		
		//List to store taxrateDetails
		List<TaxRateDetails> taxRateDetailsList = new ArrayList<TaxRateDetails>();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.treeToValue(jn.get(FAULT), Fault.class));
				taxService.setFault(mapper.treeToValue(jn.get(FAULT), Fault.class));
				continue;
			} else if(key.equalsIgnoreCase("TaxCode")){
				taxService.setTaxCode(mapper.treeToValue(jn.get(key),String.class));
			} else if(key.equalsIgnoreCase("TaxCodeId"))
			{
				taxService.setTaxCodeId(mapper.treeToValue(jn.get(key),String.class));
			} else if(key.equalsIgnoreCase("TaxRateDetails"))
			{
			//add a loop to read all tax rate details
				JsonNode jn1 = jn.get(key);
				if (jn1.isArray()) {
					Iterator<JsonNode> iteJson = jn1.iterator();
					while (iteJson.hasNext()) {
						JsonNode jn2 = iteJson.next();
						
						TaxRateDetails taxRateDetails = getTaxRateDetails(jn2); //mapper.readValue(jn2.get(key),TaxRateDetails.class);
						taxRateDetailsList.add(taxRateDetails);
					}
				}
				
				taxService.setTaxRateDetails(taxRateDetailsList);
				}
}
		return taxService;
	}
	
	private TaxRateDetails getTaxRateDetails(JsonNode jn) throws IOException
	{
		TaxRateDetails taxRateDetails = new TaxRateDetails();
		
		ObjectMapper mapper = new ObjectMapper();
		
		//Make the mapper JAXB annotations aware
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = new AnnotationIntrospectorPair(primary, secondary);
		mapper.setAnnotationIntrospector(pair);
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Iterator<String> ite = jn.fieldNames();
		
		while (ite.hasNext()) {
			String key = ite.next();
		
		if(key.equalsIgnoreCase("TaxRateName")){
		taxRateDetails.setTaxRateName(mapper.treeToValue(jn.get(key),String.class));
		} else if (key.equalsIgnoreCase("RateValue"))
		{
		taxRateDetails.setRateValue(mapper.treeToValue(jn.get(key),BigDecimal.class));
		}
		else if (key.equalsIgnoreCase("TaxAgencyId"))
		{
		taxRateDetails.setTaxAgencyId(mapper.treeToValue(jn.get(key),String.class));
		} else if (key.equalsIgnoreCase("TaxApplicableOn"))
		{
		taxRateDetails.setTaxApplicableOn(mapper.treeToValue(jn.get(key),TaxRateApplicableOnEnum.class));
		} else if (key.equalsIgnoreCase("TaxRateId"))
		{
		taxRateDetails.setTaxRateId(mapper.treeToValue(jn.get(key),String.class));
		}
		}
		return taxRateDetails;
	}
}