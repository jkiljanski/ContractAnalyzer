package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.misc.CurrentUserService;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfig;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SecurityConfig.class})
public class ApplicationConfig {

    private final SecurityConfigurable securityConfigurable;

    public ApplicationConfig(SecurityConfigurable securityConfigurable) {
        this.securityConfigurable = securityConfigurable;
    }

    @Bean
    public ChecksFacade contractChecksFacade(RestCheckRepository restCheckRepository, ReportService reportService) {
        return new ChecksFacade(restCheckRepository, reportService, checkReportMapper());
    }

    @Bean
    public AggregatedChecksFacade aggregatedChecksFacade(AggregatedReportService aggregatedReportService) {
        return new AggregatedChecksFacade(aggregatedReportService);
    }

    @Bean
    public ReportMapper checkReportMapper() {
        return new ReportMapper(currentUserService());
    }

    @Bean
    public CurrentUserService currentUserService() {
        return new CurrentUserService(securityConfigurable.provideKeycloakSecurityContext());
    }

}
