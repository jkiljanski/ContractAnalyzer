package com.sciamus.contractanalyzer.domain.checks.suites.config;

import com.sciamus.contractanalyzer.application.ContractChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReportMapper;
import com.sciamus.contractanalyzer.infrastructure.port.SuitesReportsRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.BasicSuite;
import com.sciamus.contractanalyzer.domain.checks.suites.CheckSuite;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SuitesConfig {

    @Bean
    public SuiteReportMapper suiteReportMapper(CheckReportMapper checkReportMapper) {
        return new SuiteReportMapper(checkReportMapper);
    }

    @Bean
    public BasicSuite basicSuite(ContractChecksFacade contractChecksFacade, CheckRepository checkRepository, CheckReportMapper checkReportMapper){
        return new BasicSuite(contractChecksFacade, checkRepository, checkReportMapper);
    }

    @Bean
    public SuitesService suitesService(SuitesReportsRepository suitesReportsRepository, List<CheckSuite> checkSuites){
        return new SuitesService(suitesReportsRepository, suitesRepository(checkSuites));
    }

    @Bean
    public SuitesRepository suitesRepository(List<CheckSuite> checkSuites){
        return new SuitesRepository(checkSuites);
    }

}
