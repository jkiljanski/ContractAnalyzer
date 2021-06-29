package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CheckReportMapper checkReportMapper(KeycloakSecurityContext keycloakSecurityContext){
        return new CheckReportMapper(currentUserService(keycloakSecurityContext));
    }

    @Bean
    public CurrentUserService currentUserService(KeycloakSecurityContext keycloakSecurityContext) {
        return new CurrentUserService(keycloakSecurityContext);
    }

    @Bean
    public ContractChecksService contractChecksService(CheckRepository checkRepository, ReportService reportService, CheckReportMapper checkReportMapper){
        return new ContractChecksService(checkRepository, reportService, checkReportMapper);
    }
}
