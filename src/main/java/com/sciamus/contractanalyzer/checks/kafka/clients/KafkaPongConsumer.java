package com.sciamus.contractanalyzer.checks.kafka.clients;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component

public class KafkaPongConsumer {

    private String topic;

    @KafkaListener(topics = "", groupId="1")
    public void listenGroup1(String message) {
//        System.out.println("Received Message in group 1: " + message);

    }

}
