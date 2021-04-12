package com.sciamus.contractanalyzer.checks.dummy;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class DummyRestContractCheck implements RestContractCheck {


    private final static String NAME = "Dummy Check";


    public TestReport run(URL Url) {
        return new TestReport(ReportResults.PASSED, "Dummy Report");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
