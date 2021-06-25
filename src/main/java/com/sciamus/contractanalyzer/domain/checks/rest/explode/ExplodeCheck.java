package com.sciamus.contractanalyzer.domain.checks.rest.explode;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
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
