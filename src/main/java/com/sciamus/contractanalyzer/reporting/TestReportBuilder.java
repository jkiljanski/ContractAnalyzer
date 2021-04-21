package com.sciamus.contractanalyzer.reporting;

import java.util.Date;

public class TestReportBuilder {
    private String id;
    private ReportResults result;
    private String reportBody;
    private Date timestamp;
    private String nameOfCheck;

    public TestReportBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public TestReportBuilder setResult(ReportResults result) {
        this.result = result;
        return this;
    }

    public TestReportBuilder setReportBody(String reportBody) {
        this.reportBody = reportBody;
        return this;
    }

    public TestReportBuilder createTimestamp(){
        this.timestamp = new Date(System.currentTimeMillis());
        return this;
    }

    public TestReportBuilder setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TestReportBuilder setNameOfCheck(String nameOfCheck) {
        this.nameOfCheck = nameOfCheck;
        return this;
    }

    public TestReport createTestReport() {
        return new TestReport(id, result, reportBody, timestamp, nameOfCheck);
    }
}