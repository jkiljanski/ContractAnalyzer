package com.sciamus.contractanalyzer.control;

import java.util.Date;

public class TestReportDTO {

    public String id;
    public String result;
    public String reportBody;
    public Date timestamp;
    public String nameOfCheck;

    public TestReportDTO(String id, String result, String reportBody, Date timestamp, String nameOfCheck) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
        this.nameOfCheck = nameOfCheck;
    }

    @Override
    public String toString() {
        return "TestReportDTO{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", reportBody='" + reportBody + '\'' +
                ", timestamp=" + timestamp +
                ", nameOfCheck='" + nameOfCheck + '\'' +
                '}';
    }
}
