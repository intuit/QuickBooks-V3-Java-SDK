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
package com.intuit.ipp.services;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.intuit.ipp.data.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.util.EntityCreator;

public class DataServiceTest {

	protected  Context context = null;
	
	protected DataService service = null;
	
	@BeforeClass
	public void setup() throws FMSException {
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		context = new Context(oauth,ippHelper.getQboAppToken(),ServiceType.QBO , ippHelper.getQboRealmID());
		service = new DataService(context);
		//EntityCreator.addCustomer(oauth, context);
	}
	
	@Test (enabled = false)
	public void testFindById() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());
		Customer customerOut = service.findById(customerIn);
		Assert.assertNotNull(customerOut);
	}
	
	@Test(enabled = false)
	public void testFindById_invalidId() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");
		Customer customerOut = service.findById(customerIn);
		Assert.assertNull(customerOut);
	}
	
	@Test (enabled = false)
	public void testAdd_duplicate() {
		boolean isException = false;
		Customer customerIn = new Customer();
		customerIn.setDisplayName(EntityCreator.customer.getDisplayName());
		customerIn.setCompanyName("Company Name Test101");
		Customer customerOut = null;
		try {
			customerOut = service.add(customerIn);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertNull(customerOut);
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testAdd() throws FMSException {
		String randomVal = UUID.randomUUID().toString().substring(0, 5);
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + randomVal);
		customerIn.setCompanyName("Company Name Test" + randomVal);
		Customer customerOut = service.add(customerIn);
		Assert.assertTrue(customerOut != null);
		Assert.assertEquals(customerIn.getDisplayName(), customerOut.getDisplayName());
	}
	
	@Test (enabled = false)
	public void testUpdate() throws FMSException {
		Customer customerIn = new Customer();
		Customer customerAdded = createCustomer();
		
		customerIn.setId(customerAdded.getId());
		customerIn.setSparse(true);
		customerIn.setSyncToken(customerAdded.getSyncToken());
		customerIn.setDisplayName(customerIn.getDisplayName() + Calendar.getInstance().getTimeInMillis());
		Customer customerOut = service.update(customerIn);
		Assert.assertEquals(customerOut.getDisplayName(), customerIn.getDisplayName());
	}
	
	@Test (enabled = false)
	public void testUpdate_invalidId() {
		boolean isException = false;
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");
		customerIn.setSparse(true);
		customerIn.setDisplayName("updated");
		Customer customerOut = null;
		try {
			customerOut = service.update(customerIn);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertNull(customerOut);
		Assert.assertTrue(isException);
	}
	
	@Test(enabled = false)
	public void testDelete() throws FMSException {
		Customer customerOut = createCustomer();
		customerOut = service.delete(customerOut);
		Assert.assertTrue(customerOut != null);
		Assert.assertEquals(customerOut.getStatus(), EntityStatusEnum.DELETED.value());
	}
	
	@Test (enabled = false)
	public void testDelete_invalidId() {
		boolean isException = false;
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");
		try {
			Customer customerOut = service.delete(customerIn);
			if (customerOut == null) {
				isException = true;
			}
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testVoidRequest() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());
		Customer customerOut = service.voidRequest(customerIn);
		Assert.assertTrue(customerOut != null);
		Assert.assertEquals(customerOut.getStatus(), EntityStatusEnum.VOIDED.value());
	}
	
	@Test(enabled = false)
	public void testVoidRequest_invalidId() {
		boolean isException = false;
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");
		try {
			service.voidRequest(customerIn);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testFindAll() throws FMSException {
		Customer customer = new Customer();
		List<Customer> customerList = service.findAll(customer);
		Assert.assertTrue(customerList != null && customerList.size()>0);
	}
	
	@Test (enabled = false)
	public void testExecuteQuery_get() throws FMSException {
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(EntityCreator.customer.getId())).generate();

		QueryResult queryResult = service.executeQuery(query);
		
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.getEntities().size(), 1);
		Assert.assertEquals(queryResult.getMaxResults().intValue(), 1);
		Assert.assertNotNull(queryResult.getStartPosition());
		Assert.assertNull(queryResult.getTotalCount());
	}
	
	@Test(enabled = false)
	public void testExecuteQuery_post() throws FMSException {
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(EntityCreator.customer.getId())).generate();
		String newQuery = "          ";
		for (int i = 1; i < 20; i++) {
			query = query + newQuery;
		}
		QueryResult queryResult = service.executeQuery(query);
		
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.getEntities().size(), 1);
		Assert.assertEquals(queryResult.getMaxResults().intValue(), 1);
		Assert.assertNotNull(queryResult.getStartPosition());
		Assert.assertNull(queryResult.getTotalCount());
	}
	
	@Test(enabled = false)
	public void testExecuteQuery_postCompression() throws FMSException {
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(EntityCreator.customer.getId())).generate();
		String newQuery = "          ";
		for (int i = 1; i < 20; i++) {
			query = query + newQuery;
		}
		try {
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");
			QueryResult queryResult = service.executeQuery(query);
			
			Assert.assertNotNull(queryResult);
			Assert.assertEquals(queryResult.getEntities().size(), 1);
			Assert.assertEquals(queryResult.getMaxResults().intValue(), 1);
			Assert.assertNotNull(queryResult.getStartPosition());
			Assert.assertNull(queryResult.getTotalCount());
		} finally {
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
		}
	}
	
	@Test(enabled = false)
	public void testExecuteQuery_invalidQuery() {
		String query = "select * from customer";
		boolean isException = false;
		try {
			service.executeQuery(query);
		} catch (FMSException e) {
			isException = true;
		}
		
		Assert.assertTrue(isException);
	}
	
	@Test(enabled = false)
	public void testExecuteBatch() throws FMSException {
		BatchOperation batchOperation = new BatchOperation();
		
		Customer customer = new Customer();
		customer.setId(EntityCreator.customer.getId());
		customer.setSparse(true);
		customer.setSyncToken(EntityCreator.customer.getSyncToken());
		customer.setDisplayName(customer.getDisplayName() + Calendar.getInstance().getTimeInMillis());
		batchOperation.addEntity(customer, OperationEnum.UPDATE, "12");
		
		Customer c = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(c.getId()), $(c.getDisplayName()))
				.where($(c.getId()).eq(EntityCreator.customer.getId())).generate();
		batchOperation.addQuery(query, "13");
		
		String reportQuery = "select * from Customer";
		batchOperation.addReportQuery(reportQuery, "14");
		
		service.executeBatch(batchOperation);
		
		Assert.assertEquals(batchOperation.getBIds().size(), 3);
	 	Assert.assertTrue(batchOperation.isEntity("12"));
		Assert.assertTrue(batchOperation.isQuery("13"));
		Assert.assertTrue(batchOperation.isFault("14"));
		
	}
	
	@Test (enabled = false)
	public void testExecuteBatch_Entity() throws FMSException {
		BatchOperation batchOperation = new BatchOperation();
		
		Customer customerIn = new Customer();
		Customer customerAdded = createCustomer();
		
		customerIn.setSparse(true);
		customerIn.setId(customerAdded.getId());
		customerIn.setSyncToken(customerAdded.getSyncToken());
		customerIn.setDisplayName(customerIn.getDisplayName() + Calendar.getInstance().getTimeInMillis());
		batchOperation.addEntity(customerIn, OperationEnum.UPDATE, "12");
		
		service.executeBatch(batchOperation);
		
		Assert.assertTrue(batchOperation.isEntity("12"));
		Assert.assertEquals(((Customer)batchOperation.getEntity("12")).getDisplayName(), customerIn.getDisplayName());
	}
	
	@Test (enabled = false)
	public void testExecuteBatch_Query() throws FMSException {
		BatchOperation batchOperation = new BatchOperation();
		
		Customer c = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(c.getId()), $(c.getDisplayName()))
				.where($(c.getId()).eq(EntityCreator.customer.getId())).generate();
		batchOperation.addQuery(query, "13");
		
		service.executeBatch(batchOperation);
		
		Assert.assertTrue(batchOperation.isQuery("13"));
		Assert.assertEquals(batchOperation.getQueryResponse("13").getEntities().size(), 1);
	}
	
	@Test(enabled = false)
	public void testExecuteBatch_invalidQuery() throws FMSException {
		BatchOperation batchOperation = new BatchOperation();
		
		String query = "select * from customer";
		batchOperation.addQuery(query, "13");
		
		service.executeBatch(batchOperation);
		
		Assert.assertNotNull(batchOperation.getFault("13"));
		Assert.assertTrue(batchOperation.getFault("13").getError().size() > 0);
	}
	
	@Test (enabled = false)
	public void testExecuteBatch_Report() throws FMSException {
		BatchOperation batchOperation = new BatchOperation();
		
		String reportQuery = "select * from Customer";
		batchOperation.addReportQuery(reportQuery, "14");
		
		service.executeBatch(batchOperation);
		
		Assert.assertTrue(batchOperation.isFault("14"));
		Assert.assertNull(batchOperation.getReport("14"));
		Assert.assertFalse(batchOperation.isReport("14"));
		Assert.assertTrue(batchOperation.getReportResult().isEmpty());
	}
	
	@Test (enabled = false)
	public void testFindById_incrementalRetry() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());
		
		Config.setProperty(Config.RETRY_MODE, "incremental");
		Config.setProperty(Config.RETRY_INCREMENTAL_COUNT, "3");
		Config.setProperty(Config.RETRY_INCREMENTAL_INCREMENT, "5");
		Config.setProperty(Config.RETRY_INCREMENTAL_INTERVAL, "30");
		
		Customer customerOut = service.findById(customerIn);
		Assert.assertNotNull(customerOut);
	}
	
	@Test (enabled = false)
	public void testFindById_exponentialRetry() throws FMSException {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());
		
		Config.setProperty(Config.RETRY_MODE, "exponential");
		Config.setProperty(Config.RETRY_EXPONENTIAL_COUNT, "3");
		Config.setProperty(Config.RETRY_EXPONENTIAL_DELTA_BACKOFF, "10");
		Config.setProperty(Config.RETRY_EXPONENTIAL_MAX_BACKOFF, "100");
		Config.setProperty(Config.RETRY_EXPONENTIAL_MIN_BACKOFF, "5");
		
		Customer customerOut = service.findById(customerIn);
		Assert.assertNotNull(customerOut);
	}
	
	@Test (enabled = false)
	public void testFindById_jsonDeserializer() throws FMSException {
		try {
			Customer customerIn = new Customer();
			customerIn.setId(EntityCreator.customer.getId());
			
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
			
			Customer customerOut = service.findById(customerIn);
			Assert.assertNotNull(customerOut);
		} finally {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
		}
	}
	
	@Test (enabled = false)
	public void testCDCQuery() throws FMSException, ParseException {
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Item());
		
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		List<CDCQueryResult> cdcQueryResults = service.executeCDCQuery(entities, changedSince);
		Assert.assertNotNull(cdcQueryResults);
		CDCQueryResult cdcQueryResult = cdcQueryResults.get(0);
		Assert.assertNull(cdcQueryResult.getFalut());
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		Assert.assertNotNull(queryResults);
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Customer customer = (Customer) customerEntities.get(0);
		Assert.assertNotNull(customer);
	}
	
	private Customer createCustomer() throws FMSException {
		String randomVal = UUID.randomUUID().toString().substring(0, 5);
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + randomVal);
		customerIn.setCompanyName("Company Name Test" + randomVal);
		return service.add(customerIn);
	}
	
	@Test(enabled = false)
	public void testUpload() throws FMSException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc.txt");
		attachable.setContentType("text/plain");
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
				
		String input = "This is just test...";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		Attachable attachableOutput = service.upload(attachable, in);
		Assert.assertNotNull(attachableOutput);
		
		InputStream output = service.download(attachableOutput);
		Assert.assertNotNull(output);
		String downloadedContent = getStringContent(output);
		Assert.assertEquals(downloadedContent, input);
	}

    @Test
    public void  testIsAvailableAsPDF()
    {
        Assert.assertTrue(service.isAvailableAsPDF(new Invoice()));
        Assert.assertTrue(service.isAvailableAsPDF(new Estimate()));
        Assert.assertTrue(service.isAvailableAsPDF(new SalesReceipt()));
        Assert.assertFalse(service.isAvailableAsPDF(new Purchase()));
    }


    @Test
    public void  isAvailableToEmail()
    {
        Assert.assertTrue(service.isAvailableToEmail(new Invoice()));
        Assert.assertTrue(service.isAvailableToEmail(new Estimate()));
        Assert.assertTrue(service.isAvailableToEmail(new SalesReceipt()));
        Assert.assertFalse(service.isAvailableToEmail(new Purchase()));
    }

	
	private Attachable getAttachableFields() {
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		Attachable attachable = new Attachable();
		attachable.setLat("25.293112341223");
		attachable.setLong("-21.3253249834");
		attachable.setPlaceName("Fake Place");
		attachable.setNote("Attachable note " + uuid);
		attachable.setTag("Attachable tag " + uuid);
		return attachable;
	}
	
	private String getStringContent(InputStream input) throws FMSException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] content = null;
		try {
			int nRead;
			byte[] data = new byte[256];
			while ((nRead = input.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			buffer.flush();
			content = buffer.toByteArray();
			buffer.close();
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
		return new String(content);
	}
}
