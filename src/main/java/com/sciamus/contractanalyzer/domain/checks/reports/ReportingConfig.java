package com.sciamus.contractanalyzer.domain.checks.reports;

import com.sciamus.contractanalyzer.infrastructure.port.ReportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportingConfig {

    @Bean
    public ReportService reportService(ReportRepository reportRepository) {
        return new ReportService(reportRepository, reportIdGenerator(reportRepository));
    }

    @Bean
    public ReportIdGenerator reportIdGenerator(ReportRepository reportRepository) {
        return new ReportIdGenerator(reportRepository);
    }
}
