package com.sciamus.contractanalyzer.domain.reporting.suites;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface SuitesReportsRepository extends MongoRepository<SuiteReport,String> {



}
