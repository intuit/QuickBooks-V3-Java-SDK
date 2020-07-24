package com.intuit.ipp.services;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.*;
import com.intuit.ipp.data.Error;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuth2Authorizer;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.util.Logger;

import javax.xml.bind.JAXBElement;
import java.text.ParseException;
import java.util.List;

public class ShwetaTest {

    protected static DataService service = null;
    private static final org.slf4j.Logger LOG = Logger.getLogger();
    private static String bearerToken = "eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiZGlyIn0..nPVI8fVzNjf6c7FNX1Xrwg.N0UN-P48eRDceYBj37-FiOKQ2pI2KfXvB_lsMhE7VUnvff6gzeIsohv-3CXsyGeJnhKbVALX2DX0yjw0xXPjywllqUqNjr_3jc2QLZ4WKsofcFp7lpsC6RFuWH6ducycr5cjhLP4XIqYu1FhtFLoTaNZq-T39ynBv7lY0uERZ6aQjnzfI59WgJTbkNf-3olJggJi8IOJSKN-nOJbpsR6VVbNHwkuwH2GtwWn6rtGKZhLKzApbI3m-Px-L6wdll03Wk-_n3F-VOCIwLv_lUk_VgfD3a3MNbHUk8qyIgDQtg3yCgopYMerV44yzIi0xVKq9mr0rItT8gnWECaLtW4ZwzBssOINKf-ONWOSjUlaAEYmuA7w9FREbee_moQUpiWXEZhVPrALMYpB0iULVJOC86TBavjiFOnVu2Ue3CwxSOCUFCf7yMAMqlGTnaDfEheogAo0AlxFUA3LYgKk9PSldxhgoROJst-BCsFeRIS2EhLgqIUf-CfLHeO3O62Mp7XHF_ZiKKiyGzcjyvlA5US3_d1s7S402VJGT8LVgDseS-3CytIJP1i69hmNGS8tagPBVVnI9I1TJ3GZaa8IrbDP1-WKv1XwJOZOf5cIvxFmtceS_GcMA0C1scumsgDiKTB78NmqL2NAvt0KTZgNKNzxRIWy1BKeSIalxSFg6wEGtXg7LcuqLwzC6uUvr8jbV9grM6PTH4uZ3WzmvQjoh7lrFb_4q0unDgLzBT9dYcXvuX1d9EcYwWxRj_mQU7bHTzBcjfmrozdpYw6Pf4XIaYXl2w5U99UTfG-xO3nX52aqxIc.KDp822XhLM26GFAk4vzJqg";
    private static String companyID  = "123146217387174";

    public static void main(String[] args) {

        try {
            OAuth2Authorizer oauth = new OAuth2Authorizer(bearerToken);
            Context context = new Context(oauth, ServiceType.QBO, companyID);
            service = new DataService(context);

            queryRecurringTxn();
        } catch (Exception e) {
            LOG.error("", e.getMessage());
            LOG.error("Error during CRUD", e.getCause());
        }
    }


    public static void queryRecurringTxn() throws Exception {

//
        try {

            String sql = "select * from RecurringTransaction";
           // String sql = "select * from Invoice maxresults 2";

            QueryResult queryResult = service.executeQuery(sql);
            int count = queryResult.getEntities().size();


            RecurringTransaction txn = (RecurringTransaction)queryResult.getEntities().get(0);

            LOG.info("Transaction: " + txn.getIntuitObject().getValue());


            //Invoice txn = (Invoice)queryResult.getEntities().get(0);
//            LOG.info("RecurringTransaction " + txn);
//            LOG.info("RecurringTransaction " + txn.getIntuitObject().getName());
//            LOG.info("RecurringTransaction " + txn.getIntuitObject().getDeclaredType());
//            LOG.info("RecurringTransaction " + txn.getIntuitObject().getValue());
//
//
//            Invoice i = (Invoice)txn.getIntuitObject().getValue();
//            IntuitEntity i2 = (IntuitEntity)txn.getIntuitObject().getValue();



            LOG.info("Invoice " + txn.getId());

        }

        catch (FMSException e) {
            LOG.error("FMSException: " + e.getMessage() , e.getMessage());
        }
        catch (Exception e) {
            LOG.error("Exception: " + e.getMessage() , e.getMessage());
        }
    }
}
