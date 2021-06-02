package com.sciamus.contractanalyzer.checks.kafka.clients.config;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerFactory {

    private final KafkaProperties kafkaProperties;

    public KafkaProducerFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    public KafkaTemplate<String,String> createProducer(String host, String port) {

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                host+":"+port);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProduc().getKeySerializer());
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProduc().getValueSerializer());

        ProducerFactory<String,String> factory = new DefaultKafkaProducerFactory<> (configProps);
        final KafkaTemplate<String,String> template = new KafkaTemplate<>(factory);

        return template;


    }



}
