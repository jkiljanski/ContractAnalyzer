package com.sciamus.contractanalyzer.checks.kafka.clients.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Properties;

@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerFactory {



    String keyDeserializer;
    String valueDeserializer;

    private Consumer<String,String> createConsumer(String topic, String host, String port) {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":"+port);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);

        final Consumer<String,String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList(topic));
        return  consumer;

    }






}
