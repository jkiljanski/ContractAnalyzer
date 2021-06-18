package com.sciamus.contractanalyzer.domain.reporting.idGenerator;

import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AggregatedReportIdGenerator {

    @Autowired
    private AggregatedChecksRepository aggregatedChecksRepository;

    private String nextID;

    public String getNextID() {
        nextID = aggregatedChecksRepository.count() + 1 + "";
        return nextID;
    }
}
