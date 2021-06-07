package com.sciamus.contractanalyzer.reporting.checks;

import java.util.List;

public class AggregatedReportBuilder {
    private ReportResults result;
    private List<String> namesOfChecks;
    private int passedTestsNumber;
    private int failedTestsNumber;

    public AggregatedReportBuilder setResult(ReportResults result) {
        this.result = result;
        return this;
    }
    public AggregatedReportBuilder setNameOfChecks(List<String> namesOfChecks) {
        this.namesOfChecks = namesOfChecks;
        return this;
    }

    public AggregatedReportBuilder setPassedTestsNumber(int passedTestsNumber) {
        this.passedTestsNumber = passedTestsNumber;
        return this;
    }

    public AggregatedReportBuilder setFailedTestsNumber(int failedTestsNumber) {
        this.failedTestsNumber = failedTestsNumber;
        return this;
    }

    public AggregatedReport build() {
        return new AggregatedReport(result, namesOfChecks, passedTestsNumber, failedTestsNumber);
    }
}
