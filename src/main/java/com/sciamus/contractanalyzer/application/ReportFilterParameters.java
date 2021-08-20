package com.sciamus.contractanalyzer.application;

public class ReportFilterParameters {
    private final String result;
    private final String reportBody;
    private final String timestampFrom;
    private final String timestampTo;
    private final String nameOfCheck;
    private final String userName;

    public ReportFilterParameters(String result, String reportBody, String timestampFrom, String timestampTo, String nameOfCheck, String userName) {
        this.result = result;
        this.reportBody = reportBody;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.nameOfCheck = nameOfCheck;
        this.userName = userName;
    }

    public String getResult() {
        return result;
    }

    public String getReportBody() {
        return reportBody;
    }

    public String getTimestampFrom() {
        return timestampFrom;
    }

    public String getTimestampTo() {
        return timestampTo;
    }

    public String getNameOfCheck() {
        return nameOfCheck;
    }

    public String getUserName() {
        return userName;
    }
}
