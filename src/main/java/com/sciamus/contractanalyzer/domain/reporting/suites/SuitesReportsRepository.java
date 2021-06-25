package com.sciamus.contractanalyzer.domain.reporting.suites;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuitesReportsRepository extends MongoRepository<SuiteReport,String> {



}
