package com.sciamus.contractanalyzer.application;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ReportDTO {

    public String id;
    public String result;
    public String reportBody;
    public Date timestamp;
    public String nameOfCheck;
    public String userName;

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
