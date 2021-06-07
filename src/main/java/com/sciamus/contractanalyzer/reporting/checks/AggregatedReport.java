package com.sciamus.contractanalyzer.reporting.checks;

import org.springframework.stereotype.Component;

import java.util.List;

public class AggregatedReport {
    private ReportResults result;
    private List<String> namesOfChecks;
    private int passedTestsNumber;
    private int failedTestsNumber;

    public AggregatedReport(ReportResults result, List<String> namesOfChecks, int passedTestsNumber, int failedTestsNumber) {
        this.result = result;
        this.namesOfChecks = namesOfChecks;
        this.passedTestsNumber = passedTestsNumber;
        this.failedTestsNumber = failedTestsNumber;
    }
}
