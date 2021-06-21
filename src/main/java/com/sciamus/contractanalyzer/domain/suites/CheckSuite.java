package com.sciamus.contractanalyzer.domain.suites;

import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;

public abstract class CheckSuite {

    private String name;

    @Autowired
    private List<RestContractCheck> restContractChecks;

    public CheckSuite() {

    }

    abstract SuiteReport run(URL url);

    public String getName() {
        return this.name;
    }
}
