package com.sciamus.contractanalyzer.domain.reporting.config;

import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksReport;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksRepository;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksService;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportRepository;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.AggregatedReportIdGenerator;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.ReportIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportingConfig {

    @Bean
    public ReportService reportService(ReportRepository reportRepository){
        return new ReportService(reportRepository, reportIdGenerator(reportRepository));
    }

    @Bean
    public ReportIdGenerator reportIdGenerator(ReportRepository reportRepository){
        return new ReportIdGenerator(reportRepository);
    }

    @Bean
    public AggregatedReportIdGenerator aggregatedReportIdGenerator(AggregatedChecksRepository aggregatedChecksRepository){
        return new AggregatedReportIdGenerator(aggregatedChecksRepository);
    }

    @Bean
    public AggregatedChecksService aggregatedChecksService(CurrentUserService currentUserService, CheckRepository checkRepository, AggregatedChecksRepository aggregatedChecksRepository, ReportRepository reportRepository){
        return new AggregatedChecksService(aggregatedReportIdGenerator(aggregatedChecksRepository), currentUserService, reportService(reportRepository), checkRepository, aggregatedChecksRepository);
    }
}
