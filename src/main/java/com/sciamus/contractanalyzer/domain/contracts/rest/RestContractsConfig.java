package com.sciamus.contractanalyzer.domain.contracts.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestContractsConfig {

    @Bean
    public RestContractsRepository restContractsRepository() {
        return new RestContractsRepository();
    }
}
