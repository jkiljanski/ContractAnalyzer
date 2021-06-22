package com.example.kafkapingponger.kafka;

import com.example.kafkapingponger.kafka.config.KafkaStreamFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class InteractiveQuery {

    private final KafkaStreamFactory factory;

    public InteractiveQuery(KafkaStreamFactory factory) {
        this.factory = factory;
    }

    @KafkaListener(topics = "command-topic", groupId = "2")
    public void interactiveQueryListen(ConsumerRecord<String, String> record) {

        System.out.println("I'm in PIES");

        String command = "compute";

        Logger logger = LogManager.getLogger(this.getClass());
        logger.info("Query command " + record.key());

        StreamsBuilder builder = new StreamsBuilder();

        builder.stream("count-topic");

        KafkaStreams stream = factory.createStream(builder.build());

        stream.start();

        ReadOnlyKeyValueStore<String, Long> keyValueStore =
                stream.store(StoreQueryParameters.fromNameAndType("statistics", QueryableStoreTypes.keyValueStore()));

        logger.info("INTERACTIVE PIES QUERY");

        keyValueStore.all().forEachRemaining(System.out::println);

        stream.close();

    }

}




