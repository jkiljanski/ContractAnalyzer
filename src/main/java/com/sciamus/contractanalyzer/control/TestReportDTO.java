package com.sciamus.contractanalyzer.control;

import java.util.Date;

public class TestReportDTO {

    public long id;
    public String result;
    public String reportBody;
    public Date timestamp;
    public String nameOfCheck;

    public TestReportDTO(long id, String result, String reportBody, Date timestamp, String nameOfCheck) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
        this.nameOfCheck = nameOfCheck;
    }

    public TestReportDTO(long id, String result, String reportBody, Date timestamp) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;

    }

}
