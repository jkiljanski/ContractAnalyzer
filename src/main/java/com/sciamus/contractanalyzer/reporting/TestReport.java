package com.sciamus.contractanalyzer.reporting;


import java.sql.Timestamp;


public class TestReport {

    //TODO: review: kiedy to pole się tworzy, nie jestem pewien tego rozwiązania
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());


    private long id;
    private final ReportResults result;
    private final String reportBody;

    public TestReport(ReportResults result, String reportBody) {

        this.result = result;
        this.reportBody = reportBody;
    }


    public TestReport(long id, ReportResults result, String reportBody, Timestamp timestamp) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
    }

    // nie jestem pewien tego rozwiązania
    void addId(long id) {
        this.id = id;
    }


    public ReportResults isPassed() {
        return result;
    }

    public long getReportId() {
        return id;
    }

    public ReportResults getResult() {
        return this.result;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getReportBody() {
        return reportBody;
    }
}
