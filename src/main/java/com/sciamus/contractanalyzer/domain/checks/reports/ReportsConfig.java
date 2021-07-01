package com.sciamus.contractanalyzer.domain.checks.reports;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.config.RestChecksConfig;
import com.sciamus.contractanalyzer.infrastructure.port.ReportRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppConfig.class})
@Configuration
public class ReportsConfig {

    @Bean
    ReportService reportService(ReportRepository reportRepository) {
        return new ReportService(reportRepository, reportIdGenerator(reportRepository));
    }

    @Bean
    ReportIdGenerator reportIdGenerator(ReportRepository repository) {
        return new ReportIdGenerator(repository);
    }


}
