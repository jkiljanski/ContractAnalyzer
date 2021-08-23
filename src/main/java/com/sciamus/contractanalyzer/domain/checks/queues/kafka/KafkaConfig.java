package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProperties;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ReportsConfig.class)
@Configuration
public class KafkaConfig {


    @Bean
    public KafkaCheckService kafkaCheckService(List<KafkaCheck> kafkaCheckList) {
        return new KafkaCheckService(kafkaCheckList);
    }

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

//    @Bean
//    public KafkaPingPongCheck kafkaPingPongCheck(ReportService reportService){
//        return new KafkaPingPongCheck(kafkaConsumFactory(), kafkaProducFactory(), reportService);
//    }

    @Bean
    KafkaMessagesManyRunsCheck kafkaMessagesManyRunsCheck(){
        return new KafkaMessagesManyRunsCheck(kafkaProducFactory(),kafkaConsumFactory());

    }
    @Bean
    KafkaMessagesManyRunsCheckWithVavr kafkaMessagesManyRunsCheckWithVavr(){
        return new KafkaMessagesManyRunsCheckWithVavr(kafkaProducFactory(),kafkaConsumFactory());
    }

    @Bean
    KafkaTransformationCheck kafkaTransformationCheck(){
        return new KafkaTransformationCheck(kafkaProducFactory(),kafkaConsumFactory());
    }





}
