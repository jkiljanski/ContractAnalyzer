package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CheckRepository checkRepository(){
        return new CheckRepository();
    }

    public ReportService reportService(){
        return new ReportService();
    }

    @Bean
    public CheckReportMapper checkReportMapper(){
        return new CheckReportMapper(currentUserService());
    }

    @Bean
    public CurrentUserService currentUserService() {
        return new CurrentUserService(keycloakSecurityContext());
    }

    @Bean
    public KeycloakSecurityContext keycloakSecurityContext() {
        return new KeycloakSecurityContext();
    }

    @Bean
    public ContractChecksService contractChecksService(CheckRepository checkRepository){
        return new ContractChecksService(checkRepository, reportService(), checkReportMapper());
    }


}
