package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.control.Option;

import java.util.function.BiConsumer;

public class AggregatedReportAccumulator implements BiConsumer<AggregatedReportStatistics, Report> {
    @Override
    public void accept(AggregatedReportStatistics aggregatedReportStatistics, Report savedReport) {
        aggregatedReportStatistics.increaseNumber();

        Option.of(savedReport.getResult())
                .filter(r -> r.equals(ReportResults.FAILED))
                .peek(r -> aggregatedReportStatistics.appendFailedReport(savedReport));
    }

}
