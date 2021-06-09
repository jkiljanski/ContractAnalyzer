package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;


@Component
public class KTableCheck implements KafkaContractCheck {

    private final String name = "KTableCheck";
    private final KafkaStreamFactory kafkaStreamFactory;
    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;


    public KTableCheck(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        Logger logger = LogManager.getLogger(KTableCheck.class);

        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);


        Consumer consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);


        for (int i = 0; i <= 20; i++) {
            producer.send(outgoingTopic, String.valueOf(new Random().nextInt(10)));
        }

        producer.send(outgoingTopic, "compute");


//
//
//        StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        KStream<String, String> kStream = streamsBuilder.stream(outgoingTopic);
//
//        KTable<String, Long> kTable = kStream
//                .groupBy((k, v) -> v)
//                .count();
//
//        kTable.toStream()
//                .mapValues(v -> String.valueOf(v))
//                .peek((k, v) -> {
//                    System.out.println("hello!! key " + k + " value " + v);
//                })
//                .to(incomingTopic);
//
//        KafkaStreams application = kafkaStreamFactory.createStream(host, port, streamsBuilder.build());


        consumer.poll(Duration.ofSeconds(3));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Iterable<ConsumerRecord<String, Long>> records = consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);


        for (ConsumerRecord<String, Long> record : records) {
            logger.info("logging records after second poll " + record.key() + "---" + record.value());
        }

        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
