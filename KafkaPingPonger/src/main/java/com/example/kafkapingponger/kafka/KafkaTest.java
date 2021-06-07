package com.example.kafkapingponger.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaTest {


    private final KafkaPongProducer producer;
    private final KafkaPingConsumer consumer;

    public KafkaTest(KafkaPongProducer producer, KafkaPingConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    void sendPing() {

        producer.sendMessage("ping");


    }

//    void listenForPing() {
//
//        consumer.listenGroup1();
//    }


}
