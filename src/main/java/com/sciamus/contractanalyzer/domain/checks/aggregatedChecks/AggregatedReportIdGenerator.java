package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AggregatedReportIdGenerator {

    private final AggregatedReportsRepository aggregatedReportsRepository;

    private String nextID;

    @Autowired
    public AggregatedReportIdGenerator(AggregatedReportsRepository aggregatedReportsRepository) {
        this.aggregatedReportsRepository = aggregatedReportsRepository;
    }

    public String getNextID() {
        nextID = aggregatedReportsRepository.count() + 1 + "";
        return nextID;
    }
}
