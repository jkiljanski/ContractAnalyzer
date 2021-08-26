package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReportMapper;
import com.sciamus.contractanalyzer.infrastructure.adapter.mongo.MongoSuitesReportsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SuitesConfig {

    @Bean
    public SuiteReportMapper suiteReportMapper(ReportViewMapper reportViewMapper) {
        return new SuiteReportMapper(reportViewMapper);
    }

    @Bean
    public BasicSuite basicSuite(ChecksFacade checksFacade, RestCheckRepository restCheckRepository, ReportViewMapper reportViewMapper) {
        return new BasicSuite(checksFacade, restCheckRepository, reportViewMapper);
    }

    @Bean
    public SuitesService suitesService(MongoSuitesReportsRepository mongoSuitesReportsRepository, List<CheckSuite> checkSuites) {
        return new SuitesService(mongoSuitesReportsRepository, suitesRepository(checkSuites));
    }

    @Bean
    public SuitesRepository suitesRepository(List<CheckSuite> checkSuites) {
        return new SuitesRepository(checkSuites);
    }

}
