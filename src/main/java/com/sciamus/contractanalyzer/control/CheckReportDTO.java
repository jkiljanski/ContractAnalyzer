package com.sciamus.contractanalyzer.control;

import java.util.Date;


public class CheckReportDTO {


    public String id;
    public String result;
    public String reportBody;
    public Date timestamp;
    public String nameOfCheck;
    public String userName;

    public CheckReportDTO(String id, String result, String reportBody, Date timestamp, String nameOfCheck, String userName) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
        this.nameOfCheck = nameOfCheck;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CheckReportDTO{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", reportBody='" + reportBody + '\'' +
                ", timestamp=" + timestamp +
                ", nameOfCheck='" + nameOfCheck + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
