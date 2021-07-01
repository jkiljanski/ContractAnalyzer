package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedChecksRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregatedReportingConfig {


    @Bean
    public AggregatedReportIdGenerator aggregatedReportIdGenerator(AggregatedChecksRepository aggregatedChecksRepository) {
        return new AggregatedReportIdGenerator(aggregatedChecksRepository);
    }

    @Bean
    public AggregatedChecksService aggregatedChecksService(CurrentUserService currentUserService, CheckRepository checkRepository, AggregatedChecksRepository aggregatedChecksRepository, ReportService reportService) {
        return new AggregatedChecksService(aggregatedReportIdGenerator(aggregatedChecksRepository), currentUserService, reportService, checkRepository, aggregatedChecksRepository);
    }
}
