package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedTestDTO;

import java.util.Date;
import java.util.List;

public class AggregatedChecksBuilder {
    private String id;
    private String aggregatedReportName;
    private List<String> namesOfChecks;
    private Date timestamp;
    private List<FailedTestDTO> failedTests;
    private String passedPercentage;
    private String failedPercentage;
    private String userName;

    public AggregatedChecksBuilder setAggregatedReportId(String id) {
        this.id = id;
        return this;
    }

    public AggregatedChecksBuilder setAggregatedReportName(String aggregatedReportName) {
        this.aggregatedReportName = aggregatedReportName;
        return this;
    }

    public AggregatedChecksBuilder setNamesOfChecks(List<String> namesOfChecks) {
        this.namesOfChecks = namesOfChecks;
        return this;
    }

    public AggregatedChecksBuilder setTimestamp(Date timestamp){
        this.timestamp = timestamp;
        return this;
    }

    public AggregatedChecksBuilder setFailedTests(List<FailedTestDTO> failedTests) {
        this.failedTests = failedTests;
        return this;
    }

    public AggregatedChecksBuilder setPassedPercentage(String passedPercentage) {
        this.passedPercentage = passedPercentage;
        return this;
    }

    public AggregatedChecksBuilder setFailedPercentage(String failedPercentage) {
        this.failedPercentage = failedPercentage;
        return this;
    }

    public AggregatedChecksBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public AggregatedChecksDTO build() {
        return new AggregatedChecksDTO(id, aggregatedReportName, namesOfChecks, timestamp,
                failedTests, passedPercentage, failedPercentage, userName);
    }
}
