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

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ReportNameTest {

    private ReportName reportName;



    @Test
    public void testProfitAndLoss() {
        reportName = ReportName.PROFITANDLOSS;
        assertEquals(reportName.toString(), "ProfitAndLoss");
    }

    @Test
    public void testBalanceSheet() {
        reportName = ReportName.BALANCESHEET;
        assertEquals(reportName.toString(), "BalanceSheet");
    }

    @Test
    public void testCashFlow() {
        reportName = ReportName.CASHFLOW;
        assertEquals(reportName.toString(), "CashFlow");
    }

    @Test
    public void testCustomerIncome() {
        reportName = ReportName.CUSTOMERINCOME;
        assertEquals(reportName.toString(), "CustomerIncome");
    }

    @Test
    public void testAgedReceivables() {
        reportName = ReportName.AGEDRECEIVABLES;
        assertEquals(reportName.toString(), "AgedReceivables");
    }

    @Test
    public void testAgedPayables() {
        reportName = ReportName.AGEDPAYABLES;
        assertEquals(reportName.toString(), "AgedPayables");
    }

    @Test
    public void testItemSales() {
        reportName = ReportName.ITEMSALES;
        assertEquals(reportName.toString(), "ItemSales");
    }

    @Test
    public void testDepartmentSales() {
        reportName = ReportName.DEPARTMENTSALES;
        assertEquals(reportName.toString(), "DepartmentSales");
    }

    @Test
    public void testClassSales() {
        reportName = ReportName.CLASSSALES;
        assertEquals(reportName.toString(), "ClassSales");
    }

    @Test
    public void testTrialBalance() {
        reportName = ReportName.TRIALBALANCE;
        assertEquals(reportName.toString(), "TrialBalance");
    }

    @Test
    public void testTrialBalanceFR() {
        reportName = ReportName.TRIALBALANCE_FR;
        assertEquals(reportName.toString(), "TrialBalanceFR");
    }

    @Test
    public void testVendorBalance() {
        reportName = ReportName.VENDORBALANCE;
        assertEquals(reportName.toString(), "VendorBalance");
    }

    @Test
    public void testVendorExpenses() {
        reportName = ReportName.VENDOREXPENSES;
        assertEquals(reportName.toString(), "VendorExpenses");
    }

    @Test
    public void testInventoryValuationSummary() {
        reportName = ReportName.INVENTORYVALUATIONSUMMARY;
        assertEquals(reportName.toString(), "InventoryValuationSummary");
    }

    @Test
    public void testBAS() {
        reportName = ReportName.BAS;
        assertEquals(reportName.toString(), "BAS");
    }

    @Test
    public void testVendorBalanceDetail() {
        reportName = ReportName.VENDORBALANCEDETAIL;
        assertEquals(reportName.toString(), "VendorBalanceDetail");
    }

    @Test
    public void testGeneralLedger() {
        reportName = ReportName.GENERALLEDGER;
        assertEquals(reportName.toString(), "GeneralLedger");
    }

    @Test
    public void testGeneralLedgerFR() {
        reportName = ReportName.GENERALLEDGER_FR;
        assertEquals(reportName.toString(), "GeneralLedgerFR");
    }

    @Test
    public void testAgedPayableDetail() {
        reportName = ReportName.AGEDPAYABLEDETAIL;
        assertEquals(reportName.toString(), "AgedPayableDetail");
    }
}
