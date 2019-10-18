package com.intuit.ipp.services;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.BatchItemRequest;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.OperationEnum;
import com.intuit.ipp.exception.FMSException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BatchOperationTest {

    private BatchOperation batchOperation;

    @BeforeMethod
    public void setUp() {
        batchOperation = new BatchOperation();
    }

    @Test
    void testAddEntity() {
        int batchSize = 2;

        for(int i=0; i<batchSize; i++) {
            String bid = "123" + i;
            Customer entity = new Customer();
            OperationEnum create = OperationEnum.CREATE;

            batchOperation.addEntity(entity, create, bid);

            Assert.assertNotNull(batchOperation.getBIds());
            Assert.assertNotNull(batchOperation.getBatchItemRequests());
            Assert.assertEquals(batchOperation.getBIds().size(), i+1);
            Assert.assertEquals(batchOperation.getBatchItemRequests().size(), i+1);

            BatchItemRequest batchItemRequest = batchOperation.getBatchItemRequests().get(i);
            Assert.assertNotNull(batchItemRequest);
            Assert.assertEquals(batchItemRequest.getBId(), bid);
            Assert.assertNotNull(batchItemRequest.getIntuitObject());
            Assert.assertEquals(batchItemRequest.getOperation(), create);

            List<String> bids = batchOperation.getBIds();
            Assert.assertNotNull(bids);
            Assert.assertEquals(bids.size(), i+1);
        }
    }

    @Test
    void testAddQuery() {
        int batchSize = 2;

        for(int i=0; i<batchSize; i++) {
            String bid = "123" + i;
            String query = "Select * from Invoice " + i;

            batchOperation.addQuery(query, bid);

            Assert.assertNotNull(batchOperation.getBIds());
            Assert.assertNotNull(batchOperation.getBatchItemRequests());
            Assert.assertEquals(batchOperation.getBIds().size(), i+1);
            Assert.assertEquals(batchOperation.getBatchItemRequests().size(), i+1);

            BatchItemRequest batchItemRequest = batchOperation.getBatchItemRequests().get(i);
            Assert.assertNotNull(batchItemRequest);
            Assert.assertEquals(batchItemRequest.getBId(), bid);
            Assert.assertNotNull(batchItemRequest.getQuery());
            Assert.assertEquals(batchItemRequest.getQuery(), query);

            List<String> bids = batchOperation.getBIds();
            Assert.assertNotNull(bids);
            Assert.assertEquals(bids.size(), i+1);
        }
    }

    @Test
    void testReportQuery() {
        int batchSize = 2;

        for(int i=0; i<batchSize; i++) {
            String bid = "123" + i;
            String reportQuery = "sample report query " + i;

            batchOperation.addReportQuery(reportQuery, bid);

            Assert.assertNotNull(batchOperation.getBIds());
            Assert.assertNotNull(batchOperation.getBatchItemRequests());
            Assert.assertEquals(batchOperation.getBIds().size(), i+1);
            Assert.assertEquals(batchOperation.getBatchItemRequests().size(), i+1);

            BatchItemRequest batchItemRequest = batchOperation.getBatchItemRequests().get(i);
            Assert.assertNotNull(batchItemRequest);
            Assert.assertEquals(batchItemRequest.getBId(), bid);
            Assert.assertNotNull(batchItemRequest.getReportQuery());
            Assert.assertEquals(batchItemRequest.getReportQuery(), reportQuery);

            List<String> bids = batchOperation.getBIds();
            Assert.assertNotNull(bids);
            Assert.assertEquals(bids.size(), i+1);
        }
    }

    @Test(expectedExceptions = FMSException.class)
    void testCDCQuery_NullEntities() throws FMSException {
        batchOperation.addCDCQuery(null, "", "");
    }

    @Test(expectedExceptions = FMSException.class)
    void testCDCQuery_EmptyEntities() throws FMSException {
        List<IEntity> empty = new ArrayList<>();
        batchOperation.addCDCQuery(empty, "", "");
    }

    @Test(expectedExceptions = FMSException.class)
    void testCDCQuery_NullCDCDate() throws FMSException {
        List<IEntity> entities = new ArrayList<>();
        entities.add(new Account());
        entities.add(new Attachable());

        batchOperation.addCDCQuery(entities, null, "");
    }

    @Test(expectedExceptions = FMSException.class)
    void testCDCQuery_EmptyCDCDate() throws FMSException {
        List<IEntity> entities = new ArrayList<>();
        entities.add(new Account());
        entities.add(new Attachable());

        batchOperation.addCDCQuery(entities, "", "");
    }

    @Test(expectedExceptions = FMSException.class)
    void testCDCQuery_InvalidDate() throws FMSException {
        String invalidDate = "2000~01~01";

        List<IEntity> entities = new ArrayList<>();
        entities.add(new Account());
        entities.add(new Attachable());

        batchOperation.addCDCQuery(entities, invalidDate, "");
    }

    @Test
    void testCDCQuery() throws FMSException {
        String bid = "123";
        String cdcDate = "2000-01-01T00:00:00.000-0700";

        List<IEntity> entities = new ArrayList<>();
        entities.add(new Account());
        entities.add(new Attachable());

        batchOperation.addCDCQuery(entities, cdcDate, bid);

        Assert.assertNotNull(batchOperation.getBIds());
        Assert.assertNotNull(batchOperation.getBatchItemRequests());
        Assert.assertEquals(batchOperation.getBIds().size(), 1);
        Assert.assertEquals(batchOperation.getBatchItemRequests().size(), 1);

        BatchItemRequest batchItemRequest = batchOperation.getBatchItemRequests().get(0);
        Assert.assertNotNull(batchItemRequest);
        Assert.assertEquals(batchItemRequest.getBId(), bid);
        Assert.assertNotNull(batchItemRequest.getCDCQuery());
        Assert.assertNotEquals(batchItemRequest.getCDCQuery().getEntities(), Account.class.getSimpleName() + Attachable.class.getSimpleName());

        List<String> bids = batchOperation.getBIds();
        Assert.assertNotNull(bids);
        Assert.assertEquals(bids.size(), 1);
    }
}