package com.example.kafkapingponger.kafka;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPongProducer {


    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.outgoing-topic}")
    private String topicName;


    public KafkaPongProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }


}
