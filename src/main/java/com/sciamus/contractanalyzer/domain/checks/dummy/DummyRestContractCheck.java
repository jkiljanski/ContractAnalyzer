package com.sciamus.contractanalyzer.domain.checks.dummy;

import com.sciamus.contractanalyzer.domain.checks.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
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
