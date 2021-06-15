package com.example.kafkapingponger.kafka;

import com.example.kafkapingponger.kafka.config.KafkaStreamFactory;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class CommandListener {

    Logger logger = LogManager.getLogger(this.getClass());

    public CommandListener(KafkaStreamFactory factory) {
        this.factory = factory;
    }

    private KafkaStreamFactory factory;

    @KafkaListener(topics = "command-topic", groupId="1")
    public void listenToCommand(String command) {


        logger.info("The krowa command is " + command);


        if (command.contains("compute")) {

            KafkaStreams application = createKafkaStreams();

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            application.close();

        }

    }

    private KafkaStreams createKafkaStreams() {
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream("count-topic").to("output-topic");
        KafkaStreams stream = factory.createStream(builder.build());
        stream.start();
        return stream;
    }



}
