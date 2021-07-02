package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedChecksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestChecksConfig.class)
public class AggregatedReportConfig {


    @Bean
    public AggregatedReportIdGenerator aggregatedReportIdGenerator(AggregatedChecksRepository aggregatedChecksRepository) {
        return new AggregatedReportIdGenerator(aggregatedChecksRepository);
    }

    @Bean
    public AggregatedReportService aggregatedChecksService(CurrentUserService currentUserService, RestCheckRepository restCheckRepository, AggregatedChecksRepository aggregatedChecksRepository, ReportService reportService) {
        return new AggregatedReportService(aggregatedReportIdGenerator(aggregatedChecksRepository), currentUserService, reportService, restCheckRepository, aggregatedChecksRepository);
    }
}
