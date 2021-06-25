package com.sciamus.contractanalyzer.interfaces.rest.config;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaContractCheckService;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaContractCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan
public class RestConfig {

    @Bean
    public KafkaContractCheckService kafkaContractCheckService(List<KafkaContractCheck> kafkaChecks){
        return new KafkaContractCheckService(kafkaChecks);
    }

}
