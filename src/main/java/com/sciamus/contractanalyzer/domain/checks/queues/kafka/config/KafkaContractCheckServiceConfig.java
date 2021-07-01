package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaContractCheckService;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaContractCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class KafkaContractCheckServiceConfig {

    @Bean
    public KafkaContractCheckService kafkaContractCheckService(List<KafkaContractCheck> kafkaChecks){
        return new KafkaContractCheckService(kafkaChecks);
    }

}
