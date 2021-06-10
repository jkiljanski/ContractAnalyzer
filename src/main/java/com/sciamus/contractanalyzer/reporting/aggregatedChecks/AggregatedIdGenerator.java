package com.sciamus.contractanalyzer.reporting.aggregatedChecks;

import org.springframework.stereotype.Component;

@Component
public class AggregatedIdGenerator {
    private final AggregatedChecksRepository aggregatedChecksRepository;

    private String nextID;

    public AggregatedIdGenerator(AggregatedChecksRepository aggregatedChecksRepository) {
        this.aggregatedChecksRepository = aggregatedChecksRepository;
    }

    public String getNextID() {
        nextID = aggregatedChecksRepository.count() + 1 + "";
        return nextID;
    }
}
