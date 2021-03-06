package com.sciamus.contractanalyzer.application;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportViewDTO {

    public String id;
    public String result;
    public String reportBody;
    public LocalDateTime timestamp;
    public String nameOfCheck;
    public String userName;

    public ReportViewDTO(String id, String result, String reportBody, LocalDateTime timestamp, String nameOfCheck, String userName) {
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
