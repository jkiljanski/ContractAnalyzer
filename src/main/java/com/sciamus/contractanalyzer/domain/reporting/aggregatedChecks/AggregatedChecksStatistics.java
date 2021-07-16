package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedTestDTO;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import io.vavr.collection.List;

public class AggregatedChecksStatistics {

    private List<CheckReport> failedTestReports = List.empty();
    private int numberOfTests;

    public List<CheckReport> getFailedTestReports() {
        return failedTestReports;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public void increaseNumber() {
        this.numberOfTests++;
    }

    public void appendFailedReport(CheckReport failedReport) {
        failedTestReports = failedTestReports.append(failedReport);
    }

    public void merge(AggregatedChecksStatistics aggregatedChecksStatistics2) {
        numberOfTests += aggregatedChecksStatistics2.numberOfTests;
        failedTestReports = failedTestReports.appendAll(aggregatedChecksStatistics2.failedTestReports);
    }
}
