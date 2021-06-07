package com.example.kafkapingponger;

import com.example.kafkapingponger.kafka.KafkaPongProducer;
import org.springframework.stereotype.Service;

@Service
public class RespondingService {

    private final KafkaPongProducer kafkaPongProducer;

    public RespondingService(KafkaPongProducer kafkaPongProducer) {
        this.kafkaPongProducer = kafkaPongProducer;
    }

    public void notify(String message) {

        String response= "pong: " + message;
        kafkaPongProducer.sendMessage(response);


    }
}
