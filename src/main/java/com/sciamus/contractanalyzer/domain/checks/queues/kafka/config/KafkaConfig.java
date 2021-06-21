package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaMessagesCountCheck;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaPingPongCheck;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaStreamsCountFun;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaStreamsFun;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProperties;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaConsumFactory kafkaConsumFactory(){
        return new KafkaConsumFactory();
    }

    @Bean
    public KafkaProducFactory kafkaProducFactory(){
        return new KafkaProducFactory();
    }

    @Bean
    public KafkaProperties kafkaProperties(){
        return new KafkaProperties();
    }

    @Bean
    public KafkaStreamFactory kafkaStreamFactory(){
        return new KafkaStreamFactory();
    }

    @Bean
    public KafkaMessagesCountCheck kafkaMessagesCountCheck(){
        return new KafkaMessagesCountCheck();
    }

    @Bean
    public KafkaPingPongCheck kafkaPingPongCheck(){
        return new KafkaPingPongCheck();
    }

    @Bean
    public KafkaStreamsCountFun kafkaStreamsCountFun(){
        return new KafkaStreamsCountFun();
    }

    @Bean
    public KafkaStreamsFun kafkaStreamsFun(){
        return new KafkaStreamsFun();
    }
}
