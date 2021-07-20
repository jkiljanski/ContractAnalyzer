package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.misc.CurrentUserService;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregatedReportConfig {


    @Bean
    public AggregatedReportIdGenerator aggregatedReportIdGenerator(AggregatedReportsRepository aggregatedReportsRepository) {
        return new AggregatedReportIdGenerator(aggregatedReportsRepository);
    }

    @Bean
    public AggregatedReportService aggregatedChecksService(CurrentUserService currentUserService, RestCheckRepository restCheckRepository, AggregatedReportsRepository aggregatedReportsRepository, ReportService reportService) {
        return new AggregatedReportService(aggregatedReportIdGenerator(aggregatedReportsRepository), currentUserService, reportService, restCheckRepository, aggregatedReportsRepository);
    }
}
