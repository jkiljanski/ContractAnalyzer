package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface AggregatedChecksRepository extends MongoRepository<AggregatedChecksReport,String> {
}
