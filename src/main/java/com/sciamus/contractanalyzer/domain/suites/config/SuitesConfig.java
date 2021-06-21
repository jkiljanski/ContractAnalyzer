package com.sciamus.contractanalyzer.domain.suites.config;

import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.domain.suites.BasicSuite;
import com.sciamus.contractanalyzer.domain.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.suites.SuitesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SuitesConfig {

    @Bean
    public SuiteReportMapper suiteReportMapper() {
        return new SuiteReportMapper();
    }

    @Bean
    public BasicSuite basicSuite(){
        return new BasicSuite();
    }

    @Bean
    public SuitesService suitesService(){
        return new SuitesService();
    }

    @Bean
    public SuitesRepository suitesRepository(){
        return new SuitesRepository();
    }

}
