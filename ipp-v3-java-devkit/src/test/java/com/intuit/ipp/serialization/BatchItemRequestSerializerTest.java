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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.CDCQuery;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.ObjectFactory;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.Vendor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jakarta.xml.bind.JAXBElement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class BatchItemRequestSerializerTest {

    private BatchItemRequestSerializer serializer;
    private JsonFactory factory;
    private Gson gson;

    @BeforeClass
    public void setUp() {
        serializer = new BatchItemRequestSerializer();
        factory = new JsonFactory();
        gson = new Gson();
    }

    @Test
    public void testBatchSerialize_CustomerCreate() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        Customer customer = getCustomerObject();

        request.setBId("bID1");
        JAXBElement element = getJaxBElementObject(customer);
        request.setIntuitObject(element);
        request.setOperation(OperationEnum.CREATE);

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"bId\":\"bID1\",\"Customer\":{\"Title\":\"Mr.\",\"GivenName\":\"John\",\"MiddleName\":\"David\",\"FamilyName\":\"Thomson\"},\"operation\":\"create\"}");
    }

    @Test
    public void testBatchSerialize_VendorCreate() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        Vendor vendor = getVendorObject();

        JAXBElement element = getJaxBElementObject(vendor);
        request.setIntuitObject(element);
        request.setOperation(OperationEnum.CREATE);

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"Vendor\":{\"Title\":\"Ms.\",\"GivenName\":\"Penny\",\"MiddleName\":\"Kaley\",\"FamilyName\":\"Cuoco\"},\"operation\":\"create\"}");
    }

    @Test
    public void testBatchSerialize_InvoiceUpdate() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        Invoice invoice = getInvoiceObject();

        request.setBId("bID3");
        JAXBElement element = getJaxBElementObject(invoice);
        request.setIntuitObject(element);
        request.setOperation(OperationEnum.UPDATE);

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"bId\":\"bID3\",\"Invoice\":{\"DocNumber\":\"1001\",\"TxnDate\":\"2019-01-01\",\"Line\":[{\"Description\":\"Buttons\",\"Amount\":1000}],\"CustomerRef\":{\"value\":\"33\",\"name\":\"Account Receivable\",\"type\":\"Account\"}},\"operation\":\"update\"}");
    }

    @Test
    public void testBatchSerialize_QueryInvoice() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        request.setBId("bID4");
        request.setQuery("select * from Invoice");
        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"bId\":\"bID4\",\"Query\":\"select * from Invoice\"}");
    }

    @Test
    public void testBatchSerialize_CustomerDelete() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        Customer customer = getCustomerObject();
        customer.setId("34");

        request.setBId("bID5");
        JAXBElement element = getJaxBElementObject(customer);
        request.setIntuitObject(element);
        request.setOperation(OperationEnum.DELETE);

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"bId\":\"bID5\",\"Customer\":{\"Id\":\"34\",\"Title\":\"Mr.\",\"GivenName\":\"John\",\"MiddleName\":\"David\",\"FamilyName\":\"Thomson\"},\"operation\":\"delete\"}");
    }

    @Test
    public void testBatchSerialize_CDCQuery() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        CDCQuery cdcQuery = new CDCQuery();
        cdcQuery.setEntities("Customer");
        cdcQuery.setChangedSince(getDate());
        request.setBId("bID6");
        request.setCDCQuery(cdcQuery);

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        JsonObject jsonObject = gson.fromJson(output, JsonObject.class);
        Assert.assertNotNull(output);
        Assert.assertEquals(jsonObject.get("bId").getAsString(), "bID6");
        JsonObject cdcQueryJson = jsonObject.get("CDCQuery").getAsJsonObject();
        Assert.assertNotNull(cdcQueryJson);
        Assert.assertEquals(cdcQueryJson.get("Entities").getAsString(), "Customer");
        Assert.assertEquals(cdcQueryJson.get("ChangedSince").getAsString().split("T")[0], "2019-01-01");
    }

    @Test
    public void testBatchSerialize_ReportQuery() throws IOException {
        BatchItemRequest request = new BatchItemRequest();
        OutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = factory.createGenerator(outputStream);

        request.setBId("bID6");
        request.setReportQuery("report queryData");
        request.setOptionsData("testOptionsData");

        serializer.serialize(request, generator, null);

        generator.flush();
        String output = outputStream.toString();
        Assert.assertEquals(output, "{\"bId\":\"bID6\",\"ReportQuery\":\"report queryData\",\"optionsData\":\"testOptionsData\"}");
    }


    private Customer getCustomerObject() {
        Customer customer = new Customer();
        customer.setGivenName("John");
        customer.setTitle("Mr.");
        customer.setFamilyName("Thomson");
        customer.setMiddleName("David");

        return customer;
    }

    private Vendor getVendorObject() {
        Vendor vendor = new Vendor();
        vendor.setGivenName("Penny");
        vendor.setTitle("Ms.");
        vendor.setMiddleName("Kaley");
        vendor.setFamilyName("Cuoco");

        return vendor;
    }

    private Invoice getInvoiceObject() {
        Invoice invoice = new Invoice();
        invoice.setDocNumber("1001");
        invoice.setTxnDate(getDate());
        ReferenceType customerRef = new ReferenceType();
        customerRef.setType("Customer");
        customerRef.setName("Mary");
        customerRef.setValue("1");
        invoice.setCustomerRef(customerRef);

        ReferenceType accountRef = new ReferenceType();
        accountRef.setName("Account Receivable");
        accountRef.setType("Account");
        accountRef.setValue("33");
        invoice.setCustomerRef(accountRef);

        Line invLine = new Line();
        invLine.setAmount(new BigDecimal(1000));
        invLine.setDescription("Buttons");
        invoice.setLine(Collections.singletonList(invLine));

        return invoice;
    }

    private Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "01-01-2019 00:00:00";
        Date date;
         Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(dateInString);
            calendar.setTime(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    @SuppressWarnings("unchecked")
    private <T> JAXBElement<? extends Object>  getJaxBElementObject(T object) {
        Class<?> objectClass = object.getClass();
        String methodName = "create".concat(objectClass.getSimpleName());
        ObjectFactory objectEntity = new ObjectFactory();
        Class<?> objectEntityClass = objectEntity.getClass();

        Method method;
        JAXBElement<? extends Object> jaxbElement = null;
        try {
            method = objectEntityClass.getMethod(methodName, Class.forName(objectClass.getName()));
            jaxbElement = (JAXBElement<? extends Object>) method.invoke(objectEntity, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jaxbElement;
    }
}
