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

import java.util.Map;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.data.Report;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.interceptors.IntuitInterceptorProvider;
import com.intuit.ipp.interceptors.IntuitMessage;
import com.intuit.ipp.interceptors.RequestElements;
import com.intuit.ipp.net.MethodType;
import com.intuit.ipp.net.OperationType;

/**
 *
 */
public class ReportService {
	

	
	/**
	 * variable context
	 */
	private transient Context context = null;

    /**
     * variable report_date
     */
    private String report_date = null;

	/**
	 * variable start_date
	 */
	private String start_date = null;
	
	/**
	 * variable end_date
	 */
	private  String end_date = null;
	
	/**
	 * variable date_macro
	 */
	private  String date_macro = null;

    /**
     * variable past_due
     */
    private  String past_due = null;

    /**
     * variable end_duedate
     */
    private  String end_duedate = null;

    /**
     * variable start_duedate
     */
    private  String start_duedate = null;

    /**
     * variable duedate_macro
     */
    private  String duedate_macro = null;

    /**
	 * variable accounting_method
	 */
	private  String accounting_method = null;

    /**
     * variable account
     */
    private  String account = null;

    /**
     * variable source_account
     */
    private  String source_account = null;

    /**
     * variable account_type
     */
    private  String account_type = null;

    /**
     * variable source_account_type
     */
    private  String source_account_type = null;

    /**
	 * variable summarize_column_by
	 */
	private  String summarize_column_by = null;
	
	/**
	 * variable customer
	 */
	private  String customer = null;
	
	/**
	 * variable vendor
	 */
	private  String vendor = null;
	
	/**
	 * variable item
	 */
	private  String item = null;
	
	/**
	 * variable class
	 */
	private  String classid = null;

    /**
     * variable appaid
     */
    private  String appaid = null;
	
	/**
	 * variable department
	 */
	private  String department = null;
	
	/**
	 * variable qzurl
	 */
	private  String qzurl = null;
	
	/**
	 * variable aging_period
	 */
	private  String aging_period = null;

    /**
     * variable aging_method
     */
    private  String aging_method = null;

    /**
	 * variable num_periods
	 */
	private  String num_periods = null;

    /**
     * variable term
     */
    private  String term = null;
    /**
     * variable columns
     */
    private  String columns = null;

    /**
     * variable sort_by
     */
    private  String sort_by = null;

    /**
     * variable sort_order
     */
    private  String sort_order = null;

    /**
     * variable group_by
     */
    private  String group_by = null;
    /**
     * variable createdate_macro
     */
    private  String createdate_macro = null;
    /**
     * variable end_createdate
     */
    private  String end_createdate = null;
    /**
     * variable start_createdate
     */
    private  String start_createdate = null;
    /**
     * variable moddate_macro
     */
    private  String moddate_macro = null;
    /**
     * variable end_moddate
     */
    private  String end_moddate = null;
    /**
     * variable start_moddate
     */
    private  String start_moddate = null;
    /**
     * variable payment_method
     */
    private  String payment_method = null;
    /**
     * variable name
     */
    private  String name = null;
    /**
     * variable transaction_type
     */
    private  String transaction_type = null;
    /**
     * variable cleared
     */
    private  String cleared = null;
    /**
     * variable arpaid
     */
    private  String arpaid = null;
    /**
     * variable printed
     */
    private  String printed = null;
    /**
     * variable both_amount
     */
    private  String both_amount = null;
    /**
     * variable memo
     */
    private  String memo = null;
    /**
     * variable doc_num
     */
    private  String doc_num = null;
    
    private  String journal_code = null;
    
    private  String employee = null;
    
    private  String agency_id = null;
    
    private  String custom1 = null;
    
    private  String custom2 = null;
    
    private  String custom3 = null;
    
    private  String shipvia = null;
    
    private  String account_status = null;



    /**
	 * Hiding the default constructor as Context is always required to function properly
	 */
	protected ReportService() {
		
	}
	
	/**
	 * Constructor DataService
	 * 
	 * @param context
	 *            the context
	 */
	public ReportService(final Context context) {
		this.context = context;
	}

	/**
	 * Method to retrieve records for the given list of query
	 * 
	 * @param reportName
	 *            the query string
	 * @return query result
	 * @throws FMSException
	 *             throws FMSException
	 */
	public Report executeReport(String reportName) throws FMSException {

		IntuitMessage intuitMessage = prepareReport(reportName);

		// execute interceptors
		executeInterceptors(intuitMessage);

		Report report = null;

		// Iterate the IntuitObjects list in QueryResponse and convert to <T> entity 
		IntuitResponse intuitResponse = (IntuitResponse) intuitMessage.getResponseElements().getResponse();
		if (intuitResponse != null) {
			report = intuitResponse.getReport();
		}
		return report;
	}

    /**
     * Invokes interceptors, which perform networking operations (serialization, compression, connection etc)
     * @param intuitMessage
     * @throws FMSException
     */
    protected void executeInterceptors(IntuitMessage intuitMessage) throws FMSException {
      new IntuitInterceptorProvider().executeInterceptors(intuitMessage);
    }


	/**
	 * Common method to prepare the request params for Reports and for both sync and async calls
	 * 
	 * @param reportName
	 *            
	 * @return IntuitMessage the intuit message
	 * @throws FMSException
	 */
	private IntuitMessage prepareReport(String reportName) throws FMSException {
		IntuitMessage intuitMessage = new IntuitMessage();
		RequestElements requestElements = intuitMessage.getRequestElements();

		//set the request params
		Map<String, String> requestParameters = requestElements.getRequestParameters();
			requestParameters.put(RequestElements.REQ_PARAM_METHOD_TYPE, MethodType.GET.toString());

        if (getReport_date() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_REPORT_DT, getReport_date());
        }
			if (getStart_date() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_START_DT, getStart_date());
			}
			if (getEnd_date() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_END_DT, getEnd_date());
			}
			if (getDate_macro() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_DT_MACRO, getDate_macro());
			}
        if (getPast_due() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_PAST_DUE, getPast_due());
        }
        if (getStart_duedate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_START_DUE_DT, getStart_duedate());
        }
        if (getEnd_duedate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_END_DUE_DT, getEnd_duedate());
        }
        if (getDuedate_macro() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_DUE_DT_MACRO, getDuedate_macro());
        }

			if (getAccounting_method() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_ACC_METHOD, getAccounting_method());
			}
        if (getAccount() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_ACCOUNT, getAccount());
        }
        if (getSource_account() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_SOURCE_ACCOUNT, getSource_account());
        }
        if (getAccount_type() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_ACCOUNT_TYPE, getAccount_type());
        }
        if (getSource_account_type() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_SOURCE_ACCOUNT_TYPE, getSource_account_type());
        }

			if (getSummarize_column_by() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_SUM_COL_BY, getSummarize_column_by());
			}
			if (getCustomer() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_CUSTOMER, getCustomer());
			}
			if (getVendor() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_VENDOR, getVendor());
			}
			if (getItem() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_ITEM, getItem());
			}
			if (getClassid() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_CLASS, getClassid());
			}
        if (getAppaid() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_APPAID, getAppaid());
        }
			if (getDepartment() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_DEPARTMENT, getDepartment());
			}
			if (getQzurl() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_QZURL, getQzurl());
			}
			if (getAging_period() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_AGING_PERIOD, getAging_period());
			}
        if (getAging_method() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_AGING_METHOD, getAging_method());
        }
			if (getNum_periods() != null)
			{
				requestParameters.put(RequestElements.REPORT_PARAM_NUM_PERIOD, getNum_periods());
			}
        if (getTerm() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_TERM, getTerm());
        }
        if (getColumns() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_COLUMNS, getColumns());
        }
        if (getSort_by() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_SORT_BY, getSort_by());
        }
        if (getSort_order() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_SORT_ORDER, getSort_order());
        }
        if (getGroup_by() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_GROUP_BY, getGroup_by());
        }

        if (getCreatedate_macro() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_CREATE_DT_MACRO, getCreatedate_macro());
        }
        if (getEnd_createdate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_END_CREATED_DT, getEnd_createdate());
        }
        if (getStart_createdate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_START_CREATED_DT, getStart_createdate());
        }
        if (getModdate_macro() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_MOD_DT_MACRO, getModdate_macro());
        }
        if (getEnd_moddate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_END_MOD_DT, getEnd_moddate());
        }
        if (getStart_moddate() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_START_MOD_DT, getStart_moddate());
        }
        if (getPayment_method() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_PAYMENT_METHOD, getPayment_method());
        }
        if (getName() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_NAME, getName());
        }
        if (getTransaction_type() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_TRANSACTION_TYPE, getTransaction_type());
        }
        if (getCleared() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_CLEARED, getCleared());
        }
        if (getArpaid() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_ARPAID, getArpaid());
        }
        if (getPrinted() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_PRINTED, getPrinted());
        }
        if (getBoth_amount() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_BOTH_AMT, getBoth_amount());
        }
        if (getMemo() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_MEMO, getMemo());
        }
        if (getDoc_num() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_DOC_NUM, getDoc_num());
        }
        if (getJournal_code() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_JOURNAL_CODE, getJournal_code());
        }
        if (getEmployee() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_EMPLOYEE, getEmployee());
        }
        if (getAgency_id() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_AGENCY_ID, getAgency_id());
        }
        if (getCustom1() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_CUSTOM1, getCustom1());
        }
        if (getCustom2() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_CUSTOM2, getCustom2());
        }
        if (getCustom3() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_CUSTOM3, getCustom3());
        }
        if (getShipvia() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_SHIPVIA, getShipvia());
        }
        if (getAccount_status() != null)
        {
            requestParameters.put(RequestElements.REPORT_PARAM_ACCOUNT_STATUS, getAccount_status());
        }

			
			requestElements.setAction(OperationType.REPORTS.toString() + "/" + reportName);
			requestElements.setContext(context);

		return intuitMessage;
	}

    /**
     * @return the report_date
     */
    public String getReport_date() {
        return report_date;
    }
    /**
     * @param report_date the report_date to set
     */
    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }
	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the end_date
	 */
	public  String getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date the end_date to set
	 */
	public  void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the date_macro
	 */
	public  String getDate_macro() {
		return date_macro;
	}

	/**
	 * @param date_macro the date_macro to set
	 */
	public  void setDate_macro(String date_macro) {
		this.date_macro = date_macro;
	}

	/**
	 * @return the accounting_method
	 */
	public  String getAccounting_method() {
		return accounting_method;
	}

	/**
	 * @param accounting_method the accounting_method to set
	 */
	public  void setAccounting_method(String accounting_method) {
		this.accounting_method = accounting_method;
	}

	/**
	 * @return the summarize_column_by
	 */
	public  String getSummarize_column_by() {
		return summarize_column_by;
	}

	/**
	 * @param summarize_column_by the summarize_column_by to set
	 */
	public void setSummarize_column_by(String summarize_column_by) {
		this.summarize_column_by = summarize_column_by;
	}

	/**
	 * @return the customer
	 */
	public  String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public  void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the vendor
	 */
	public  String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public  void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the item
	 */
	public  String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public  void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the classid
	 */
	public String getClassid() {
		return classid;
	}

	/**
	 * @param classid the classid to set
	 */
	public  void setClassid(String classid) {
		this.classid = classid;
	}

	/**
	 * @return the department
	 */
	public  String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public  void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the qzurl
	 */
	public  String getQzurl() {
		return qzurl;
	}

	/**
	 * @param qzurl the qzurl to set
	 */
	public  void setQzurl(String qzurl) {
		this.qzurl = qzurl;
	}

	/**
	 * @return the aging_period
	 */
	public  String getAging_period() {
		return aging_period;
	}

	/**
	 * @param aging_period the aging_period to set
	 */
	public  void setAging_period(String aging_period) {
		this.aging_period = aging_period;
	}

	/**
	 * @return the num_periods
	 */
	public  String getNum_periods() {
		return num_periods;
	}

	/**
	 * @param num_periods the num_periods to set
	 */
	public  void setNum_periods(String num_periods) {
		this.num_periods = num_periods;
	}

    /**
     * @return the past_due
     */
    public String getPast_due() {
        return past_due;
    }
    /**
     * @param past_due the past_due to set
     */
    public void setPast_due(String past_due) {
        this.past_due = past_due;
    }

    /**
     * @return the end_duedate
     */
    public String getEnd_duedate() {
        return end_duedate;
    }
    /**
     * @param end_duedate the end_duedate to set
     */
    public void setEnd_duedate(String end_duedate) {
        this.end_duedate = end_duedate;
    }

    /**
     * @return the start_duedate
     */
    public String getStart_duedate() {
        return start_duedate;
    }
    /**
     * @param start_duedate the start_duedate to set
     */
    public void setStart_duedate(String start_duedate) {
        this.start_duedate = start_duedate;
    }

    /**
     * @return the duedate_macro
     */
    public String getDuedate_macro() {
        return duedate_macro;
    }
    /**
     * @param duedate_macro the duedate_macro to set
     */
    public void setDuedate_macro(String duedate_macro) {
        this.duedate_macro = duedate_macro;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }
    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the source_account
     */
    public String getSource_account() {
        return source_account;
    }
    /**
     * @param source_account the source_account to set
     */
    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    /**
     * @return the account_type
     */
    public String getAccount_type() {
        return account_type;
    }
    /**
     * @param account_type the account_type to set
     */
    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    /**
     * @return the source_account_type
     */
    public String getSource_account_type() {
        return source_account_type;
    }
    /**
     * @param source_account_type the source_account_type to set
     */
    public void setSource_account_type(String source_account_type) {
        this.source_account_type = source_account_type;
    }

    /**
     * @return the appaid
     */
    public String getAppaid() {
        return appaid;
    }
    /**
     * @param appaid the appaid to set
     */
    public void setAppaid(String appaid) {
        this.appaid = appaid;
    }

    /**
     * @return the aging_method
     */
    public String getAging_method() {
        return aging_method;
    }
    /**
     * @param aging_method the aging_method to set
     */
    public void setAging_method(String aging_method) {
        this.aging_method = aging_method;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }
    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }
    /**
     * @param columns the columns to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    /**
     * @return the sort_by
     */
    public String getSort_by() {
        return sort_by;
    }
    /**
     * @param sort_by the sort_by to set
     */
    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    /**
     * @return the sort_order
     */
    public String getSort_order() {
        return sort_order;
    }
    /**
     * @param sort_order the sort_order to set
     */
    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    /**
     * @return the group_by
     */
    public String getGroup_by() {
        return group_by;
    }
    /**
     * @param group_by the group_by to set
     */
    public void setGroup_by(String group_by) {
        this.group_by = group_by;
    }
    /**
     * @return the createdate_macro
     */
    public String getCreatedate_macro() {
        return createdate_macro;
    }
    /**
     * @param createdate_macro the createdate_macro to set
     */
    public void setCreatedate_macro(String createdate_macro) {
        this.createdate_macro = createdate_macro;
    }
    /**
     * @return the end_createdate
     */
    public String getEnd_createdate() {
        return end_createdate;
    }
    /**
     * @param end_createdate the end_createdate to set
     */
    public void setEnd_createdate(String end_createdate) {
        this.end_createdate = end_createdate;
    }
    /**
     * @return the start_createdate
     */
    public String getStart_createdate() {
        return start_createdate;
    }
    /**
     * @param start_createdate the start_createdate to set
     */
    public void setStart_createdate(String start_createdate) {
        this.start_createdate = start_createdate;
    }
    /**
     * @return the moddate_macro
     */
    public String getModdate_macro() {
        return moddate_macro;
    }
    /**
     * @param moddate_macro the moddate_macro to set
     */
    public void setModdate_macro(String moddate_macro) {
        this.moddate_macro = moddate_macro;
    }
    /**
     * @return the end_moddate
     */
    public String getEnd_moddate() {
        return end_moddate;
    }
    /**
     * @param end_moddate the end_moddate to set
     */
    public void setEnd_moddate(String end_moddate) {
        this.end_moddate = end_moddate;
    }
    /**
     * @return the start_moddate
     */
    public String getStart_moddate() {
        return start_moddate;
    }
    /**
     * @param start_moddate the start_moddate to set
     */
    public void setStart_moddate(String start_moddate) {
        this.start_moddate = start_moddate;
    }
    /**
     * @return the payment_method
     */
    public String getPayment_method() {
        return payment_method;
    }
    /**
     * @param payment_method the payment_method to set
     */
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the transaction_type
     */
    public String getTransaction_type() {
        return transaction_type;
    }
    /**
     * @param transaction_type the transaction_type to set
     */
    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
    /**
     * @return the cleared
     */
    public String getCleared() {
        return cleared;
    }
    /**
     * @param cleared the cleared to set
     */
    public void setCleared(String cleared) {
        this.cleared = cleared;
    }
    /**
     * @return the arpaid
     */
    public String getArpaid() {
        return arpaid;
    }
    /**
     * @param arpaid the arpaid to set
     */
    public void setArpaid(String arpaid) {
        this.arpaid = arpaid;
    }
    /**
     * @return the printed
     */
    public String getPrinted() {
        return printed;
    }
    /**
     * @param printed the printed to set
     */
    public void setPrinted(String printed) {
        this.printed = printed;
    }
    /**
     * @return the both_amount
     */
    public String getBoth_amount() {
        return both_amount;
    }
    /**
     * @param both_amount the both_amount to set
     */
    public void setBoth_amount(String both_amount) {
        this.both_amount = both_amount;
    }
    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }
    /**
     * @param memo the memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
    /**
     * @return the doc_num
     */
    public String getDoc_num() {
        return doc_num;
    }
    /**
     * @param doc_num the doc_num to set
     */
    public void setDoc_num(String doc_num) {
        this.doc_num = doc_num;
    }

	public String getJournal_code() {
		return journal_code;
	}

	public void setJournal_code(String journal_code) {
		this.journal_code = journal_code;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	public String getCustom2() {
		return custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	public String getCustom3() {
		return custom3;
	}

	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	public String getShipvia() {
		return shipvia;
	}

	public void setShipvia(String shipvia) {
		this.shipvia = shipvia;
	}

	public String getAccount_status() {
		return account_status;
	}

	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}
    
    
}
