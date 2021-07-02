package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.explode.ExplodeCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.GetListOfChecksCheck;
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
    public RestCheck dummyRestContractCheck() {
        return new DummyRestCheck();
    }

    @Bean
    public RestCheck explodeCheck() {
        return new ExplodeCheck();
    }

    @Bean
    public RestCheck reportingCheck(ReportMapper reportMapper, RequestInterceptor requestInterceptor) {
        return new ReportingCheck(reportMapper, requestInterceptor);
    }

    @Bean
    public RestCheck getListOfContractChecksCheck(RequestInterceptor requestInterceptor) {
        return new GetListOfChecksCheck(requestInterceptor);
    }

    @Bean
    public RestCheckRepository checkRepository(List<RestCheck> restChecks, CurrentUserService currentUserService) {
        return new RestCheckRepository(restChecks, currentUserService);
    }

}
