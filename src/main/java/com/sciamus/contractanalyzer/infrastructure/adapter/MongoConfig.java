package com.sciamus.contractanalyzer.infrastructure.adapter;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.ReportsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.SuitesReportsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.infrastructure.port")
public class MongoConfig implements RepositoryConfigurable {


    private final AggregatedReportsRepository aggregatedReportsRepository;
    private final ReportsRepository reportsRepository;
    private final SuitesReportsRepository suitesReportsRepository;


    public MongoConfig(AggregatedReportsRepository aggregatedReportsRepository, ReportsRepository reportsRepository, SuitesReportsRepository suitesReportsRepository) {
        this.aggregatedReportsRepository = aggregatedReportsRepository;
        this.reportsRepository = reportsRepository;
        this.suitesReportsRepository = suitesReportsRepository;
    }

    @Override
    public AggregatedReportsRepository getAggregatedReportsRepository() {
        return aggregatedReportsRepository;
    }

    @Override
    public ReportsRepository getReportsRepository() {
        return reportsRepository;
    }

    @Override
    public SuitesReportsRepository getSuitesReportsRepository() {
        return suitesReportsRepository;
    }

}
