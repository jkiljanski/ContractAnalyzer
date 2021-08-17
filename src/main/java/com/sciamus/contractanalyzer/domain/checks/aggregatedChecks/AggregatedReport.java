package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class AggregatedReport {

    public String id;
    private String aggregatedReportName;
    private final List<String> namesOfChecks;
    private Date timestamp;
    private final List<Report> failedTestReportList;
    private final String passedPercentage;
    private final String failedPercentage;
    private final String userName;


    public AggregatedReport(String id, String aggregatedReportName, List<String> namesOfChecks, Date timestamp, List<Report> failedTestReportList, String passedPercentage, String failedPercentage, String userName) {
        this.id = id;
        this.aggregatedReportName = aggregatedReportName;
        this.namesOfChecks = namesOfChecks;
        this.timestamp = timestamp;
        this.failedTestReportList = failedTestReportList;
        this.passedPercentage = passedPercentage;
        this.failedPercentage = failedPercentage;
        this.userName = userName;
    }

    public void addId(String id) {
        this.id = id;
    }

    public void addName(String name) {
        this.aggregatedReportName = name;
    }

    public String toString() {
        return "AggregatedChecksReport{" +
                "id='" + id + '\'' +
                ", aggregatedReportName=" + aggregatedReportName +
                ", namesOfChecks='" + namesOfChecks + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", failedTestReportList='" + failedTestReportList + '\'' +
                ", passedPercentage='" + passedPercentage + '\'' +
                ", failedPercentage='" + failedPercentage + '\'' +
                ", userName='" + userName +
                '}';
    }

}
