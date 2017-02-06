package com.intuit.ipp.serialization;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

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
				AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
				mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
				
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		//mapper.setPropertyNamingStrategy(PascalCaseStrategy);

		//Read the QueryResponse as a tree
		JsonNode jn = jp.readValueAsTree();

		//Iterate over the field names
		Iterator<String> ite = jn.getFieldNames();
		
		//Create the QueryResponse to be returned
		IntuitResponse qr = new IntuitResponse();
		
		//List to store taxrateDetails
		List<TaxRateDetails> taxRateDetailsList = new ArrayList<TaxRateDetails>();

		while (ite.hasNext()) {
			String key = ite.next();

			//Attributes
			if (key.equalsIgnoreCase(FAULT)) {
				qr.setFault(mapper.readValue(jn.get(FAULT), Fault.class));
				taxService.setFault(mapper.readValue(jn.get(FAULT), Fault.class));
				continue;
			} else if(key.equalsIgnoreCase("TaxCode")){
				taxService.setTaxCode(mapper.readValue(jn.get(key),String.class));
			} else if(key.equalsIgnoreCase("TaxCodeId"))
			{
				taxService.setTaxCodeId(mapper.readValue(jn.get(key),String.class));
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
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
		mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
		
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Iterator<String> ite = jn.getFieldNames();
		
		while (ite.hasNext()) {
			String key = ite.next();
		
		if(key.equalsIgnoreCase("TaxRateName")){
		taxRateDetails.setTaxRateName(mapper.readValue(jn.get(key),String.class));
		} else if (key.equalsIgnoreCase("RateValue"))
		{
		taxRateDetails.setRateValue(mapper.readValue(jn.get(key),BigDecimal.class));
		}
		else if (key.equalsIgnoreCase("TaxAgencyId"))
		{
		taxRateDetails.setTaxAgencyId(mapper.readValue(jn.get(key),String.class));
		} else if (key.equalsIgnoreCase("TaxApplicableOn"))
		{
		taxRateDetails.setTaxApplicableOn(mapper.readValue(jn.get(key),TaxRateApplicableOnEnum.class));
		} else if (key.equalsIgnoreCase("TaxRateId"))
		{
		taxRateDetails.setTaxRateId(mapper.readValue(jn.get(key),String.class));
		}
		}
		return taxRateDetails;
	}
}