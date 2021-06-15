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

    @KafkaListener(topics = "command-topic", groupId="1")
    public void listenToCommand(ConsumerRecord<String,String> record) {


        logger.info("The krowa command is " + record.key());

        if (record.key().contains("compute")) {



            String s = record.key();




//            KafkaStreams application = createKafkaStreams();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            application.close();

        }

    }

    private KafkaStreams createKafkaStreams(String key) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String,String> stream = builder
                .stream("count-topic");
        stream.filter((k,v)-> k.contains(key));

        KafkaStreams application = factory.createStream(builder.build());
        application.start();
        return application;
    }



}
