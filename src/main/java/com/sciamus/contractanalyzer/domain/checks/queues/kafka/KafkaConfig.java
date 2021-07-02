package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.*;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProperties;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ReportsConfig.class)
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
    @Bean KafkaMessagesSimpleCountCheck kafkaMessagesSimpleCountCheck() {
        return new KafkaMessagesSimpleCountCheck(kafkaStreamFactory(),kafkaProducFactory(),kafkaConsumFactory());
    }

    @Bean
    KafkaMessagesManyRunsCheck kafkaMessagesManyRunsCheck(){
        return new KafkaMessagesManyRunsCheck(kafkaProducFactory(),kafkaConsumFactory());

    }
    @Bean
    KafkaMessagesManyRunsCheckWithVavr kafkaMessagesManyRunsCheckWithVavr(){
        return new KafkaMessagesManyRunsCheckWithVavr(kafkaProducFactory(),kafkaConsumFactory());
    }





}
