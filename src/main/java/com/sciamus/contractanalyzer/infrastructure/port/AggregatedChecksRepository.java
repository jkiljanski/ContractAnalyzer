package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregatedChecksRepository extends MongoRepository<AggregatedReport,String>, RepositoryConfigurable {



}
