package com.intuit.ipp.services;

public enum ReportName {

	/**
	 * Profit and Loss report
	 */
	PROFITANDLOSS("ProfitAndLoss"),
	
	/**
	 * BalanceSheet report
	 */
	BALANCESHEET("BalanceSheet"),
	
	/**
	 * CashFlow report
	 */
	CASHFLOW("CashFlow"),
	
	/**
	 * Customer Income report
	 */
	CUSTOMERINCOME("CustomerIncome"),
	
	/**
	 * AR Aging summary report
	 */
	AGEDRECEIVABLES("AgedReceivables"),
	
	/**
	 * AP Aging summary report
	 */
	AGEDPAYABLES("AgedPayables"),
	
	/**
	 * Customer Balance report
	 */
	CUSTOMERBALANCE("CustomerBalance"),
	
	/**
	 * CustomerSales report
	 */
	CUSTOMERSALES("CustomerSales"),
	
	/**
	 * ItemSales report
	 */
	ITEMSALES("ItemSales"),
	
	/**
	 * DepartmentSales report
	 */
	DEPARTMENTSALES("DepartmentSales"),
	
	/**
	 * ClassSales report
	 */
	CLASSSALES("ClassSales"),
	
	/**
	 * Trial Balance report
	 */
	TRIALBALANCE("TrialBalance"),
	
	/**
	 * Vendor Balance report
	 */
	VENDORBALANCE("VendorBalance"),
	
	/**
	 * Trial Balance report
	 */
	VENDOREXPENSES("VendorExpenses"),
	
	
	/**
	 * Inventory Valuation Summary report
	 */
	INVENTORYVALUATIONSUMMARY("InventoryValuationSummary"),
	
	/**
	 * BAS report
	 */
	BAS("BAS"),

    /**
     * Vendor Balance Detail report
     */
    VENDORBALANCEDETAIL( "VendorBalanceDetail"),

    /**
     * General Ledger report
     */
    GENERALLEDGER( "GeneralLedger"),

    /**
     * Aged Payable Detail report
     */
    AGEDPAYABLEDETAIL( "AgedPayableDetail");

    /*
     * Transaction List By Date report
     */
    //TRANSACTIONLISTBYDATE( "TransactionListByDate");

	
	private String name;
	
	/**
	 * Constructor operation type
	 * 
	 * @param name the operation type
	 */
	private ReportName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to get the string value of operation type
	 */
	@Override
	public String toString() {
		return name;
	}
	
	

}
