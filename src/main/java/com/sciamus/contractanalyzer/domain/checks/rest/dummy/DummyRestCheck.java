package com.sciamus.contractanalyzer.domain.checks.rest.dummy;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;

import java.net.URL;

public class DummyRestCheck implements RestCheck {


    private final static String NAME = "Dummy Check";

    public Report run(URL url, ReportBuilder reportBuilder) {

        return reportBuilder
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
