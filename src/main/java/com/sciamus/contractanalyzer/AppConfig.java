package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ContractChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import io.vavr.jackson.datatype.VavrModule;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootConfiguration
@EnableAutoConfiguration
//TODO: move to specific config
@EnableFeignClients
//TODO: move to specific config
@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.domain.reporting")
@EnableConfigurationProperties
//TODO: move to specific config
@EnableSwagger2
//TODO: import other configs (auto scan doesn't work)
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
    public ContractChecksFacade contractChecksService(CheckRepository checkRepository, ReportService reportService, CheckReportMapper checkReportMapper){
        return new ContractChecksFacade(checkRepository, reportService, checkReportMapper);
    }

    @Bean
    VavrModule vavrModule() {
        return new VavrModule();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.sciamus.contractanalyzer")).build();
    }
}
