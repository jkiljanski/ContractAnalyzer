package com.sciamus.contractanalyzer.checks.kafka.clients.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class KafkaConsumFactory {


    private final KafkaProperties kafkaProperties;

    public KafkaConsumFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    // pytanie 1: czy tu trzeba używać springa??

    // pytanie 2: ważne - mieszane konstruktory - trochę springa a trochę nie - jak to zrobić


    public Consumer<String,String> createConsumer(String topic, String host, String port) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":"+port);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getValueDeserializer());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,  "earliest");

        final Consumer<String,String> consumer = new KafkaConsumer<>(props);

//        TopicPartition topicPartition = new TopicPartition(topic, partition);

//        consumer.assign(List.of(topicPartition));

        consumer.subscribe(List.of(topic));

        return  consumer;
    }






}
