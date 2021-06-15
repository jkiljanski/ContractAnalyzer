package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamsFun implements KafkaContractCheck {

    private final KafkaStreamFactory kafkaStreamFactory;

    private final String name = "KafkaStreamsFun";

    private final KafkaProducFactory producFactory;

    private final KafkaConsumFactory consumFactory;

    public KafkaStreamsFun(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory producFactory, KafkaConsumFactory consumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.producFactory = producFactory;
        this.consumFactory = consumFactory;
    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        KafkaTemplate<String, String> producer = producFactory.createProducer(host, port);


        producer.send(outgoingTopic, "streams check");

        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(outgoingTopic).to(incomingTopic);

        Topology topology = builder.build();

        KafkaStreams stream = kafkaStreamFactory.createStream(host, port, topology);

//        stream.cleanUp();

        stream.start();

        try {
            Thread.sleep(30000);
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
