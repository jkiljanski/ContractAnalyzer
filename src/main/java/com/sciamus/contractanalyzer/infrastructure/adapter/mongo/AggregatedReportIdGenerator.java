package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import org.springframework.beans.factory.annotation.Autowired;

public class AggregatedReportIdGenerator {

    private final MongoAggregatedReportsRepository mongoAggregatedReportsRepository;

    private String nextID;

    @Autowired
    public AggregatedReportIdGenerator(MongoAggregatedReportsRepository mongoAggregatedReportsRepository) {
        this.mongoAggregatedReportsRepository = mongoAggregatedReportsRepository;
    }

    public String getNextID() {
        nextID = mongoAggregatedReportsRepository.count() + 1 + "";
        return nextID;
    }
}
