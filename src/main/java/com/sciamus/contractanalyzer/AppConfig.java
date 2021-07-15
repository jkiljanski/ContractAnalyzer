package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfig;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//ASK: dlaczego nie samo configuration???
@SpringBootApplication
@EnableConfigurationProperties

@Import({SecurityConfig.class, ReportService.class})
public class AppConfig {


    private final SecurityConfigurable securityConfigurable;

    public AppConfig(SecurityConfigurable securityConfigurable) {
        this.securityConfigurable = securityConfigurable;
    }

    @Bean
    public ReportMapper checkReportMapper() {
        return new ReportMapper(currentUserService());
    }

    @Bean
    public CurrentUserService currentUserService() {
        return new CurrentUserService(securityConfigurable.provideKeycloakSecurityContext());
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
    VavrModule vavrModule() {
        return new VavrModule();
    }


}
