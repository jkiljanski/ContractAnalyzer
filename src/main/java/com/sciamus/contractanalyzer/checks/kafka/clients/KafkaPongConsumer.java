package com.example.kafkapingponger.kafka;


import com.example.kafkapingponger.RespondingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component

public class KafkaPingConsumer {

    private final RespondingService respondingService;

    public KafkaPingConsumer(RespondingService respondingService) {
        this.respondingService = respondingService;
    }

    @KafkaListener(topics = "${kafka.incoming-topic}", groupId="1")
    public void listenGroup1(String message) {
//        System.out.println("Received Message in group 1: " + message);

        respondingService.notify(message);

    }

}
