package com.sciamus.contractanalyzer.reporting;

import org.bouncycastle.util.test.TestResult;

public class TestReport {

    public TestReport(ReportResults result, String report) {
        this.result = result;
        this.report = report;
    }

    private ReportResults result;
    private String report;


    public ReportResults isPassed() {
        return result;
    }



}
