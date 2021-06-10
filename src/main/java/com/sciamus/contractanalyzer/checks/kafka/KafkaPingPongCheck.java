package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.StreamSupport;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {

    final String name = "KafkaPingPongCheck";

    private final KafkaConsumFactory kafkaConsumFactory;


    private final KafkaProducFactory kafkaProducFactory;

    private final ReportService reportService;

    //2 strategie: albo assign() albo subscribe()


    public KafkaPingPongCheck(KafkaConsumFactory kafkaConsumFactory, KafkaProducFactory kafkaProducFactory, ReportService reportService) {
        this.kafkaConsumFactory = kafkaConsumFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.reportService = reportService;
    }


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        final Logger logger = LogManager.getLogger(KafkaPingPongCheck.class);

        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);


        Iterable<ConsumerRecord<String, String>> records;

        records = consumer.poll(Duration.ofSeconds(3)).records(incomingTopic);

        logger.info("records after first poll");
        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after initial poll " + record.value());
        }


        //        logger.info("consumer position is " + getConsumerPosition(consumer, partitionSet));


        CheckReportBuilder reportBuilder = new CheckReportBuilder();
        reportBuilder.setNameOfCheck("kafka check").createTimestamp();

        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);

        String messageToProduce = "ping" + (new Random().nextInt(10));

        String correctFetchedMessage = messageToProduce + "pong";

        producer.send(outgoingTopic, 0, null, messageToProduce);


        logger.info("before poll");
        records = consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);

//        logger.info("consumer position after polling " + getConsumerPosition(consumer, partitionSet));


        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after second poll " + record.value());
        }

//        logger.info("position before sync: "+getConsumerPosition(consumer,partitionSet));

        consumer.close();

        String additionalReportInfo = "incoming topic: " + incomingTopic + " outgoing topic: " + outgoingTopic + " ";

        reportBuilder.setReportBody(additionalReportInfo);


        if (StreamSupport.stream(records.spliterator(), false)
                .map(r -> r.value())
//                .peek(System.out::println)
                .anyMatch(p -> p.equals(correctFetchedMessage))) {

            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.PASSED)
                    .addTextToBody("messageToProduce should be: " +
                            "" + correctFetchedMessage +
                            " and indeed was")
                    .build());
        } else {
            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.FAILED)
                    .addTextToBody("Sorry, we couldn't find the correct messageToProduce: " + correctFetchedMessage
                    )
                    .build());
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    private Set<TopicPartition> getTopicPartitions(String incomingTopic) {
        TopicPartition partitionDefinition = new TopicPartition(incomingTopic, 0);

        Set<TopicPartition> partitionSet = new HashSet<>(Collections.singleton(partitionDefinition));
        return partitionSet;
    }

    private long getConsumerPosition(Consumer<String, String> consumer, Set<TopicPartition> topicPartitions) {
        return consumer.position(topicPartitions.iterator().next());
    }

}