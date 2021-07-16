package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedTestDTO;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static io.vavr.API.*;

public class AggregatedChecksAccumulator implements BiConsumer<AggregatedChecksStatistics, CheckReport> {
    @Override
    public void accept(AggregatedChecksStatistics aggregatedChecksStatistics, CheckReport savedReport) {
        aggregatedChecksStatistics.increaseNumber();

        Match(savedReport.getResult()).of(
                Case($(Predicate.isEqual(ReportResults.FAILED)), () -> {
                    aggregatedChecksStatistics.appendFailedReport(savedReport);
                    return null;
                }),
                Case($(), doNothingIfPassed())
        );
    }

        private Void doNothingIfPassed() {
        return null;
    }

}
