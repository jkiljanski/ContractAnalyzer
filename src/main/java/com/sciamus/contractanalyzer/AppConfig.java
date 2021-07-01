package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserServiceSecured;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserServiceUnsecured;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {


    @Profile("!unsecured")
    @Bean
    public CheckReportMapper checkReportMapper(KeycloakSecurityContext keycloakSecurityContext){
        return new CheckReportMapper(currentUserService(keycloakSecurityContext));
    }

    @Profile("unsecured")
    @Bean
    public CheckReportMapper checkReportMapperUnsecured(){
        return new CheckReportMapper(currentUserServiceUnsecured());
    }


    @Profile("!unsecured")
    @Bean
    public CurrentUserService currentUserService(KeycloakSecurityContext keycloakSecurityContext) {
        return new CurrentUserServiceSecured(keycloakSecurityContext);
    }

    @Profile("unsecured")
    @Bean
    public CurrentUserService currentUserServiceUnsecured() {
        return new CurrentUserServiceUnsecured();
    }

    @Bean
    public ContractChecksService contractChecksService(CheckRepository checkRepository, ReportService reportService, CheckReportMapper checkReportMapper){
        return new ContractChecksService(checkRepository, reportService, checkReportMapper);
    }
}
