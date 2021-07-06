package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregatedReportsRepository extends MongoRepository<AggregatedReport, String> {


}
