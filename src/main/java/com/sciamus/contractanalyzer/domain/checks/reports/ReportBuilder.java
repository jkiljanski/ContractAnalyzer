package com.sciamus.contractanalyzer.domain.checks.reports;

import java.util.Date;

public class ReportBuilder {
    private String id;
    private ReportResults result;
    private String reportBody;
    // refactor to Localdate
    private Date timestamp;
    private String nameOfCheck;
    private String userName;

    public ReportBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ReportBuilder setResult(ReportResults result) {
        this.result = result;
        return this;
    }

    public ReportBuilder setReportBody(String reportBody) {
        this.reportBody = reportBody;
        return this;
    }

    public ReportBuilder createTimestamp(){
        this.timestamp = new Date(System.currentTimeMillis());
        return this;
    }

    public ReportBuilder setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ReportBuilder setNameOfCheck(String nameOfCheck) {
        this.nameOfCheck = nameOfCheck;
        return this;
    }

    public ReportBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }


    public ReportBuilder addTextToBody(String text) {
        this.reportBody = reportBody +text;
        return this;
    }

    public Report build() {
        return new Report(id, result, reportBody, timestamp, nameOfCheck, userName);
    }
}