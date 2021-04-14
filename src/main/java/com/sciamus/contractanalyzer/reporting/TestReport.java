package com.sciamus.contractanalyzer.reporting;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;



public class TestReport {





    private long id;
    private final ReportResults result;
    private final String reportBody;

    public TestReport(ReportResults result, String reportBody) {

        this.result = result;
        this.reportBody = reportBody;
    }

    public TestReport(long id, ReportResults result, String reportBody) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
    }

    // nie jestem pewien tego rozwiązania
    void addId(long id) {
        this.id = id;
    }



    public ReportResults isPassed() {
        return result;
    }
    public long getReportId(){
        return id;
    }
    public ReportResults getResult() {return this.result;}

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getReportBody() {
        return reportBody;
    }
}
