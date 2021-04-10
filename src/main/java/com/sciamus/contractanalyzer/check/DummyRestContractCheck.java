package com.sciamus.contractanalyzer.check;

import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service

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
