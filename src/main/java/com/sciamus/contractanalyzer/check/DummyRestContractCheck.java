package com.sciamus.contractanalyzer.check;

import com.sciamus.contractanalyzer.reporting.DummyTestReport;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service

public class DummyRestContractCheck implements RestContractCheck {

    public DummyRestContractCheck() {
    }


    public TestReport run(URL url) {
        return new DummyTestReport();
    }

    @Override
    public String getName() {
        return "Dummy";
    }
}
