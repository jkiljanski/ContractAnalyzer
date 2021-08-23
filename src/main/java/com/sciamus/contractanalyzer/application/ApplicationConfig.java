package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.aggregatedReport.AggregatedReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.aggregatedReport.AggregatedReportViewMapper;
import com.sciamus.contractanalyzer.application.mapper.report.ReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportPersistancePort;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
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
    public  KafkaChecksFacade kafkaChecksFacade (KafkaCheckService kafkaCheckService, ReportViewMapper reportViewMapper) {
        return new KafkaChecksFacade(kafkaCheckService, reportViewMapper);
    }

    @Bean
    public ChecksFacade checksFacade(RestCheckRepository restCheckRepository, ReportPersistancePort reportPersistancePort) {
        return new ChecksFacade(restCheckRepository, reportFacade(reportPersistancePort), reportPersistancePort, reportViewMapper(), reportInfrastructureMapper());
    }

    @Bean
    public ReportFacade reportFacade(ReportPersistancePort reportPersistancePort) {

        return new ReportFacade(checkReportMapper(),reportInfrastructureMapper(), reportPersistancePort);
    }

    @Bean
    public ReportInfrastructureMapper reportInfrastructureMapper() {
        return  new ReportInfrastructureMapper();
    }

    @Bean ReportViewMapper reportViewMapper(){
        return  new ReportViewMapper();
    }

    @Bean
    AggregatedReportViewMapper aggregatedReportViewMapper() {return  new AggregatedReportViewMapper();}

    @Bean
    AggregatedReportInfrastructureMapper aggregatedReportInfrastructureMapper() {return  new AggregatedReportInfrastructureMapper();}


    @Bean
    public AggregatedChecksFacade aggregatedChecksFacade(RestCheckRepository restCheckRepository, AggregatedReportPersistancePort aggregatedReportPersistancePort, ChecksFacade checkFacade) {
        return new AggregatedChecksFacade(restCheckRepository, aggregatedReportPersistancePort, aggregatedReportInfrastructureMapper(), aggregatedReportViewMapper(), currentUserService(), checkFacade);
    }

    @Bean
    public ReportViewMapper checkReportMapper() {
        return new ReportViewMapper();
    }

    @Bean
    public CurrentUserService currentUserService() {
        return new CurrentUserService(securityConfigurable.provideKeycloakSecurityContext());
    }

    @Bean
    public  KafkaChecksFacade kafkaChecksFacade (KafkaCheckService kafkaCheckService) {
        return new KafkaChecksFacade(kafkaCheckService, reportViewMapper());
    }

}
