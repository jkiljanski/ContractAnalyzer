package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public AppConfig(){
        System.out.println("App Config created!!!");
    }

    @Bean
    public CheckReportMapper checkReportMapper(){
        return new CheckReportMapper();
    }

    @Bean
    public CurrentUserService currentUserService() {
        return new CurrentUserService();
    }

    @Bean
    public KeycloakSecurityContext keycloakSecurityContext() {
        return new KeycloakSecurityContext();
    }

    @Bean
    public ContractChecksService contractChecksService(){
        return new ContractChecksService();
    }
}
