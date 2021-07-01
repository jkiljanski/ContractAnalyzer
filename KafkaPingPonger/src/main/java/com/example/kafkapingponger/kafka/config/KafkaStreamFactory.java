package com.example.kafkapingponger.kafka.config;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaStreamFactory {


    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public KafkaStreams createStream(Topology topology) {
        final Properties props = new Properties();

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "redirect-app");

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
//        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 4);


        props.put(StreamsConfig.STATE_DIR_CONFIG, "data");

        return new KafkaStreams(topology, props);

    }

}
