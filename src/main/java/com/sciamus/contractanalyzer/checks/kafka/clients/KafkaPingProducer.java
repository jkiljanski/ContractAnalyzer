package com.sciamus.contractanalyzer.checks.kafka.clients;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPingProducer {


    private KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    public KafkaPingProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addTopicName(String topicName) {
        this.topicName = topicName;

    }

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }


}
