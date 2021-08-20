package com.sciamus.contractanalyzer.application;

import java.time.LocalDateTime;

public class QueryParameters {
    private final LocalDateTime timestampFrom;
    private final LocalDateTime timestampTo;
    private final String nameOfCheck;
    private final String result;
    private final String userName;
    private final String reportBody;

    public QueryParameters(LocalDateTime timestampFrom, LocalDateTime timestampTo, String nameOfCheck, String result, String userName, String reportBody) {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.nameOfCheck = nameOfCheck;
        this.result = result;
        this.userName = userName;
        this.reportBody = reportBody;
    }

    public LocalDateTime getTimestampFrom() {
        return timestampFrom;
    }

    public LocalDateTime getTimestampTo() {
        return timestampTo;
    }

    public String getNameOfCheck() {
        return nameOfCheck;
    }

    public String getResult() {
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public String getReportBody() {
        return reportBody;
    }
}
