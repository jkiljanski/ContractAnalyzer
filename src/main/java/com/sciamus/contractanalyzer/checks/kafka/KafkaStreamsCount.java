package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class KafkaStreamsCount implements KafkaContractCheck {

    private final String name = "KafkaStreamsCount";


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = streamsBuilder.stream(outgoingTopic);

        KTable<String, Long> kTable = stream
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .groupBy((key, value) -> value)
                .count();


        kTable.toStream().to(incomingTopic);

        final Logger logger = LogManager.getLogger(KafkaStreamsCount.class);

        logger.info("I'm in the count stream class");

        return null;


    }

    @Override
    public String getName() {
        return this.name;
    }
}
