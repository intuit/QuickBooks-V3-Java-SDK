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
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.intuit.ipp.data.Transfer;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.TaxRate;
import com.intuit.ipp.data.BatchItemResponse;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Deposit;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.JournalCode;
import com.intuit.ipp.util.Config;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.exception.SerializationException;

public class JSONSerializerTest {

    JSONSerializer jsonObj;

    @BeforeClass
    public void setUP() {
        jsonObj = new JSONSerializer();
    }

    @Test
    public void testSerialize() throws FMSException {
        Customer s = new Customer();
        s.setContactName("Aditi");

        String x = jsonObj.serialize(getSerializableRequestObject(s));
        Assert.assertEquals(x, "{\"ContactName\":\"Aditi\"}");
    }

    @Test
    public void testSerialize_null() throws Exception {
        String str = jsonObj.serialize(null);
        Assert.assertNull(str);
    }

    @Test
    public void testDeserialize() throws SerializationException {

        String json = "{\"Customer\":{\"CompanyName\":\"Company Name 31\",\"DisplayName\":\"Loki31\"}}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(json, IntuitResponse.class);
        Assert.assertEquals((((Customer) intuitResponse.getIntuitObject().getValue()).getCompanyName()), "Company Name 31");
    }

    @Test
    public void testDeserialize_Fault() throws SerializationException {

        String json = "{\"time\":\"2012-08-14T07:25:33.111Z\",\"Fault\":{\"Error\":[{\"Message\":\"Unsupported operation requested\",\"code\":\"500\"}],\"type\":\"Validation\"}}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(json, IntuitResponse.class);
        Assert.assertNotNull(intuitResponse.getFault());
    }

    @Test
    public void testDeserialize_Report() throws SerializationException {

        String json = "{\"time\":\"2012-08-14T07:25:33.111Z\",\"requestId\":\"123\",\"status\":\"pending\",\"Report\":{\"domain\":\"Validation\"}}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(json, IntuitResponse.class);
        Assert.assertNotNull(intuitResponse.getReport());
    }

    @Test
    public void testDeserialize_Query() throws SerializationException {

        String json = "{\"time\":\"2012-08-14T07:25:33.111Z\",\"QueryResponse\":{\"startPosition\":1,\"totalCount\":1,\"maxResults\":1,\"Fault\":{\"Error\":[{\"Message\":\"Unsupported operation requested\",\"code\":\"500\"}],\"type\":\"Validation\"},\"Customer\":[{\"Id\":\"NG:2293936\",\"DisplayName\":\"0588e717-4c0f-4fb3-8e7c-7\"}]}}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(json, IntuitResponse.class);
        Assert.assertNotNull(intuitResponse.getQueryResponse());
    }

    @Test
    public void testDeserialize_BatchResponse() throws SerializationException {
        String xmlResponse = "{\"time\":\"2012-08-14T07:25:33.111Z\",\"BatchItemResponse\":[{\"bId\":\"12\",\"Customer\":{\"Job\":false,\"OpenBalanceDate\":\"2012-07-23T11:17:15Z\",\"CurrencyRef\":{\"name\":\"US Dollar\"},\"status\":\"Pending\",\"Id\":\"NG:2285964\",\"SyncToken\":\"216\",\"MetaData\":{\"CreateTime\":\"2012-07-23T11:17:56Z\",\"LastUpdatedTime\":\"2012-08-14T07:25:33Z\"},\"Organization\":false,\"CompanyName\":\"Company Name 34\",\"DisplayName\":\"L 34\",\"Active\":false}},{\"bId\":\"13\",\"QueryResponse\":{\"startPosition\":1,\"maxResults\":1,\"Customer\":[{\"Id\":\"NG:2293936\",\"DisplayName\":\"0588e717-4c0f-4fb3-8e7c-7\"}]}},{\"bId\":\"14\",\"Fault\":{\"Error\":[{\"Message\":\"Unsupported operation requested\",\"code\":\"500\"}],\"type\":\"Validation\"}}]}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(xmlResponse, IntuitResponse.class);

        List<BatchItemResponse> batchItemResponses = intuitResponse.getBatchItemResponse();
        Assert.assertEquals(batchItemResponses.size(), 3);
    }

    @Test
    public void testDeserialize_QueryResponse() throws SerializationException {
        String xmlResponse = "{\"QueryResponse\":{\"Account\":[{\"Name\":\"Accounts Receivable_ae079\",\"SubAccount\":false,\"Description\":\"Updated @ 2015-09-10T11:15:59-05:00\",\"FullyQualifiedName\":\"Accounts Receivable_ae079\",\"Active\":true,\"Classification\":\"Asset\",\"AccountType\":\"Accounts Receivable\",\"AccountSubType\":\"AccountsReceivable\",\"CurrentBalance\":-646.8,\"CurrentBalanceWithSubAccounts\":-646.8,\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"13320\",\"SyncToken\":\"1\",\"MetaData\":{\"CreateTime\":\"2015-09-10T05:23:47-07:00\",\"LastUpdatedTime\":\"2015-09-10T12:44:57-07:00\"}},{\"Name\":\"Acct10007519461569892922\",\"SubAccount\":false,\"Description\":\"Originally created @ 2015-09-09T16:13:21-05:00\",\"FullyQualifiedName\":\"Acct10007519461569892922\",\"Active\":true,\"Classification\":\"Expense\",\"AccountType\":\"Expense\",\"AccountSubType\":\"Travel\",\"CurrentBalance\":0,\"CurrentBalanceWithSubAccounts\":0,\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"12079\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-09-09T14:13:21-07:00\",\"LastUpdatedTime\":\"2015-09-09T14:13:21-07:00\"}},{\"Name\":\"Acct10115087251982090345\",\"SubAccount\":false,\"Description\":\"Updated @ 2015-09-04T11:15:47-05:00\",\"FullyQualifiedName\":\"Acct10115087251982090345\",\"Active\":true,\"Classification\":\"Expense\",\"AccountType\":\"Expense\",\"AccountSubType\":\"Travel\",\"CurrentBalance\":0,\"CurrentBalanceWithSubAccounts\":0,\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"636\",\"SyncToken\":\"1\",\"MetaData\":{\"CreateTime\":\"2015-08-25T09:43:27-07:00\",\"LastUpdatedTime\":\"2015-09-04T09:23:16-07:00\"}},{\"Name\":\"Acct1012566432148149940\",\"SubAccount\":false,\"Description\":\"Updated @ 2015-08-27T11:15:35-05:00\",\"FullyQualifiedName\":\"Acct1012566432148149940\",\"Active\":true,\"Classification\":\"Expense\",\"AccountType\":\"Expense\",\"AccountSubType\":\"Travel\",\"CurrentBalance\":0,\"CurrentBalanceWithSubAccounts\":0,\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"1688\",\"SyncToken\":\"1\",\"MetaData\":{\"CreateTime\":\"2015-08-27T09:19:25-07:00\",\"LastUpdatedTime\":\"2015-08-27T09:19:26-07:00\"}}],\"startPosition\":1,\"maxResults\":100},\"time\":\"2015-09-10T13:53:18.893-07:00\"}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(xmlResponse, IntuitResponse.class);
        Account account = (Account) intuitResponse.getQueryResponse().getIntuitObject().get(0).getValue();
        Assert.assertEquals(account.getCurrentBalance(), new BigDecimal("-646.80"));

    }

    @Test
    // Tests big decimal issue in a CDCQueryResponse
    public void testDeserialize_CDCQueryResponse() throws SerializationException {
        String xmlResponse = "{\"CDCResponse\":[{\"QueryResponse\":[{\"Deposit\":[{\"DepositToAccountRef\":{\"value\":\"653\",\"name\":\"Bank (102028346)\"},\"TotalAmt\":100,\"HomeTotalAmt\":100,\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"12037\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-09-07T03:31:54-07:00\",\"LastUpdatedTime\":\"2015-09-07T03:31:54-07:00\"},\"TxnDate\":\"2015-09-07\",\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"ExchangeRate\":1,\"PrivateNote\":\"PrivateNote8691daaa\",\"Line\":[{\"Id\":\"1\",\"LineNum\":1,\"Description\":\"Description\",\"Amount\":100,\"DetailType\":\"DepositLineDetail\",\"DepositLineDetail\":{\"Entity\":{\"value\":\"116\",\"name\":\"$%^(&\",\"type\":\"CUSTOMER\"},\"AccountRef\":{\"value\":\"8342\",\"name\":\"Name_7e5e3cec92a74dd09bd264bc5aa19af1\"},\"PaymentMethodRef\":{\"value\":\"707\",\"name\":\"CreditCard20b99ee9341d4\"}}}]},{\"DepositToAccountRef\":{\"value\":\"653\",\"name\":\"Bank (102028346)\"},\"TotalAmt\":100,\"HomeTotalAmt\":100,\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"12039\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-09-07T03:32:19-07:00\",\"LastUpdatedTime\":\"2015-09-07T03:32:19-07:00\"},\"TxnDate\":\"2015-09-07\",\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"ExchangeRate\":1,\"PrivateNote\":\"PrivateNote63bf961f\",\"Line\":[{\"Id\":\"1\",\"LineNum\":1,\"Description\":\"Description\",\"Amount\":100,\"DetailType\":\"DepositLineDetail\",\"DepositLineDetail\":{\"Entity\":{\"value\":\"116\",\"name\":\"$%^(&\",\"type\":\"CUSTOMER\"},\"AccountRef\":{\"value\":\"8344\",\"name\":\"Name_10176b52b2b0460a9bb7ef487f856ff8\"},\"PaymentMethodRef\":{\"value\":\"709\",\"name\":\"CreditCardb661720ccb144\"}}}]}]}]}],\"time\":\"2015-09-08T17:31:09.172-07:00\"}";
        IntuitResponse intuitResponse = (IntuitResponse) jsonObj.deserialize(xmlResponse, IntuitResponse.class);
        Deposit deposit = (Deposit) intuitResponse.getCDCResponse().get(0).getQueryResponse().get(0).getIntuitObject().get(0).getValue();
    }

    @Test
    public void testDeserialize_Invalid() {
        String xmlResponse = "{\"time\":\"2012-08-14T07:25:33.111Z\",\"QueryResponse\":{\"startPosition\":1,\"totalCount\":1,\"maxResults\":1,\"Customer\":[{\"Id\":\"NG:2293936\",\"DisplayName\":\"0588e717-4c0f-4fb3-8e7c-7\"}],\"Fault\":{\"Error\":[{\"Message\":\"Unsupported operation requested\",\"code\":\"500\"}],\"type\":\"Validation\"}}";
        IntuitResponse intuitResponse = null;
        boolean isException = false;
        try {
            intuitResponse = (IntuitResponse) jsonObj.deserialize(xmlResponse, IntuitResponse.class);
        } catch (SerializationException e) {
            isException = true;
        }
        Assert.assertTrue(isException);
        Assert.assertNull(intuitResponse);
    }


    @Test
    public void testAttachableResponse() throws SerializationException
    {
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getAttachableResponseJSON(), IntuitResponse.class);
        Assert.assertEquals(2, intuitResponse.getAttachableResponse().size());
        Assert.assertEquals("my_text.txt", intuitResponse.getAttachableResponse().get(0).getAttachable().getFileName());
        Assert.assertEquals("my_text2.txt", intuitResponse.getAttachableResponse().get(1).getAttachable().getFileName());
    }

    // This test expects changes in the schema
    @Test(enabled = true)
    public void testInvoiceAndHomeBalanceResponse() throws SerializationException
    {
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getHomeBalanceResponseJSON(), IntuitResponse.class);
        Invoice entity = (Invoice)intuitResponse.getIntuitObject().getValue();
        Assert.assertEquals(new BigDecimal("100055.55"), entity.getHomeBalance());

    }

    // This test expects changes in the schema
    @Test(enabled = true)
    public void testJournalCode() throws SerializationException
    {
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getJournalCode(), IntuitResponse.class);
        JournalCode entity = (JournalCode)intuitResponse.getIntuitObject().getValue();
       Assert.assertEquals("JC49b8b075-7be2-4", entity.getName());

    }

    @Test
    public void testMultiAttachableResponseFault() throws SerializationException
    {
     //getMultiAttachablePayload
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getMultiAttachablePayload(), IntuitResponse.class);
        Assert.assertEquals(5, intuitResponse.getAttachableResponse().size());
        Assert.assertEquals("Invalid Company Status", intuitResponse.getAttachableResponse().get(0).getFault().getError().get(0).getMessage());
        Assert.assertEquals("Invalid Company XXXX", intuitResponse.getAttachableResponse().get(4).getFault().getError().get(0).getMessage());
    }



    private String getAttachableResponseJSON()
    {
        return "{\"AttachableResponse\":[{\"Attachable\":{\"FileName\":\"my_text.txt\",\"FileAccessUri\":\"/v3/company/1136943200/download/300400000000000048735\",\"TempDownloadUri\":\"https://intuit-qbo-preprod-17.s3.amazonaws.com/1136943200%2Fattachments%2Fmy_text.txt?Expires=1431625806&AWSAccessKeyId=AKIAJGSTOFUAHNSLCR2Q&Signature=p4htwmxFf45PgCFVcHfX3qkpnzA%3D\",\"Size\":20,\"ContentType\":\"text/plain\",\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"300400000000000048735\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-05-14T10:35:05-07:00\",\"LastUpdatedTime\":\"2015-05-14T10:35:05-07:00\"}}},{\"Attachable\":{\"FileName\":\"my_text2.txt\",\"FileAccessUri\":\"/v3/company/1136943200/download/300400000000000048736\",\"TempDownloadUri\":\"https://intuit-qbo-preprod-12.s3.amazonaws.com/1136943200%2Fattachments%2Fmy_text2-1431624906064.txt?Expires=1431625807&AWSAccessKeyId=AKIAJGSTOFUAHNSLCR2Q&Signature=6lCQs2zHh5fmIGaORF2IAwMoYA0%3D\",\"Size\":21,\"ContentType\":\"text/plain\",\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"300400000000000048736\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-05-14T10:35:06-07:00\",\"LastUpdatedTime\":\"2015-05-14T10:35:06-07:00\"}}}],\"time\":\"2015-05-14T10:35:04.856-07:00\"}\n";
    }

    private String getHomeBalanceResponseJSON()
    {
        return "{\"Invoice\":{\"Deposit\":0,\"AllowIPNPayment\":false,\"AllowOnlinePayment\":false,\"AllowOnlineCreditCardPayment\":false,\"AllowOnlineACHPayment\":false,\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"20246\",\"SyncToken\":\"0\",\"MetaData\":{\"CreateTime\":\"2015-08-10T11:33:46-07:00\",\"LastUpdatedTime\":\"2015-08-10T16:30:47-07:00\"},\"CustomField\":[],\"DocNumber\":\"4020\",\"TxnDate\":\"2015-08-10\",\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"ExchangeRate\":1,\"PrivateNote\":\"Opening Balance\",\"LinkedTxn\":[],\"Line\":[{\"Id\":\"1\",\"LineNum\":1,\"Description\":\"Opening Balance\",\"Amount\":100055.55,\"DetailType\":\"SalesItemLineDetail\",\"SalesItemLineDetail\":{\"ItemRef\":{\"value\":\"1\",\"name\":\"Services\"},\"TaxCodeRef\":{\"value\":\"NON\"}}},{\"Amount\":100055.55,\"DetailType\":\"SubTotalLineDetail\",\"SubTotalLineDetail\":{}}],\"TxnTaxDetail\":{\"TotalTax\":0},\"CustomerRef\":{\"value\":\"4841\",\"name\":\"fc444465-95c0-43b6-9395-c\"},\"BillAddr\":{\"Id\":\"5679\",\"Line1\":\"Testing1\",\"Line2\":\"Testing2\",\"Line3\":\"Testing3\",\"City\":\"Bangalore\",\"Country\":\"India\",\"CountrySubDivisionCode\":\"KA\",\"PostalCode\":\"560097\",\"Lat\":\"13.0809029\",\"Long\":\"77.5564981\"},\"ShipAddr\":{\"Id\":\"5680\",\"Line1\":\"Shipping1\",\"Line2\":\"Shipping1\",\"Line3\":\"Shipping1\",\"City\":\"Bangalore\",\"Country\":\"India\",\"CountrySubDivisionCode\":\"KA\",\"PostalCode\":\"560097\",\"Lat\":\"13.0809029\",\"Long\":\"77.5564981\"},\"DueDate\":\"2015-08-10\",\"TotalAmt\":100055.55,\"HomeTotalAmt\":100055.55,\"ApplyTaxAfterDiscount\":false,\"PrintStatus\":\"NotSet\",\"EmailStatus\":\"EmailSent\",\"BillEmail\":{\"Address\":\"javasdk-fake-email@gmail.com\"},\"Balance\":100055.55,\"HomeBalance\":100055.55,\"DeliveryInfo\":{\"DeliveryType\":\"Email\",\"DeliveryTime\":\"2015-08-10T16:30:47-07:00\"}},\"time\":\"2015-08-12T11:08:01.753-07:00\"}";
    }

    private String getJournalCode()
    {
        return "{\"JournalCode\":{\"Name\":\"JC49b8b075-7be2-4\",\"Type\":\"Cash\",\"Description\":\"Journal code for JC49b8b075-7be2-4\",\"Active\":true,\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"4\",\"SyncToken\":\"0\"},\"time\":\"2015-08-18T15:41:32.619-07:00\"}";
    }


    private String getPayloadWithAmount()
    {
        return "{\"Transfer\":{\"ToAccountRef\":{\"value\":\"30\"},\"FromAccountRef\":{\"value\":\"29\"},\"Amount\":100.00,\"status\":\"Draft\",\"Id\":\"11\",\"SyncToken\":\"0\",\"TxnDate\":\"2015-05-22\"},\"time\":\"2015-05-22T11:47:34.815-07:00\"}";
    }

    private String getBatchItemPayload(){
        return "{\"BatchItemResponse\":[{\"Deposit\":{\"DepositToAccountRef\":{\"value\":\"411\",\"name\":\"BankAccount98ab4\"},\"CashBack\":{\"AccountRef\":{\"value\":\"194\",\"name\":\"Bank (1067562021)\"},\"Amount\":5.00,\"Memo\":\"testLinkedTxn\"},\"TotalAmt\":6.00,\"HomeTotalAmt\":6.00,\"domain\":\"QBO\",\"sparse\":false,\"Id\":\"971\",\"SyncToken\":\"219\",\"MetaData\":{\"CreateTime\":\"2015-08-24T13:47:42-07:00\",\"LastUpdatedTime\":\"2015-08-24T13:47:42-07:00\"},\"TxnDate\":\"2015-08-24\",\"CurrencyRef\":{\"value\":\"USD\",\"name\":\"United States Dollar\"},\"ExchangeRate\":1,\"Line\":[{\"Id\":\"1\",\"LineNum\":1,\"Amount\":11.00,\"DetailType\":\"DepositLineDetail\",\"DepositLineDetail\":{\"AccountRef\":{\"value\":\"94\",\"name\":\"Acct1044418638944214883\"},\"PaymentMethodRef\":{\"value\":\"1\",\"name\":\"Cash\"}}}]},\"bId\":\"1\"},{\"QueryResponse\":{\"Deposit\":[{\"DepositToAccountRef\":{\"value\":\"410\",\"name\":\"BankAccount169ba\"},\"CashBack\":{\"AccountRef\":{\"value\":\"194\",\"name\":\"Bank (1067562021)\"},\"Amount\":5.00,\"Memo\":\"testLinkedTxn\"},\"TotalAmt\":6.00,\"sparse\":true,\"Id\":\"967\",\"Line\":[{\"DetailType\":\"DepositLineDetail\",\"DepositLineDetail\":{\"AccountRef\":{\"value\":\"94\",\"name\":\"Acct1044418638944214883\"},\"PaymentMethodRef\":{\"value\":\"1\",\"name\":\"Cash\"}}}]}],\"startPosition\":1,\"maxResults\":1,\"totalCount\":1},\"bId\":\"2\"}],\"time\":\"2015-08-24T13:47:42.737-07:00\"}";
    }

    private String getMultiAttachablePayload(){
        return " {\"AttachableResponse\":[{\"Fault\":{\"Error\":[{\"Message\":\"Invalid Company Status\",\"Detail\":\"Subscription period has ended or canceled or there was a billing problem : You can't add data to QuickBooks Online Plus because your trial or subscription period ended, you canceled your subscription, or there was a billing problem. To see or change your subscription status, click 'Your Account' at the upper-right of the screen, and click on Resubscribe link or contact your administrator.\",\"code\":\"6190\",\"element\":\"\"}],\"type\":\"ValidationFault\"}},{\"Fault\":{\"Error\":[{\"Message\":\"Invalid Company Status\",\"Detail\":\"Subscription period has ended or canceled or there was a billing problem : You can't add data to QuickBooks Online Plus because your trial or subscription period ended, you canceled your subscription, or there was a billing problem. To see or change your subscription status, click 'Your Account' at the upper-right of the screen, and click on Resubscribe link or contact your administrator.\",\"code\":\"6190\",\"element\":\"\"}],\"type\":\"ValidationFault\"}},{\"Fault\":{\"Error\":[{\"Message\":\"Invalid Company Status\",\"Detail\":\"Subscription period has ended or canceled or there was a billing problem : You can't add data to QuickBooks Online Plus because your trial or subscription period ended, you canceled your subscription, or there was a billing problem. To see or change your subscription status, click 'Your Account' at the upper-right of the screen, and click on Resubscribe link or contact your administrator.\",\"code\":\"6190\",\"element\":\"\"}],\"type\":\"ValidationFault\"}},{\"Fault\":{\"Error\":[{\"Message\":\"Invalid Company Status\",\"Detail\":\"Subscription period has ended or canceled or there was a billing problem : You can't add data to QuickBooks Online Plus because your trial or subscription period ended, you canceled your subscription, or there was a billing problem. To see or change your subscription status, click 'Your Account' at the upper-right of the screen, and click on Resubscribe link or contact your administrator.\",\"code\":\"6190\",\"element\":\"\"}],\"type\":\"ValidationFault\"}},{\"Fault\":{\"Error\":[{\"Message\":\"Invalid Company XXXX\",\"Detail\":\"Subscription period has ended or canceled or there was a billing problem : You can't add data to QuickBooks Online Plus because your trial or subscription period ended, you canceled your subscription, or there was a billing problem. To see or change your subscription status, click 'Your Account' at the upper-right of the screen, and click on Resubscribe link or contact your administrator.\",\"code\":\"6190\",\"element\":\"\"}],\"type\":\"ValidationFault\"}}],\"time\":\"2015-10-01T19:22:00.173-07:00\"}";
    }


    @Test
    public void testMoneyExtraction() throws SerializationException
    {

        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getPayloadWithAmount(), IntuitResponse.class);
        BigDecimal result = ((Transfer) intuitResponse.getIntuitObject().getValue()).getAmount();
        Assert.assertEquals(result, new BigDecimal("100.00"));
    }

    @Test
    public void testMoneyExraction_Nofeature() throws SerializationException
    {
        Config.setProperty(Config.BIGDECIMAL_SCALE_SHIFT, "false");
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getPayloadWithAmount(), IntuitResponse.class);
        BigDecimal result = ((Transfer) intuitResponse.getIntuitObject().getValue()).getAmount();
        Assert.assertEquals(result, new BigDecimal("100.0"));
        Config.setProperty(Config.BIGDECIMAL_SCALE_SHIFT, "true");
    }

    @Test
    public void testBatchJsonDeserializer() throws SerializationException{
        IntuitResponse intuitResponse = null;
        intuitResponse = (IntuitResponse) jsonObj.deserialize(getBatchItemPayload(), IntuitResponse.class);
        Deposit deposit = (Deposit)intuitResponse.getBatchItemResponse().get(0).getIntuitObject().getValue();;
        BigDecimal totalAmount = deposit.getTotalAmt();
        Assert.assertEquals(totalAmount, new BigDecimal("6.00"));
    }

    @SuppressWarnings("unchecked")
	private <T extends Object> Object getSerializableRequestObject(T object) throws FMSException {
		Class<?> objectClass = object.getClass();
		String methodName = "create".concat(objectClass.getSimpleName());
		ObjectFactory objectEntity = new ObjectFactory();
		Class<?> objectEntityClass = objectEntity.getClass();

		Method method = null;
		try {
			method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
		} catch (Exception e) {
			throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
		}
		JAXBElement<? extends Object> jaxbElement = null;
		try {
			jaxbElement = (JAXBElement<? extends Object>) method.invoke(objectEntity, object);
		} catch (Exception e) {
			throw new FMSException("Exception while prepare the method signature using reflection to generate JAXBElement", e);
		}

		return jaxbElement;
	}
}
