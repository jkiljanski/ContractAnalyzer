package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaPingPongStreamFactory;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamsCheck implements KafkaContractCheck{

    private final KafkaPingPongStreamFactory kafkaPingPongStreamFactory;

    private final String name = "KafkaStreamsCheck";

    public KafkaStreamsCheck(KafkaPingPongStreamFactory kafkaPingPongStreamFactory) {
        this.kafkaPingPongStreamFactory = kafkaPingPongStreamFactory;
    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        KafkaStreams stream = kafkaPingPongStreamFactory.createStream(incomingTopic, outgoingTopic, host, port);
        stream.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stream.close();

        return null;

    }

    @Override
    public String getName() {
        return this.name;
    }
}
