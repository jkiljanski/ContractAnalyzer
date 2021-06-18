package com.sciamus.contractanalyzer.domain.suites;

import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SuitesConfig {

    @Bean
    public SuiteReportMapper suiteReportMapper(){
        return new SuiteReportMapper();
    }

}

