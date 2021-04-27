package com.sciamus.contractanalyzer.reporting.checks;

import java.util.Date;

public class CheckReportBuilder {
    private String id;
    private ReportResults result;
    private String reportBody;
    // refactor to Localdate
    private Date timestamp;
    private String nameOfCheck;

    public CheckReportBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CheckReportBuilder setResult(ReportResults result) {
        this.result = result;
        return this;
    }

    public CheckReportBuilder setReportBody(String reportBody) {
        this.reportBody = reportBody;
        return this;
    }

    public CheckReportBuilder createTimestamp(){
        this.timestamp = new Date(System.currentTimeMillis());
        return this;
    }

    public CheckReportBuilder setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public CheckReportBuilder setNameOfCheck(String nameOfCheck) {
        this.nameOfCheck = nameOfCheck;
        return this;
    }

    public CheckReportBuilder addTextToBody(String text) {
        this.reportBody = reportBody +text;
        return this;
    }

    public CheckReport createTestReport() {
        return new CheckReport(id, result, reportBody, timestamp, nameOfCheck);
    }
}