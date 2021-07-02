package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;

import java.net.URL;
import java.util.List;

public abstract class CheckSuite {

    private String name;

    private List<RestCheck> restChecks;

    public CheckSuite(){}

    public CheckSuite(List<RestCheck> restChecks) {}

    abstract SuiteReport run(URL url);

    public String getName() {
        return this.name;
    }
}
