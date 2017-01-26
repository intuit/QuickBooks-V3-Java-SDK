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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.IntuitBatchRequest;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.data.Purchase;
import com.intuit.ipp.data.GlobalTaxCalculationEnum;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.SerializationException;

public class XMLSerializerTest {

	XMLSerializer xmlobj;

	@BeforeClass
	public void setUP() {
		xmlobj = new XMLSerializer();
	}
	
	@Test
	public void testSerialize() throws Exception {
		Customer c = new Customer();
		c.setCompanyName("Aditi");
		String str = xmlobj.serialize(getIntuitObject(c));

		Assert.assertEquals(str, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
				+ "<Customer xmlns=\"http://schema.intuit.com/finance/v3\">\n" + "    <CompanyName>Aditi</CompanyName>\n" + "</Customer>\n");
	}

	@Test
	public void testSerialize_null() throws Exception {
		String str = xmlobj.serialize(null);
		Assert.assertNull(str);
	}
	
	@Test
	public void testDeserialize() throws Exception {
		String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><IntuitResponse xmlns=\"http://schema.intuit.com/finance/v3\" time=\"2012-07-17T07:16:29.618Z\"><Customer status=\"Pending\"><Id>NG:2270532</Id><SyncToken>2</SyncToken><MetaData><CreateTime>2012-07-17T07:16:25Z</CreateTime><LastUpdatedTime>2012-07-17T07:16:30Z</LastUpdatedTime></MetaData><Organization>false</Organization><CompanyName>qualis</CompanyName><DisplayName>RamSeths</DisplayName><Active>false</Active><ContactName>leostorys</ContactName><AltContactName>krishna</AltContactName><Job>false</Job><AcctNum>7896</AcctNum><CurrencyRef name=\"US Dollar\"/></Customer></IntuitResponse>";
		IntuitResponse intuitResponse = (IntuitResponse) xmlobj.deserialize(xmlResponse, IntuitResponse.class);
		Customer c = (Customer) intuitResponse.getIntuitObject().getValue();
		Assert.assertEquals("qualis", c.getCompanyName());
	}

	@Test
	public void testDeserialize_BatchResponse() throws Exception {
		String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><IntuitResponse xmlns=\"http://schema.intuit.com/finance/v3\" time=\"2012-08-14T05:43:59.649Z\"><BatchItemResponse bId=\"12\"><Customer status=\"Pending\"><Id>NG:2285964</Id><SyncToken>204</SyncToken><MetaData><CreateTime>2012-07-23T11:17:56Z</CreateTime><LastUpdatedTime>2012-08-14T05:43:59Z</LastUpdatedTime></MetaData><Organization>false</Organization><CompanyName>Company Name 34</CompanyName><DisplayName>L 34</DisplayName><Active>false</Active><Job>false</Job><OpenBalanceDate>2012-07-23T11:17:15Z</OpenBalanceDate><CurrencyRef name=\"US Dollar\"/></Customer></BatchItemResponse><BatchItemResponse bId=\"13\"><QueryResponse maxResults=\"1\" startPosition=\"1\"><Customer><Id>NG:2293936</Id><DisplayName>0588e717-4c0f-4fb3-8e7c-7</DisplayName></Customer></QueryResponse></BatchItemResponse><BatchItemResponse bId=\"14\"><Fault type=\"Validation\"><Error code=\"500\"><Message>Unsupported operation requested</Message></Error></Fault></BatchItemResponse></IntuitResponse>";
		IntuitResponse intuitResponse = (IntuitResponse) xmlobj.deserialize(xmlResponse, IntuitResponse.class);
		
		List<BatchItemResponse> batchItemResponses = intuitResponse.getBatchItemResponse();
		Assert.assertEquals(batchItemResponses.size(), 3);
	}

    @Test
    public void testDeserializeBalance() throws Exception {
        String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><IntuitResponse xmlns=\"http://schema.intuit.com/finance/v3\" time=\"2012-07-17T07:16:29.618Z\"><Customer status=\"Pending\"><Id>NG:2270532</Id><SyncToken>2</SyncToken><MetaData><CreateTime>2012-07-17T07:16:25Z</CreateTime><LastUpdatedTime>2012-07-17T07:16:30Z</LastUpdatedTime></MetaData><Organization>false</Organization><DisplayName>RamSeths</DisplayName><Active>false</Active><AcctNum>7896</AcctNum><CurrencyRef name=\"US Dollar\"/><Balance>100055.00</Balance></Customer></IntuitResponse>";
        IntuitResponse intuitResponse = (IntuitResponse) xmlobj.deserialize(xmlResponse, IntuitResponse.class);
        Customer c = (Customer) intuitResponse.getIntuitObject().getValue();
        Assert.assertEquals(new BigDecimal("100055.00"), c.getBalance());
    }

    @Test
    public void testDeserialize_GlobalTaxCalculation() throws Exception {
        String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><IntuitResponse xmlns=\"http://schema.intuit.com/finance/v3\" time=\"2015-07-01T11:56:17.666-07:00\"> <Purchase domain=\"QBO\" sparse=\"false\"> <Id>171</Id> <SyncToken>0</SyncToken> <MetaData> <CreateTime>2015-07-01T11:56:14-07:00</CreateTime> <LastUpdatedTime>2015-07-01T11:56:14-07:00</LastUpdatedTime> </MetaData> <TxnDate>2015-07-02</TxnDate> <CurrencyRef name=\"Australian Dollar\">AUD</CurrencyRef> <ExchangeRate>1</ExchangeRate> <Line> <Id>1</Id> <Description>some line item</Description> <Amount>7.50</Amount> <DetailType>AccountBasedExpenseLineDetail</DetailType> <AccountBasedExpenseLineDetail> <AccountRef name=\"Amortisation (and depreciation) expense\">8</AccountRef> <BillableStatus>NotBillable</BillableStatus> <TaxCodeRef>NON</TaxCodeRef> </AccountBasedExpenseLineDetail> </Line> <Line> <Id>2</Id> <Description>some line item</Description> <Amount>7.50</Amount> <DetailType>AccountBasedExpenseLineDetail</DetailType> <AccountBasedExpenseLineDetail> <AccountRef name=\"Amortisation (and depreciation) expense\">8</AccountRef> <BillableStatus>NotBillable</BillableStatus> <TaxCodeRef>NON</TaxCodeRef> </AccountBasedExpenseLineDetail> </Line> <AccountRef name=\"Cash and cash equivalents\">13</AccountRef> <PaymentType>Check</PaymentType> <TotalAmt>15.00</TotalAmt> <PrintStatus>NotSet</PrintStatus> <GlobalTaxCalculation>NotApplicable</GlobalTaxCalculation> <PurchaseEx> <NameValue> <Name>TxnType</Name> <Value>3</Value> </NameValue> </PurchaseEx> </Purchase> </IntuitResponse>";
        IntuitResponse intuitResponse = (IntuitResponse) xmlobj.deserialize(xmlResponse, IntuitResponse.class);

        Purchase p = (Purchase) intuitResponse.getIntuitObject().getValue();
        Assert.assertEquals(p.getGlobalTaxCalculation(), GlobalTaxCalculationEnum.NOT_APPLICABLE);
    }
	
	@Test
	public void testSerialize_BatchRequest() throws SerializationException {
		IntuitBatchRequest batchRequest = new IntuitBatchRequest();
		List<BatchItemRequest> list = new ArrayList<BatchItemRequest>();

		Customer customerIn = new Customer();
		customerIn.setId("NG:2285964");
		customerIn.setSparse(true);
		customerIn.setDisplayName("updated");

		BatchItemRequest batchItemRequest = new BatchItemRequest();
		batchItemRequest.setBId("123");
		batchItemRequest.setOperation(OperationEnum.UPDATE);
		batchItemRequest.setIntuitObject(getIntuitObject(customerIn));

		list.add(batchItemRequest);
		batchRequest.setBatchItemRequest(list);

		String data = new XMLSerializer().serialize(getSerializableRequestObject(batchRequest));
		Assert.assertNotNull(data);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> JAXBElement<? extends IntuitEntity> getIntuitObject(T entity) {
		Class<?> objectClass = entity.getClass();
		String methodName = "create".concat(objectClass.getSimpleName());
		ObjectFactory objectEntity = new ObjectFactory();
		Class<?> objectEntityClass = objectEntity.getClass();

		Method method = null;
		try {
			method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
		} catch (Exception e) {
			//log.error("Exception while prepare the method signature using reflection to generate JAXBElement", e);
		}
		JAXBElement<? extends IntuitEntity> jaxbElement = null;
		try {
			jaxbElement = (JAXBElement<? extends IntuitEntity>) method.invoke(objectEntity, entity);
		} catch (Exception e) {
			//log.error("Exception while invoking the method using reflection to generate JAXBElement", e);
		}
		
		return jaxbElement;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Object> Object getSerializableRequestObject(T object) {
		Class<?> objectClass = object.getClass();
		String methodName = "create".concat(objectClass.getSimpleName());
		ObjectFactory objectEntity = new ObjectFactory();
		Class<?> objectEntityClass = objectEntity.getClass();

		Method method = null;
		try {
			method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
		} catch (Exception e) {
			//LOG.error("Exception while prepare the method signature using reflection to generate JAXBElement", e);
		}
		JAXBElement<? extends Object> jaxbElement = null;
		try {
			jaxbElement = (JAXBElement<? extends Object>) method.invoke(objectEntity, object);
		} catch (Exception e) {
			//LOG.error("Exception while invoking the method using reflection to generate JAXBElement", e);
		}
		
		return jaxbElement;
	}
}
