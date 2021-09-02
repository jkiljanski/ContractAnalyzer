package com.sciamus.contractanalyzer.infrastructure.port;

import java.time.LocalDateTime;
import java.util.List;

public class AggregatedReportInfrastructureDTO {

    public String id;
    public String aggregatedReportName;
    public List<String> namesOfChecks;
    public LocalDateTime timestamp;
    public List<ReportInfrastructureDTO> failedTestReportList;
    public String passedPercentage;
    public String failedPercentage;
    public String userName;

    public AggregatedReportInfrastructureDTO(String id, String aggregatedReportName, List<String> namesOfChecks, LocalDateTime timestamp, List<ReportInfrastructureDTO> failedTestReportList, String passedPercentage, String failedPercentage, String userName) {
        this.id = id;
        this.aggregatedReportName = aggregatedReportName;
        this.namesOfChecks = namesOfChecks;
        this.timestamp = timestamp;
        this.failedTestReportList = failedTestReportList;
        this.passedPercentage = passedPercentage;
        this.failedPercentage = failedPercentage;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CheckReportDTO{" +
                "id='" + id + '\'' +
                ", aggregatedReportName='" + aggregatedReportName + '\'' +
                ", namesOfChecks='" + namesOfChecks + '\'' +
                ", timestamp=" + timestamp +
                ", failedTestReportList='" + failedTestReportList + '\'' +
                ", passedPercentage='" + passedPercentage + '\'' +
                ", failedPercentage='" + failedPercentage + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getAggregatedReportName() {
        return aggregatedReportName;
    }

    public List<String> getNamesOfChecks() {
        return namesOfChecks;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<ReportInfrastructureDTO> getFailedTestReportList() {
        return failedTestReportList;
    }

    public String getPassedPercentage() {
        return passedPercentage;
    }

    public String getFailedPercentage() {
        return failedPercentage;
    }

    public String getUserName() {
        return userName;
    }

    public void addName(String name) {
        this.aggregatedReportName = name;
    }
}
