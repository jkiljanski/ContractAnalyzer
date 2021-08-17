package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAggregatedReportsRepository extends MongoRepository<AggregatedReportDocument, String> {




}
