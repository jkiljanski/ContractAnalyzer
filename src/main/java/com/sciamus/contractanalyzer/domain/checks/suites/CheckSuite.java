package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;

import java.net.URL;
import java.util.List;

public abstract class CheckSuite {

    private String name;

    private List<RestContractCheck> restContractChecks;

    public CheckSuite(){}

    public CheckSuite(List<RestContractCheck> restContractChecks) {}

    abstract SuiteReport run(URL url);

    public String getName() {
        return this.name;
    }
}
