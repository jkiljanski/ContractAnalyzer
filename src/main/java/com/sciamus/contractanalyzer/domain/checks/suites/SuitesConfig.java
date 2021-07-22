package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReportMapper;
import com.sciamus.contractanalyzer.infrastructure.port.SuitesReportsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SuitesConfig {

    @Bean
    public SuiteReportMapper suiteReportMapper(ReportMapper reportMapper) {
        return new SuiteReportMapper(reportMapper);
    }

    @Bean
    public BasicSuite basicSuite(ChecksFacade checksFacade, RestCheckRepository restCheckRepository, ReportMapper reportMapper) {
        return new BasicSuite(checksFacade, restCheckRepository, reportMapper);
    }

    @Bean
    public SuitesService suitesService(SuitesReportsRepository suitesReportsRepository, List<CheckSuite> checkSuites) {
        return new SuitesService(suitesReportsRepository, suitesRepository(checkSuites));
    }

    @Bean
    public SuitesRepository suitesRepository(List<CheckSuite> checkSuites) {
        return new SuitesRepository(checkSuites);
    }

}
