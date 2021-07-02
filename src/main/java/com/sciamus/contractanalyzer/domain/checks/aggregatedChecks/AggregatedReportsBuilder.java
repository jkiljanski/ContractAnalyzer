package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedCheckDTO;

import java.util.Date;
import java.util.List;

public class AggregatedReportsBuilder {
    private String id;
    private String aggregatedReportName;
    private List<String> namesOfChecks;
    private Date timestamp;
    private List<FailedCheckDTO> failedTests;
    private String passedPercentage;
    private String failedPercentage;
    private String userName;

    public AggregatedReportsBuilder setAggregatedReportId(String id) {
        this.id = id;
        return this;
    }

    public AggregatedReportsBuilder setAggregatedReportName(String aggregatedReportName) {
        this.aggregatedReportName = aggregatedReportName;
        return this;
    }

    public AggregatedReportsBuilder setNamesOfChecks(List<String> namesOfChecks) {
        this.namesOfChecks = namesOfChecks;
        return this;
    }

    public AggregatedReportsBuilder setTimestamp(Date timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public AggregatedReportsBuilder setFailedTests(List<FailedCheckDTO> failedTests) {
        this.failedTests = failedTests;
        return this;
    }

    public AggregatedReportsBuilder setPassedPercentage(String passedPercentage) {
        this.passedPercentage = passedPercentage;
        return this;
    }

    public AggregatedReportsBuilder setFailedPercentage(String failedPercentage) {
        this.failedPercentage = failedPercentage;
        return this;
    }

    public AggregatedReportsBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public AggregatedReportDTO build() {
        return new AggregatedReportDTO(id, aggregatedReportName, namesOfChecks, timestamp,
                failedTests, passedPercentage, failedPercentage, userName);
    }
}
