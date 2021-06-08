package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaPingPongStreamFactory;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class KafkaStreamsCount implements KafkaContractCheck {

    private final String name = "KafkaStreamsCount";
    private KafkaPingPongStreamFactory kafkaPingPongStreamFactory;

    public KafkaStreamsCount(KafkaPingPongStreamFactory kafkaPingPongStreamFactory) {
        this.kafkaPingPongStreamFactory = kafkaPingPongStreamFactory;
    }

    @SneakyThrows
    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = streamsBuilder.stream(outgoingTopic);

        stream.mapValues(value ->
            String.valueOf(value.length())
        )
                .peek((k,v) -> System.out.println("value="+v))
                .to(incomingTopic);

        Topology topology = streamsBuilder.build();

        KafkaStreams application = kafkaPingPongStreamFactory.createStream(host, port, topology);

        application.cleanUp();

        application.start();


        Thread.sleep(30000);

        application.close();

//        KTable<String, Long> kTable = stream
//                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
//                .groupBy((key, value) -> value)
//                .count();
//
//
//        kTable.toStream().to(incomingTopic);

        final Logger logger = LogManager.getLogger(KafkaStreamsCount.class);

        logger.info("I'm in the count stream class");

        return null;

    }

    @Override
    public String getName() {
        return this.name;
    }
}
