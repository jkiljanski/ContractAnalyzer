package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuitesReportsRepository extends MongoRepository<SuiteReport,String> {



}
