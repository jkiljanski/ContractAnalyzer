package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import io.vavr.collection.List;

public class AggregatedReportStatistics {

    private List<Report> failedCheckReports = List.empty();
    private int numberOfTests;

    public List<Report> getFailedCheckReports() {
        return failedCheckReports;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public void increaseNumber() {
        this.numberOfTests++;
    }

    public void appendFailedReport(Report failedReport) {
        failedCheckReports = failedCheckReports.append(failedReport);
    }

    public void merge(AggregatedReportStatistics aggregatedReportStatistics2) {
        numberOfTests += aggregatedReportStatistics2.numberOfTests;
        failedCheckReports = failedCheckReports.appendAll(aggregatedReportStatistics2.failedCheckReports);
    }
}
