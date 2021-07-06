package com.sciamus.contractanalyzer.domain.checks.reports;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.infrastructure.adapter.MongoConfig;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppConfig.class, MongoConfig.class})
@Configuration
public class ReportsConfig {

    @Bean
    public ReportService reportService(RepositoryConfigurable repositoryConfigurable) {
        return new ReportService(repositoryConfigurable, reportIdGenerator(repositoryConfigurable));
    }

    @Bean
    public ReportIdGenerator reportIdGenerator(RepositoryConfigurable repositoryConfigurable) {
        return new ReportIdGenerator(repositoryConfigurable.getReportsRepository());
    }


}
