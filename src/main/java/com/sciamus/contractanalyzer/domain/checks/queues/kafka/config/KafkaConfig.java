package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaMessagesSimpleCountCheck;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaPingPongCheck;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaStreamsCountFun;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaStreamsFun;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaConsumFactory kafkaConsumFactory(){
        return new KafkaConsumFactory(kafkaProperties());
    }

    @Bean
    public KafkaProducFactory kafkaProducFactory(){
        return new KafkaProducFactory(kafkaProperties());
    }

    @Bean
    public KafkaProperties kafkaProperties(){
        return new KafkaProperties();
    }

    @Bean
    public KafkaStreamFactory kafkaStreamFactory(){
        return new KafkaStreamFactory(kafkaProperties());
    }

    @Bean
    public KafkaMessagesSimpleCountCheck kafkaMessagesCountCheck(){
        return new KafkaMessagesSimpleCountCheck(kafkaStreamFactory(), kafkaProducFactory(), kafkaConsumFactory());
    }

    @Bean
    public KafkaPingPongCheck kafkaPingPongCheck(ReportService reportService){
        return new KafkaPingPongCheck(kafkaConsumFactory(), kafkaProducFactory(), reportService);
    }

    @Bean
    public KafkaStreamsCountFun kafkaStreamsCountFun(){
        return new KafkaStreamsCountFun(kafkaStreamFactory());
    }

    @Bean
    public KafkaStreamsFun kafkaStreamsFun(){
        return new KafkaStreamsFun(kafkaStreamFactory(), kafkaProducFactory(), kafkaConsumFactory());
    }
}
