package com.sciamus.contractanalyzer.checks.kafka.clients;

import org.springframework.stereotype.Component;

@Component
public class KafkaTest {


    private final KafkaPingProducer producer;
    private final KafkaPongConsumer consumer;

    public KafkaTest(KafkaPingProducer producer, KafkaPongConsumer consumer) {
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
