package com.sciamus.contractanalyzer.control;

import java.sql.Timestamp;

public class TestReportDTO {

    public long id;
    public String result;
    public String reportBody;
    public Timestamp timestamp;

    public TestReportDTO(long id, String result, String reportBody, Timestamp timestamp) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
    }

}
