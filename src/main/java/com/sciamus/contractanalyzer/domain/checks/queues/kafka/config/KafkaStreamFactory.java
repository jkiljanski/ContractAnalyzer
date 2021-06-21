package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;


import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

public class KafkaStreamFactory {

    @Autowired
    private KafkaProperties kafkaProperties;

    public KafkaStreams createStream(String host, String port, Topology topology) {
        final Properties props = new Properties();

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, host + ":" + port);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getAppName());


        //dlaczego nie można tego zaczytać z application.yml ?????
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, kafkaProperties.getStreams().getKeySerde());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, kafkaProperties.getStreams().getValueSerde());

        props.put(StreamsConfig.STATE_DIR_CONFIG, "data");


        return new KafkaStreams(topology, props);

    }


}
