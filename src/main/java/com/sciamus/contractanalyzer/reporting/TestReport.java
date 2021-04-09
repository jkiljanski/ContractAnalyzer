package com.sciamus.contractanalyzer.reporting;

import io.vavr.control.Option;

public class TestReport {

    public TestReport(ReportResults result, String report) {
        this.result = result;
        this.report = report;
    }

    private ReportResults result;
    private String report;

    public TestReport() {

    }

    public boolean isPassed() {
        return result == ReportResults.PASSED;
    }

}
