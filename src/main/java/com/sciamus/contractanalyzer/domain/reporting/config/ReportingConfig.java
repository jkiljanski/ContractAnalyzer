package com.sciamus.contractanalyzer.domain.reporting.config;

import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
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
    public ReportService reportService(){
        return new ReportService();
    }

    @Bean
    public ReportIdGenerator reportIdGenerator(){
        return new ReportIdGenerator();
    }

    @Bean
    public AggregatedReportIdGenerator aggregatedReportIdGenerator(){
        return new AggregatedReportIdGenerator();
    }

    @Bean
    public AggregatedChecksService aggregatedChecksService(){
        return new AggregatedChecksService();
    }
}
