package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedChecksRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AggregatedReportIdGenerator {

    private final AggregatedChecksRepository aggregatedChecksRepository;

    private String nextID;

    @Autowired
    public AggregatedReportIdGenerator(AggregatedChecksRepository aggregatedChecksRepository) {
        this.aggregatedChecksRepository = aggregatedChecksRepository;
    }

    public String getNextID() {
        nextID = aggregatedChecksRepository.count() + 1 + "";
        return nextID;
    }
}
