package com.sciamus.contractanalyzer.domain.suites;

import com.sciamus.contractanalyzer.domain.checks.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReport;
import io.vavr.collection.List;

import java.net.URL;

public abstract class CheckSuite {

    private String name;
    private List<RestContractCheck> checkList;

    public CheckSuite(List<RestContractCheck> checkList) {

    }

    public CheckSuite() {

    }

    abstract SuiteReport run(URL url);

    public String getName() {
        return this.name;
    }
}
