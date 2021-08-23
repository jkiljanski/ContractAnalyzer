package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoSuitesReportsRepository extends MongoRepository<SuiteReport, String> {


}
