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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.AccountBasedExpenseLineDetail;
import com.intuit.ipp.data.AccountClassificationEnum;
import com.intuit.ipp.data.AccountSubTypeEnum;
import com.intuit.ipp.data.AccountTypeEnum;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.data.Bill;
import com.intuit.ipp.data.Company;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.EmailAddress;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PhysicalAddress;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Vendor;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.net.ContentTypes;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.DateUtils;
import com.intuit.ipp.services.IPPHelper;

public class QBODataServiceTest {

	private Context context = null;

	private DataService service = null;

	private CallbackMessage callbackMessageResult = null;
	
	private String randomVal = UUID.randomUUID().toString();

	@BeforeClass
	public void setup() throws FMSException {
		IPPHelper ippHelper = IPPHelper.getInstance();
		OAuthAuthorizer oauth = new OAuthAuthorizer(ippHelper.getQboConsumerKey(), ippHelper.getQboConsumerSecret(), ippHelper.getQboAccessToken(),
				ippHelper.getQboAccessTokenSecret());
		context = new Context(oauth, ServiceType.QBO, ippHelper.getQboRealmID());
		service = new DataService(context);
	}

	@Test (enabled = false)
	public void testAddCustomer() throws FMSException {
		Customer customer = new Customer();
		customer.setDisplayName(randomVal.substring(0, 20));
		customer.setTitle(randomVal.substring(0, 11));
		Customer addedCustomer = service.add(customer);
		Assert.assertNotNull(addedCustomer);
		Assert.assertEquals(addedCustomer.getDisplayName(), customer.getDisplayName());
	}
	
	private Customer getCustomer() throws FMSException {
		List<Customer> customers = (List<Customer>) service.findAll(new Customer());
		if (!customers.isEmpty()) {
			return customers.get(0);
		}
		return createCustomer();
	}

	private Vendor getVendor() throws FMSException {
		List<Vendor> vendors = (List<Vendor>) service.findAll(new Vendor());
		if (!vendors.isEmpty()) {
			return vendors.get(0);
		}
		return createVendor();
	}

	@Test (enabled = false)
	public Account getLiabilityBankAccount() throws FMSException {
		List<Account> accounts = (List<Account>) service.findAll(new Account());
		if (!accounts.isEmpty()) {
			Iterator<Account> itr = accounts.iterator();
			while (itr.hasNext()) {
				Account account = itr.next();
				if (account.getAccountType().equals(AccountTypeEnum.ACCOUNTS_PAYABLE)
						&& account.getClassification().value().equals(AccountClassificationEnum.LIABILITY)) {
					return account;
				}
			}
		}
		return createLiabilityBankAccount();
	}

	@Test (enabled = false)
	public Account getExpenseBankAccount() throws FMSException {
		List<Account> accounts = (List<Account>) service.findAll(new Account());
		if (!accounts.isEmpty()) {
			Iterator<Account> itr = accounts.iterator();
			while (itr.hasNext()) {
				Account account = itr.next();
				if (account.getAccountType().equals(AccountTypeEnum.EXPENSE)) {
					return account;
				}
			}
		}
		return createExpenseBankAccount();
	}

	private Vendor createVendor() throws FMSException {
		Vendor vendor = new Vendor();
		vendor.setDisplayName(randomVal.substring(0, 25));
		vendor.setTitle(randomVal.substring(0, 7));
		return service.add(vendor);
	}

	private Customer createCustomer() throws FMSException {
		Customer customer = new Customer();
		customer.setDisplayName(randomVal.substring(0, 25));
		customer.setTitle(randomVal.substring(0, 7));
		return service.add(customer);
	}
	
	private Account createLiabilityBankAccount() throws FMSException {
		Account account = new Account();
		account.setName("Equity" + randomVal.substring(0, 5));
		account.setSubAccount(false);
		account.setFullyQualifiedName("Liability" + randomVal.substring(0, 5));
		account.setActive(true);
		account.setClassification(AccountClassificationEnum.LIABILITY);
		account.setAccountType(AccountTypeEnum.ACCOUNTS_PAYABLE);
		account.setAccountSubType(AccountSubTypeEnum.ACCOUNTS_PAYABLE.value());
		account.setCurrentBalance(new BigDecimal("3000"));
		account.setCurrentBalanceWithSubAccounts(new BigDecimal("3000"));
		ReferenceType currencyRef = new ReferenceType();
		currencyRef.setName("United States Dollar");
		currencyRef.setValue("USD");
		account.setCurrencyRef(currencyRef);

		return service.add(account);
	}

	private Account createExpenseBankAccount() throws FMSException {
		Account account = new Account();
		account.setName("Expense" + randomVal.substring(0, 5));
		account.setSubAccount(false);
		account.setFullyQualifiedName("Expense" + randomVal.substring(0, 5));
		account.setActive(true);
		account.setClassification(AccountClassificationEnum.EXPENSE);
		account.setAccountType(AccountTypeEnum.EXPENSE);
		account.setAccountSubType(AccountSubTypeEnum.ADVERTISING_PROMOTIONAL.value());
		account.setCurrentBalance(new BigDecimal("0"));
		account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"));
		ReferenceType currencyRef = new ReferenceType();
		currencyRef.setName("United States Dollar");
		currencyRef.setValue("USD");
		account.setCurrencyRef(currencyRef);

		return service.add(account);
	}

	@Test (enabled = false)
	public void testFindById() throws FMSException {
		Customer customerIn = getCustomer();
		Customer customerOut = service.findById(customerIn);
		Assert.assertNotNull(customerOut);
	}

	@Test (enabled = false)
	public void testAddBill() throws FMSException {
		Vendor vendor = getVendor();
		ReferenceType vendorRef = new ReferenceType();
		vendorRef.setName(vendor.getDisplayName());
		vendorRef.setValue(vendor.getId());

		Account account = getLiabilityBankAccount();
		ReferenceType accountRef = new ReferenceType();
		accountRef.setName(account.getName());
		accountRef.setValue(account.getId());

		Bill bill = new Bill();
		bill.setTotalAmt(new BigDecimal("5.00"));
		bill.setBalance(new BigDecimal("10.00"));
		bill.setVendorRef(vendorRef);
		bill.setAPAccountRef(accountRef);

		Line line1 = new Line();
		line1.setAmount(new BigDecimal("3.00"));
		line1.setDetailType(LineDetailTypeEnum.ACCOUNT_BASED_EXPENSE_LINE_DETAIL);
		AccountBasedExpenseLineDetail detail = new AccountBasedExpenseLineDetail();
		account = getExpenseBankAccount();
		ReferenceType expenseAccountRef = new ReferenceType();
		expenseAccountRef.setName(account.getName());
		expenseAccountRef.setValue(account.getId());

		detail.setAccountRef(expenseAccountRef);
		line1.setAccountBasedExpenseLineDetail(detail);

		List<Line> lines1 = new ArrayList<Line>();
		lines1.add(line1);
		bill.setLine(lines1);

		EmailAddress emailAddr = new EmailAddress();
		emailAddr.setAddress("test@testing.com");
		emailAddr.setDefault(true);
		emailAddr.setTag("Business");
		bill.setBillEmail(emailAddr);

		Bill billOut = service.add(bill);
		Assert.assertTrue(billOut != null);
	}

	@Test(enabled = false)
	public void testAddCompany() throws FMSException {
		Company companyIn = new Company();
		//companyIn.setSyncToken("0");
		companyIn.setCompanyName("Test" + randomVal);

		PhysicalAddress companyAddr = new PhysicalAddress();
		companyAddr.setLine1("Testing1");
		companyAddr.setLine2("Testing2");
		companyAddr.setLine3("Testing3");
		companyAddr.setCity("Bangalore");
		companyAddr.setCountry("India");
		companyAddr.setCountrySubDivisionCode("KA");
		companyAddr.setPostalCode("560097");
		companyIn.setCompanyAddr(companyAddr);

		Company companyOut = service.add(companyIn);
		Assert.assertTrue(companyOut != null);
	}

	@Test (enabled = false)
	public void testPurchaseQuery() throws FMSException {
		QueryResult queryResult = service.executeQuery("select * from Purchase");
		Assert.assertNotNull(queryResult);
	}
	
	@Test (enabled = false)
	public void testCDCQuery() throws FMSException, ParseException {
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		
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
	
	@Test (enabled = false)
	public void testCDCQuery_forNullEntities() throws FMSException, ParseException {
		boolean isException = false;
		List<IEntity> entities = null;
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		try {
			service.executeCDCQuery(entities, changedSince);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testCDCQuery_forEmptyEntities() throws FMSException, ParseException {
		boolean isException = false;
		List<IEntity> entities = new ArrayList<IEntity>();
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		try {
			service.executeCDCQuery(entities, changedSince);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testCDCQuery_forNullChangedSince() throws FMSException, ParseException {
		boolean isException = false;
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Item());
		
		String changedSince = null;
		try {
			service.executeCDCQuery(entities, changedSince);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	@Test (enabled = false)
	public void testCDCQuery_incorrectChangedSince() throws FMSException, ParseException {
		boolean isException = false;
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Item());
		
		String changedSince = "22-22-22";
		try {
			service.executeCDCQuery(entities, changedSince);
		} catch (FMSException e) {
			isException = true;
		}
		Assert.assertTrue(isException);
	}
	
	/** Countdown latch */
	private CountDownLatch lock_cdcQuery = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testAsyncCDCQuery() throws FMSException, Exception {
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		try {
			service.executeCDCQueryAsync(entities, changedSince, new CallbackHandler() {
				@Override
				public void execute(CallbackMessage callbackMessage) {
					callbackMessageResult = callbackMessage;
					lock_cdcQuery.countDown();
				}
			});
		} catch (FMSException e) {
			Assert.assertTrue(false, e.getMessage());
		}
		lock_cdcQuery.await();
		List<CDCQueryResult> cdcQueryResults = callbackMessageResult.getCDCQueryResults();
		Assert.assertNotNull(cdcQueryResults);
		CDCQueryResult cdcQueryResult = cdcQueryResults.get(0);
		Assert.assertNull(cdcQueryResult.getFalut());
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		Assert.assertNotNull(queryResults);
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Customer customer = (Customer) customerEntities.get(0);
		Assert.assertNotNull(customer);
	}
	
	@Test (enabled = false)
	public void testCDCQuery_JsonResponse() throws FMSException, ParseException {
		try {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
			
			List<IEntity> entities = new ArrayList<IEntity>();	
			entities.add(new Customer());
			entities.add(new Account());
			
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
		} finally {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
		}
	}
	
	@Test (enabled = false)
	public void testBatchCDCQuery() throws FMSException, ParseException {
		BatchOperation batchOperation = new BatchOperation();
		
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		batchOperation.addCDCQuery(entities, changedSince, "1");
		
		service.executeBatch(batchOperation);
		
		CDCQueryResult cdcQueryResult = batchOperation.getCDCQueryResult("1");
		Assert.assertNull(cdcQueryResult.getFalut());
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		Assert.assertNotNull(queryResults);
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Customer customer = (Customer) customerEntities.get(0);
		Assert.assertNotNull(customer);
	}
	
	@Test (enabled = false)
	public void testBatchCDCQuery_JsonResponse() throws FMSException, ParseException {
		try {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
			
			BatchOperation batchOperation = new BatchOperation();
			
			List<IEntity> entities = new ArrayList<IEntity>();	
			entities.add(new Customer());
			entities.add(new Account());
			
			String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
			
			batchOperation.addCDCQuery(entities, changedSince, "1");
			
			service.executeBatch(batchOperation);
			
			CDCQueryResult cdcQueryResult = batchOperation.getCDCQueryResult("1");
			Assert.assertNull(cdcQueryResult.getFalut());
			Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
			Assert.assertNotNull(queryResults);
			List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
			Customer customer = (Customer) customerEntities.get(0);
			Assert.assertNotNull(customer);
		} finally {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
		}
	}
	
	@Test (enabled = false)
	public void testBatchCDCQuery_JsonRequestAndResponse() throws FMSException, ParseException {
		try {
			Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "json");
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
			
			BatchOperation batchOperation = new BatchOperation();
			
			List<IEntity> entities = new ArrayList<IEntity>();	
			entities.add(new Customer());
			entities.add(new Account());
			
			String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
			
			batchOperation.addCDCQuery(entities, changedSince, "1");
			
			service.executeBatch(batchOperation);
			
			CDCQueryResult cdcQueryResult = batchOperation.getCDCQueryResult("1");
			Assert.assertNull(cdcQueryResult.getFalut());
			Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
			Assert.assertNotNull(queryResults);
			List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
			Customer customer = (Customer) customerEntities.get(0);
			Assert.assertNotNull(customer);
		} finally {
			Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, "xml");
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
		}
	}
	
	@Test (enabled = false)
	public void testPurchaseQuery_JsonResponse() throws FMSException {
		try {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "json");
			QueryResult queryResult = service.executeQuery("select * from Purchase");
			Assert.assertNotNull(queryResult);
		} finally {
			Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, "xml");
		}
	}
	
	@Test (enabled = false)
	public void testExecuteQuery_get() throws FMSException {
		Customer customerIn = getCustomer();
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(customerIn.getId())).generate();

		QueryResult queryResult = service.executeQuery(query);
		
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(queryResult.getEntities().size(), 1);
		Assert.assertEquals(queryResult.getMaxResults().intValue(), 1);
		Assert.assertNotNull(queryResult.getStartPosition());
		Assert.assertNull(queryResult.getTotalCount());
	}
	
	@Test(enabled=false)
	public void testExecuteQuery_post() throws FMSException {
		Customer customerIn = getCustomer();
		Customer customer = GenerateQuery.createQueryEntity(Customer.class);
		String query = select($(customer.getId()), $(customer.getDisplayName())).where($(customer.getId()).eq(customerIn.getId())).generate();
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

    //TODO remove this test, since it's part of integration suit already (it was also disabled)
    //see SDK-180 for more details
	@Test(enabled=false)
	public void testUploadDownload() throws FMSException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc1.txt");
		attachable.setContentType("text/plain");
		
		String input = "This is just test upload download...";
		
		InputStream in = null;
		Attachable attachableOutput = null;
		try {
			
			in = new ByteArrayInputStream(input.getBytes());
			attachableOutput = service.upload(attachable, in);
			Assert.assertNotNull(attachableOutput);
		} finally {
			close(in);
		}
		
		InputStream output = null;
		try {
			output = service.download(attachableOutput);
			Assert.assertNotNull(output);
			String downloadedContent = getStringContent(output);
			Assert.assertEquals(downloadedContent, input);
		} finally {
			close(output);
		}
	}
	
	@Test(enabled=false)
	public void testUploadDownload_Json() throws FMSException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc2.txt");
		attachable.setContentType("text/plain");
		
		Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
				
		String input = "This is just test upload download json...";

		InputStream in = null;
		Attachable attachableOutput = null;
		try {
			
			in = new ByteArrayInputStream(input.getBytes());
			attachableOutput = service.upload(attachable, in);
			Assert.assertNotNull(attachableOutput);
		} finally {
			close(in);
		}
		
		InputStream output = null;
		try {
			output = service.download(attachableOutput);
			Assert.assertNotNull(output);
			String downloadedContent = getStringContent(output);
			Assert.assertEquals(downloadedContent, input);
		} finally {
			close(output);
		}
	}
	
	/** Countdown latch */
	private CountDownLatch lock_upload = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testAsyncUpload() throws FMSException, Exception {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc3.txt");
		attachable.setContentType("text/plain");
		
		String input = "This is just test upload async...";
		InputStream in = null;
		
		try {
			in = new ByteArrayInputStream(input.getBytes());
			
			try {
				service.uploadAsync(attachable, in, new CallbackHandler() {
					@Override
					public void execute(CallbackMessage callbackMessage) {
						callbackMessageResult = callbackMessage;
						lock_upload.countDown();
					}
				});
			} catch (FMSException e) {
				Assert.assertTrue(false, e.getMessage());
			}
			lock_upload.await();
			List<AttachableResponse> attachableResponses = callbackMessageResult.getAttachableResponse();
			Assert.assertNotNull(attachableResponses);
			AttachableResponse attachableResponse = attachableResponses.get(0);
			Assert.assertNotNull(attachableResponse);
			Attachable attachableOutput = attachableResponse.getAttachable();
			Assert.assertNotNull(attachableOutput);
		} finally {
			close(in);
		}
	}
	
	/** Countdown latch */
	private CountDownLatch lock_download = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testAsyncDownload() throws FMSException, Exception {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc4.txt");
		attachable.setContentType("text/plain");
		
		String input = "This is just test download async...";
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(input.getBytes());
			Attachable attachableOutput = service.upload(attachable, in);
			Assert.assertNotNull(attachableOutput);
			
			try {
				service.downloadAsync(attachableOutput, new CallbackHandler() {
					@Override
					public void execute(CallbackMessage callbackMessage) {
						callbackMessageResult = callbackMessage;
						lock_download.countDown();
					}
				});
			} catch (FMSException e) {
				Assert.assertTrue(false, e.getMessage());
			}
			lock_download.await();
			InputStream downloadedFile = callbackMessageResult.getDownloadedFile();
			Assert.assertNotNull(downloadedFile);
			String downloadedContent = getStringContent(downloadedFile);
			Assert.assertEquals(downloadedContent, input);
		} finally {
			close(in);
		}
	}
	
	/** Countdown latch */
	private CountDownLatch lock_downloadJson = new CountDownLatch(1);
	
	@Test (enabled = false)
	public void testAsyncDownload_Json() throws FMSException, Exception {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc5.txt");
		attachable.setContentType("text/plain");
		
		Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
				
		String input = "This is just test download async json...";
		InputStream in = null;
		
		try {
			in = new ByteArrayInputStream(input.getBytes());
			Attachable attachableOutput = service.upload(attachable, in);
			Assert.assertNotNull(attachableOutput);
			
			try {
				service.downloadAsync(attachableOutput, new CallbackHandler() {
					@Override
					public void execute(CallbackMessage callbackMessage) {
						callbackMessageResult = callbackMessage;
						lock_downloadJson.countDown();
					}
				});
			} catch (FMSException e) {
				Assert.assertTrue(false, e.getMessage());
			}
			lock_downloadJson.await();
			InputStream downloadedFile = callbackMessageResult.getDownloadedFile();
			Assert.assertNotNull(downloadedFile);
			String downloadedContent = getStringContent(downloadedFile);
			Assert.assertEquals(downloadedContent, input);
		} finally {
			close(in);
		}
	}
	
	@Test(enabled=false)
	public void testUploadDownload_withoutCompression() throws FMSException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc6.txt");
		attachable.setContentType("text/plain");
		
		String input = "This is just test upload download without compression...";
		try {
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "");
			Config.setProperty(Config.COMPRESSION_RESPONSE_FORMAT, "");
			InputStream in = null;
			Attachable attachableOutput = null;
			try {
				
				in = new ByteArrayInputStream(input.getBytes());
				attachableOutput = service.upload(attachable, in);
				Assert.assertNotNull(attachableOutput);
			} finally {
				close(in);
			}
			
			InputStream output = null;
			try {
				output = service.download(attachableOutput);
				Assert.assertNotNull(output);
				String downloadedContent = getStringContent(output);
				Assert.assertEquals(downloadedContent, input);
			} finally {
				close(output);
			}
		} finally {
			Config.setProperty(Config.COMPRESSION_REQUEST_FORMAT, "gzip");
			Config.setProperty(Config.COMPRESSION_RESPONSE_FORMAT, "gzip");
		}
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
	
	private String getStringContent(InputStream in) throws FMSException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] content = null;
		try {
			int nRead;
			byte[] data = new byte[256];
			while ((nRead = in.read(data, 0, data.length)) != -1) {
			  baos.write(data, 0, nRead);
			}
			baos.flush();
			content = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		} finally {
			close(baos);
			close(in);
		}
		return new String(content);
	}
	
	private void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void close(ByteArrayOutputStream baos) {
		if (baos != null) {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
