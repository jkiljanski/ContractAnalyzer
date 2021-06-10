package com.sciamus.contractanalyzer.reporting.aggregatedChecks;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregatedChecksRepository extends MongoRepository<AggregatedChecksReport,String> {
}
