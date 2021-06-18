package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaContractCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class RestConfig {

    @Bean
    public KafkaContractCheckService kafkaContractCheckService(){
        return new KafkaContractCheckService();
    }
}
