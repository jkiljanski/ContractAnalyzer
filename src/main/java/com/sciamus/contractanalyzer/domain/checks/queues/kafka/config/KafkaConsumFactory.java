package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.Random;

@Component
public class KafkaConsumFactory {


    private final KafkaProperties kafkaProperties;

    public KafkaConsumFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }



    public Consumer createConsumer(String topic, String host, String port) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":"+port);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, new Random().nextInt(100)+"");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getValueDeserializer());
        props.put(
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");

        final Consumer consumer = new KafkaConsumer<>(props);



        consumer.subscribe(List.of(topic));

        return  consumer;
    }

//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,  "earliest");


//        TopicPartition topicPartition = new TopicPartition(topic, partition);

//        consumer.assign(List.of(topicPartition));



}
