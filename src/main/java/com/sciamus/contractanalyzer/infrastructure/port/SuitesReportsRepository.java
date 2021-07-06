package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuitesReportsRepository extends MongoRepository<SuiteReport, String> {


}
