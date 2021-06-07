package com.example.kafkapingponger;

import com.example.kafkapingponger.kafka.PongProducer;
import org.springframework.stereotype.Service;

@Service
public class RespondingService {

    private final PongProducer kafkaPongProducer;

    public RespondingService(PongProducer kafkaPongProducer) {
        this.kafkaPongProducer = kafkaPongProducer;
    }

    public void notify(String message) {

        String response= message + "pong";

        kafkaPongProducer.sendMessage(response);


    }
}
