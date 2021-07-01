package com.sciamus.contractanalyzer.domain.checks.rest.config;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.explode.ExplodeCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.GetListOfContractChecksCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.ReportingCheck;
import com.sciamus.contractanalyzer.misc.conf.FeignClientsConfig;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;


@Import({AppConfig.class, FeignClientsConfig.class})
@Configuration
public class RestChecksConfig {

    @Bean
    public RestContractCheck dummyRestContractCheck() {
        return new DummyRestContractCheck();
    }

    @Bean
    public RestContractCheck explodeCheck() {
        return new ExplodeCheck();
    }

    @Bean
    public RestContractCheck reportingCheck(CheckReportMapper checkReportMapper, RequestInterceptor requestInterceptor) {
        return new ReportingCheck(checkReportMapper, requestInterceptor);
    }

    @Bean
    public RestContractCheck getListOfContractChecksCheck(RequestInterceptor requestInterceptor) {
        return new GetListOfContractChecksCheck(requestInterceptor);
    }

    @Bean
    public CheckRepository checkRepository(List<RestContractCheck> restContractChecks, CurrentUserService currentUserService) {
        return new CheckRepository(restContractChecks, currentUserService);
    }

}
