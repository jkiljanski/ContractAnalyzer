package com.sciamus.contractanalyzer.domain.checks.rest.config;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.explode.ExplodeCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.GetListOfContractChecksCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.ReportingCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ChecksConfig {

    @Bean
    public RestContractCheck dummyRestContractCheck(){
        return new DummyRestContractCheck();
    }

    @Bean
    public RestContractCheck explodeCheck(){
        return new ExplodeCheck();
    }

    @Bean
    public RestContractCheck reportingCheck(){
        return new ReportingCheck();
    }

    @Bean
    public RestContractCheck getListOfContractChecksCheck() {
        return new GetListOfContractChecksCheck();
    }

    @Bean
    public List<RestContractCheck> restContractChecks(){
        return new ArrayList<>();
    }

    @Bean List<KafkaContractCheck> kafkaContractChecks() {
        return new ArrayList<>();
    }

    @Bean
    public CheckRepository checkRepository(){
        return new CheckRepository();
    }

}
