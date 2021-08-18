package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportPersistancePort;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories()
@Configuration
public class MongoAdapterConfig {


    @Bean
    public ReportPersistancePort mongoRepositoryPersistanceAdapter (MongoReportsRepository mongoReportsRepository) {
        return new MongoReportPersistenceAdapter(reportDocumentMapper(), mongoReportsRepository, reportIdGenerator(mongoReportsRepository));
    }

    @Bean
    public AggregatedReportPersistancePort mongoAggregatedReportsPersistenceAdapter (MongoAggregatedReportsRepository mongoAggregatedReportsRepository) {
        return new MongoAggregatedReportsPersistenceAdapter(aggregatedReportIdGenerator(mongoAggregatedReportsRepository),mongoAggregatedReportsRepository,aggregatedReportDocumentMapper());
    }

    @Bean AggregatedReportIdGenerator aggregatedReportIdGenerator(MongoAggregatedReportsRepository mongoAggregatedReportsRepository) {
        return new AggregatedReportIdGenerator(mongoAggregatedReportsRepository);
    }

    @Bean
    AggregatedReportDocumentMapper aggregatedReportDocumentMapper() {
        return new AggregatedReportDocumentMapper();
    }

    @Bean
    ReportDocumentMapper reportDocumentMapper() {
        return new ReportDocumentMapper();
    }

    @Bean
    ReportIdGenerator reportIdGenerator (MongoReportsRepository mongoReportsRepository) {
        return new ReportIdGenerator(mongoReportsRepository);
    }

}
