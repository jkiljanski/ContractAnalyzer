package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MongoReportsRepository extends MongoRepository<ReportDocument, String>, QuerydslPredicateExecutor<ReportDocument> {




}
