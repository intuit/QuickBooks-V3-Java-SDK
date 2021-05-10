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

import com.intuit.ipp.core.Context;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.interceptors.IntuitInterceptorProvider;
import com.intuit.ipp.interceptors.IntuitMessage;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReportServiceTest {
    @Mocked
    private Context context;

    private ReportService reportService;

    @BeforeClass
    public void setup() {
        reportService = new ReportService(context);
        reportService = getMockedIntuitMessage();
    }

    @Test
    public void testExecuteReport() throws FMSException {
        MockIntuitInterceptorProvider mockIntuitInterceptorProvider
                = new MockIntuitInterceptorProvider();
        reportService.executeReport("mock");
    }

    public ReportService getMockedIntuitMessage() {
        reportService.setReport_date("");
        reportService.setStart_date("");
        reportService.setEnd_date("");
        reportService.setDate_macro("");
        reportService.setDate_macro("");
        reportService.setPast_due("");
        reportService.setEnd_duedate("");
        reportService.setStart_duedate("");
        reportService.setDuedate_macro("");
        reportService.setAccounting_method("");
        reportService.setAccount("");
        reportService.setSource_account("");
        reportService.setSource_account_type("");
        reportService.setSummarize_column_by("");
        reportService.setAccount_type("");
        reportService.setCustomer("");
        reportService.setVendor("");
        reportService.setItem("");
        reportService.setClassid("");
        reportService.setAppaid("");
        reportService.setDepartment("");
        reportService.setQzurl("");
        reportService.setAging_period("");
        reportService.setAging_method("");
        reportService.setNum_periods("");
        reportService.setTerm("");
        reportService.setColumns("");
        reportService.setSort_by("");
        reportService.setSort_order("");
        reportService.setGroup_by("");
        reportService.setCreatedate_macro("");
        reportService.setEnd_createdate("");
        reportService.setStart_createdate("");
        reportService.setModdate_macro("");
        reportService.setEnd_moddate("");
        reportService.setStart_moddate("");
        reportService.setPayment_method("");
        reportService.setName("");
        reportService.setTransaction_type("");
        reportService.setCleared("");
        reportService.setArpaid("");
        reportService.setPrinted("");
        reportService.setBoth_amount("");
        reportService.setMemo("");
        reportService.setDoc_num("");
        reportService.setJournal_code("");
        reportService.setEmployee("");
        reportService.setAgency_id("");
        reportService.setCustom1("");
        reportService.setCustom2("");
        reportService.setCustom3("");
        reportService.setShipvia("");
        reportService.setAccount_status("");
        reportService.setSubcol_pct_inc("");
        reportService.setSubcol_pct_exp("");
        reportService.setShowrows("");

        return reportService;
    }
    private static final class MockIntuitInterceptorProvider extends MockUp<IntuitInterceptorProvider> {

        @Mock
        public void executeInterceptors(final IntuitMessage intuitMessage) throws FMSException {
            // mocked executeInterceptors
        }
    }
}