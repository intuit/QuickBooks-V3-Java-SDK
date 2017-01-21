package com.intuit.ipp.example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Account;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.BatchOperation;
import com.intuit.ipp.services.CDCQueryResult;
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.DateUtils;

public class CDCQueryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
				String consumerKey = "replace consumer key here";
				String consumerSecret = "replace consumer secret here";
				String accessToken = "replace acces token  here";
				String accessTokenSecret = "replace access token secret here";	
				String appDBId = "QBD";
		String realmID = "replace company id here";
		Config.setProperty(Config.BASE_URL_QBD, "https://qb.sbfinance.stage.intuit.com/v3/company");

		OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		//String appDBId = "b7nmwqt6hr";
		//String realmID = "674392905";
		Context context = null;

		try {
			context = new Context(oauth, ServiceType.QBD, realmID);

			DataService service = new DataService(context);

			executeCDCQuery(service);
			executeCDCQueryBatch(service);
			executeCDCQueryAsync(service);
			
		} catch (FMSException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private static void executeCDCQuery(DataService service) throws ParseException, FMSException {
		System.out.println("In executeCDCQuery..... ");
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		List<CDCQueryResult> cdcQueryResults = service.executeCDCQuery(entities, changedSince);
		CDCQueryResult cdcQueryResult = cdcQueryResults.get(0);
		
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		
		// get customer records
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Iterator<? extends IEntity> customerItr = customerEntities.iterator();
		while (customerItr.hasNext()) {
			Customer customer = (Customer) customerItr.next();
			System.out.println("Customer : " + customer.getDisplayName());
		}
		
		// get account records
		List<? extends IEntity> accountEntities = queryResults.get("Account").getEntities();
		Iterator<? extends IEntity> accountItr = accountEntities.iterator();
		while (accountItr.hasNext()) {
			Account account = (Account) accountItr.next();
			System.out.println("Account : " + account.getName());
		}
	}

	private static void executeCDCQueryBatch(DataService service) throws ParseException, FMSException {
		System.out.println("In executeCDCQueryBatch..... ");
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		BatchOperation batchOperation = new BatchOperation();
		batchOperation.addCDCQuery(entities, changedSince, "1");
		
		service.executeBatch(batchOperation);
		
		CDCQueryResult cdcQueryResult = batchOperation.getCDCQueryResult("1");
		
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		
		// get customer records
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Iterator<? extends IEntity> customerItr = customerEntities.iterator();
		while (customerItr.hasNext()) {
			Customer customer = (Customer) customerItr.next();
			System.out.println("Customer : " + customer.getDisplayName());
		}
		
		// get account records
		List<? extends IEntity> accountEntities = queryResults.get("Account").getEntities();
		Iterator<? extends IEntity> accountItr = accountEntities.iterator();
		while (accountItr.hasNext()) {
			Account account = (Account) accountItr.next();
			System.out.println("Account : " + account.getName());
		}
	}
	
	private static void executeCDCQueryAsync(DataService service) throws ParseException, FMSException {
		System.out.println("In executeCDCQueryAsync..... ");
		List<IEntity> entities = new ArrayList<IEntity>();	
		entities.add(new Customer());
		entities.add(new Account());
		
		String changedSince = DateUtils.getStringFromDateTime(DateUtils.getDateWithPrevDays(2));
		
		service.executeCDCQueryAsync(entities, changedSince, new AsyncCallBackCDCQuery());
	}
	
}

class AsyncCallBackCDCQuery implements CallbackHandler {

	@Override
	public void execute(CallbackMessage callbackMessage) {
		System.out.println("In AsyncCallBackCDCQuery callback...");
		List<CDCQueryResult> cdcQueryResults = callbackMessage.getCDCQueryResults();
		CDCQueryResult cdcQueryResult = cdcQueryResults.get(0);
		
		Map<String, QueryResult> queryResults = cdcQueryResult.getQueryResults();
		
		// get customer records
		List<? extends IEntity> customerEntities = queryResults.get("Customer").getEntities();
		Iterator<? extends IEntity> customerItr = customerEntities.iterator();
		while (customerItr.hasNext()) {
			Customer customer = (Customer) customerItr.next();
			System.out.println("Customer : " + customer.getDisplayName());
		}
		
		// get account records
		List<? extends IEntity> accountEntities = queryResults.get("Account").getEntities();
		Iterator<? extends IEntity> accountItr = accountEntities.iterator();
		while (accountItr.hasNext()) {
			Account account = (Account) accountItr.next();
			System.out.println("Account : " + account.getName());
		}
	}

}
