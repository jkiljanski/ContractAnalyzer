package com.sciamus.contractanalyzer.domain.suites.config;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuitesReportsRepository;
import com.sciamus.contractanalyzer.domain.suites.BasicSuite;
import com.sciamus.contractanalyzer.domain.suites.CheckSuite;
import com.sciamus.contractanalyzer.domain.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.suites.SuitesService;
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
    public BasicSuite basicSuite(ContractChecksService contractChecksService, CheckRepository checkRepository, CheckReportMapper checkReportMapper){
        return new BasicSuite(contractChecksService, checkRepository, checkReportMapper);
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
