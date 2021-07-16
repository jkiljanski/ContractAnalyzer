package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import java.util.function.BiConsumer;

public class AggregatedChecksConsumer implements BiConsumer<AggregatedChecksStatistics, AggregatedChecksStatistics> {
    @Override
    public void accept(AggregatedChecksStatistics aggregatedChecksStatistics, AggregatedChecksStatistics aggregatedChecksStatistics2) {
        aggregatedChecksStatistics.merge(aggregatedChecksStatistics2);
    }
}
