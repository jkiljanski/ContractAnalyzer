package com.example.kafkapingponger.kafka;

import com.example.kafkapingponger.kafka.config.KafkaStreamFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
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

    @KafkaListener(topics = "command-topic", groupId = "1")
    public void listenToCommand(ConsumerRecord<String, String> record) {

        String command = "compute";

        logger.info("The krowa command is " + record.key());


        if (record.key().endsWith(command)) {

            String uniqueKey = record.key().replace(command, "");

            KafkaStreams application = createKafkaStreams(uniqueKey);

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            application.close();

        }

    }

    private KafkaStreams createKafkaStreams(String uniqueKey) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream("count-topic");
        stream
//                .filter((k, v) -> k.startsWith(uniqueKey))
                .peek((k,v) -> System.out.println("From count-topic to output-topic: " +k+" :"+ v))
                .to("output-topic");


        KafkaStreams application = factory.createStream(builder.build());
        application.start();
        return application;
    }


}
