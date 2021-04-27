package com.sciamus.contractanalyzer.checks.explode;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ExplodeCheck implements RestContractCheck {

    private final String NAME = "Exploding Check";


    @Override
    public CheckReport run(URL url) {
        return new CheckReportBuilder()
                .setNameOfCheck(this.getName())
                .setReportBody("This report always fails; " + "Run on "+url)
                .setResult(ReportResults.FAILED)
                .createTimestamp()
                .createTestReport();
    }


    @Override
    public String getName() {
        return NAME;
    }
}
