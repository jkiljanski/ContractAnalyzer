package com.example.kafkapingponger.kafka.pingPonger;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component

public class PingConsumer {

    private final PongRespondingService pongRespondingService;

    public PingConsumer(PongRespondingService pongRespondingService) {
        this.pongRespondingService = pongRespondingService;
    }

    @KafkaListener(topics = "${spring.kafka.incoming-topic}", groupId="1")
    public void listenGroup1(String message) {
//        System.out.println("Received Message in group 1: " + message);

        pongRespondingService.notify(message);

    }

}
