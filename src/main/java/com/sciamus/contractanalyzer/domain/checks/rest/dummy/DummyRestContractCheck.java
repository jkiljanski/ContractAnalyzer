package com.sciamus.contractanalyzer.domain.checks.rest.dummy;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReport;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;

import java.net.URL;

public class DummyRestContractCheck implements RestContractCheck {


    private final static String NAME = "Dummy Check";

    public CheckReport run(URL url, CheckReportBuilder checkReportBuilder) {

        return checkReportBuilder
                .setNameOfCheck(this.getName())
                .setReportBody("This report is always PASSED;" + " Run on " + url)
                .setResult(ReportResults.PASSED)
                .createTimestamp()
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
