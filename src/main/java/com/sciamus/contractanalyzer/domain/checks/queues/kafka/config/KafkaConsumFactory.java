package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaConsumFactory {


    private final KafkaProperties kafkaProperties;

    public KafkaConsumFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    public Consumer<String, String> createConsumer(String topic, String host, String port) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":" + port);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getValueDeserializer());


        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        TopicPartition topicPartition = new TopicPartition(topic, 0);

        consumer.assign(List.of(topicPartition));


        return consumer;
    }

    public Consumer<String, String> createMultiTopicConsumer(List<String> topics, String host, String port) {

        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":" + port);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsum().getValueDeserializer());

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        List<TopicPartition> topicPartitions = new ArrayList<>();
        for (int i=0; i<topics.size(); i++) {
            topicPartitions.add(new TopicPartition(topics.get(i), i));
        }
        consumer.assign(topicPartitions);

        return consumer;
    }

}
