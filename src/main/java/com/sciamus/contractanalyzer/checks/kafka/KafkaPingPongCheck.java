package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPongConsumer;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {


    private final KafkaPingProducer kafkaPingProducer;


    private final KafkaPongConsumer kafkaPongConsumer;

    public KafkaPingPongCheck(KafkaPingProducer kafkaPingProducer, KafkaPongConsumer kafkaPongConsumer) {
        this.kafkaPingProducer = kafkaPingProducer;
        this.kafkaPongConsumer = kafkaPongConsumer;
    }

    @Override
    public CheckReport run(String topicName1, String topicName2) {


        kafkaPingProducer.addTopicName(topicName1);
        kafkaPingProducer.sendMessage("ping");



        return null;


    }
}
