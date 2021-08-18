package com.sciamus.contractanalyzer.infrastructure.port;

import java.util.Date;
import java.util.List;

public class AggregatedReportInfrastructureDTO {

    public String id;
    public String aggregatedReportName;
    public List<String> namesOfChecks;
    public Date timestamp;
    public List<ReportInfrastructureDTO> failedTestReportList;
    public String passedPercentage;
    public String failedPercentage;
    public String userName;

    public AggregatedReportInfrastructureDTO(String id, String aggregatedReportName, List<String> namesOfChecks, Date timestamp, List<ReportInfrastructureDTO> failedTestReportList, String passedPercentage, String failedPercentage, String userName) {
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

    public Date getTimestamp() {
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
}
