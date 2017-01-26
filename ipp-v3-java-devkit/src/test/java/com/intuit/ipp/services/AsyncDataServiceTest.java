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

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.EntityStatusEnum;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.EntityCreator;
import com.intuit.ipp.services.IPPHelper;

public class AsyncDataServiceTest {

	private  Context context = null;
	
	private DataService service = null;
	
	private CallbackMessage callbackMessageResult = null;
	
	private String randomVal = UUID.randomUUID().toString().substring(0, 5);
	
	@BeforeClass
	public void setup() throws FMSException {
		
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(), ippHelper.getQboAccessTokenSecret());
		context = new Context(oauth, ippHelper.getQboAppToken(), ServiceType.QBO, ippHelper.getQboRealmID());
		service = new DataService(context);
		EntityCreator.addCustomer(oauth, context);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_find = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testFindByIdAsync() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());
		
		service.findByIdAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_find.countDown();
			}
		});

		lock_find.await();
		
		Customer customer = (Customer) callbackMessageResult.getEntity();
		Assert.assertNotNull(customer);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_findAll = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testFindAllAsync() throws Exception {
		Customer customer = new Customer();
		
		service.findAllAsync(customer, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_findAll.countDown();
			}
		});
		
		lock_findAll.await();
		
		QueryResult queryResult = callbackMessageResult.getQueryResult();
		Assert.assertTrue(queryResult != null && !queryResult.getEntities().isEmpty());
	}
	
	/** Countdown latch */
	private CountDownLatch lock_add = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testAddAsync() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test101");
		customerIn.setCompanyName("Company Name Test101");
		
		service.addAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_add.countDown();
			}
		});
		
		lock_add.await();
		
		Customer customerOut = (Customer) callbackMessageResult.getEntity();
		Assert.assertNotNull(customerOut);
	}
	
	
	@Test (enabled = false)
	public void testAddAsync_duplicate() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test101");
		customerIn.setCompanyName("Company Name Test101");
		
		service.addAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_add.countDown();
			}
		});
		
		lock_add.await();
		
		FMSException fmsException = callbackMessageResult.getFMSException();
		Assert.assertNotNull(fmsException);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_update = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testUpdateAsync() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + randomVal);
		customerIn.setCompanyName("Company Name Test" + randomVal);
		Customer customerAdded = service.add(customerIn);
		
		customerIn.setId(customerAdded.getId());
		customerIn.setSyncToken(customerAdded.getSyncToken());
		customerIn.setSparse(true);
		customerIn.setDisplayName(customerIn.getDisplayName() + Calendar.getInstance().getTimeInMillis());

		service.updateAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_update.countDown();
			}
		});
		
		lock_update.await();
		
		Customer customerOut = (Customer) callbackMessageResult.getEntity();
		Assert.assertNotNull(customerOut);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_delete = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testDeleteAsync() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());

		service.deleteAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_delete.countDown();
			}
		});
		
		lock_delete.await();
		
		Customer customerOut = (Customer) callbackMessageResult.getEntity();
		Assert.assertNotNull(customerOut);
		Assert.assertEquals(customerOut.getStatus(), EntityStatusEnum.DELETED.value());
	}
	
	@Test (enabled = false)
	public void testDeleteAsync_invalidId() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");

		service.deleteAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_delete.countDown();
			}
		});
		
		lock_delete.await();
		//FMSException fmsException = callbackMessageResult.getFMSException();
		//Assert.assertNotNull(fmsException);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_void = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testVoidRequestAsync() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setId(EntityCreator.customer.getId());

		service.voidRequestAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_void.countDown();
			}
		});
		
		lock_void.await();
		
		Customer customerOut = (Customer) callbackMessageResult.getEntity();
		Assert.assertNotNull(customerOut);
		Assert.assertEquals(customerOut.getStatus(), EntityStatusEnum.VOIDED.value());
	}
	
	@Test (enabled = false)
	public void testVoidRequestAsync_invalidId() throws Exception {
		Customer customerIn = new Customer();
		customerIn.setId("NG:111");

		service.voidRequestAsync(customerIn, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_void.countDown();
			}
		});
		
		lock_void.await();
		
		FMSException fmsException = callbackMessageResult.getFMSException();
		Assert.assertNotNull(fmsException);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_query = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testExecuteQueryAsync() throws Exception {
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(EntityCreator.customer.getId())).generate();

		
		service.executeQueryAsync(query, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_query.countDown();
			}
		});
		
		lock_query.await();
		
		QueryResult queryResult = callbackMessageResult.getQueryResult();
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.getEntities().size(), 1);
		Assert.assertEquals(queryResult.getMaxResults().intValue(), 1);
		Assert.assertNotNull(queryResult.getStartPosition());
		Assert.assertNull(queryResult.getTotalCount());
	}
	
	/** Countdown latch */
	private CountDownLatch lock_batch = new CountDownLatch(1);
	
	@Test(enabled = false)
	public void testExecuteBatchAsync() throws Exception {
		BatchOperation batchOperation = new BatchOperation();
		
		Customer customerIn = new Customer();
		customerIn.setDisplayName("Test" + randomVal);
		customerIn.setCompanyName("Company Name Test" + randomVal);
		Customer customerAdded = service.add(customerIn);
		
		customerIn.setId(customerAdded.getId());
		customerIn.setSyncToken(customerAdded.getSyncToken());
		customerIn.setSparse(true);
		customerIn.setDisplayName(customerIn.getDisplayName()+Calendar.getInstance().getTimeInMillis());
		batchOperation.addEntity(customerIn, OperationEnum.UPDATE, "12");
		
		Customer c = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(c.getId()), $(c.getDisplayName()))
				.where($(c.getId()).eq("NG:2293936")).generate();
		batchOperation.addQuery(query, "13");
		
		String reportQuery = "select * from Customer";
		batchOperation.addReportQuery(reportQuery, "14");
		
		service.executeBatchAsync(batchOperation, new CallbackHandler() {
			
			@Override
			public void execute(CallbackMessage callbackMessage) {
				callbackMessageResult = callbackMessage;
				lock_batch.countDown();
			}
		});
		
		lock_batch.await();
		
		batchOperation = callbackMessageResult.getBatchOperation();
		Assert.assertTrue(batchOperation.isEntity("12"));
		Assert.assertTrue(batchOperation.isQuery("13"));
		Assert.assertTrue(batchOperation.isFault("14"));
	}
}
