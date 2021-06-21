package com.sciamus.contractanalyzer.domain.checks.rest.dummy;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;

import java.net.URL;

public class DummyRestContractCheck implements RestContractCheck {


    private final static String NAME = "Dummy Check";

    public CheckReport run(URL url, CheckReportBuilder checkReportBuilder) {

        return new CheckReportBuilder()
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
