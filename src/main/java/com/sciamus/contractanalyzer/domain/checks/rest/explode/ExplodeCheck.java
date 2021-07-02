package com.sciamus.contractanalyzer.domain.checks.rest.explode;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;

import java.net.URL;

public class ExplodeCheck implements RestCheck {

    private final String NAME = "Exploding Check";

    @Override
    public Report run(URL url, ReportBuilder reportBuilder) {
        return reportBuilder
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
