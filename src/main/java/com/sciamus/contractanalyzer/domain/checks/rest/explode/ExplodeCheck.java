package com.sciamus.contractanalyzer.domain.checks.rest.explode;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReport;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReportBuilder;

import java.net.URL;

public class ExplodeCheck implements RestContractCheck {

    private final String NAME = "Exploding Check";

    @Override
    public CheckReport run(URL url, CheckReportBuilder checkReportBuilder) {
        return checkReportBuilder
                .setNameOfCheck(this.getName())
                .setReportBody("This report always fails; " + "Run on "+url)
                .setResult(ReportResults.FAILED)
                .createTimestamp()
                .build();
    }


    @Override
    public String getName() {
        return NAME;
    }
}
