package com.sciamus.contractanalyzer.control;

import java.util.Date;
import java.sql.Timestamp;

public class TestReportDTO {

    public long id;
    public String result;
    public String reportBody;
    public Date timestamp;
    public String nameOfCheck;

    public TestReportDTO(long id, String result, String reportBody, Date timestamp) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
    }

}
