package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import io.vavr.control.Option;

import java.util.function.BiConsumer;

public class AggregatedChecksAccumulator implements BiConsumer<AggregatedChecksStatistics, CheckReport> {
    @Override
    public void accept(AggregatedChecksStatistics aggregatedChecksStatistics, CheckReport savedReport) {
        aggregatedChecksStatistics.increaseNumber();

        Option.of(savedReport.getResult())
                .filter(r -> r.equals(ReportResults.FAILED))
                .peek(r -> aggregatedChecksStatistics.appendFailedReport(savedReport));
    }

}
