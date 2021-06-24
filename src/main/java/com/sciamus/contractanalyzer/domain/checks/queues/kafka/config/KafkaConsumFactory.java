package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

@Component
public class KafkaConsumFactory {


    private final KafkaProperties kafkaProperties;

    public KafkaConsumFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }



    public Consumer<String,String> createConsumer(String topic, String host, String port) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":"+port);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID() +"");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getValueDeserializer());
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

//        props.put(
//                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");


        final Consumer<String,String> consumer = new KafkaConsumer<>(props);

        TopicPartition topicPartition = new TopicPartition(topic, 0);

        consumer.assign(List.of(topicPartition));

//        consumer.subscribe(List.of(topic));

        return  consumer;
    }

//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,  "earliest");






}
