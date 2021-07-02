package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class KafkaContractCheckServiceConfig {

    @Bean
    public KafkaCheckService kafkaContractCheckService(List<KafkaCheck> kafkaChecks){
        return new KafkaCheckService(kafkaChecks);
    }

}
