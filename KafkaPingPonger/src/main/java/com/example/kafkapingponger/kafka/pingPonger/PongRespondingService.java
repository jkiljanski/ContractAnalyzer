package com.example.kafkapingponger.kafka.pingPonger;

import org.springframework.stereotype.Service;

@Service
public class PongRespondingService {

    private final PongProducer kafkaPongProducer;

    public PongRespondingService(PongProducer kafkaPongProducer) {
        this.kafkaPongProducer = kafkaPongProducer;
    }

    public void notify(String message) {

        String response = message + "pong";

        kafkaPongProducer.sendMessage(response);

    }
}
