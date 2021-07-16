package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static io.vavr.API.*;


public class KafkaPingPongCheck implements KafkaContractCheck {

    final String name = "KafkaPingPongCheck";

    private final KafkaConsumFactory kafkaConsumFactory;

    private final KafkaProducFactory kafkaProducFactory;

    private final ReportService reportService;

    @Autowired
    public KafkaPingPongCheck(KafkaConsumFactory kafkaConsumFactory, KafkaProducFactory kafkaProducFactory, ReportService reportService) {
        this.kafkaConsumFactory = kafkaConsumFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.reportService = reportService;
    }

    //2 strategie: albo assign() albo subscribe()


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        final Logger logger = getLogger();

        Consumer<String, String> consumer = getConsumer(incomingTopic, host, port);

        Iterable<ConsumerRecord<String, String>> records = fetchConsumerRecords(incomingTopic, consumer);

        logger.info("records after first poll");

        logRecords(logger, records, "logging records after initial poll ");


        //        logger.info("consumer position is " + getConsumerPosition(consumer, partitionSet));


        CheckReportBuilder reportBuilder = getCheckReportBuilder();

        KafkaTemplate<String, String> producer = getProducer(host, port);

        String messageToSend = getMessageToProduce();

        String correctMessageToFetch = getCorrectFetchedMessage(messageToSend);

        sendMessage(outgoingTopic, producer, messageToSend);

        logger.info("before poll");
        records = getRecords(incomingTopic, consumer);

//        logger.info("consumer position after polling " + getConsumerPosition(consumer, partitionSet));


        logRecords(logger, records, "logging records after second poll ");

//        logger.info("position before sync: "+getConsumerPosition(consumer,partitionSet));

        closeConsumer(consumer);

        addTopicInfoToReport(incomingTopic, outgoingTopic, reportBuilder);


        return Match(StreamSupport.stream(records.spliterator(), false)
                .map(r -> r.value())
//                .peek(System.out::println)
                .anyMatch(p -> p.equals(correctMessageToFetch)))
                .of(
                    Case($(Predicate.isEqual(true)),

                        reportService.addReportToRepository(reportBuilder.setResult(ReportResults.PASSED)
                                .addTextToBody("messageToSend should be: " +
                                        "" + correctMessageToFetch +
                                        " and indeed was")
                                .build())
                    ),
                    Case($(),
                    reportService.addReportToRepository(reportBuilder.setResult(ReportResults.FAILED)
                            .addTextToBody("Sorry, we couldn't find the correct messageToSend: " + correctMessageToFetch)
                            .build())
                    ));
    }

    private void addTopicInfoToReport(String incomingTopic, String outgoingTopic, CheckReportBuilder reportBuilder) {
        String additionalReportInfo = "incoming topic: " + incomingTopic + " outgoing topic: " + outgoingTopic + " ";

        reportBuilder.setReportBody(additionalReportInfo);
    }

    private void closeConsumer(Consumer<String, String> consumer) {
        consumer.close();
    }

    private void logRecords(Logger logger, Iterable<ConsumerRecord<String, String>> records, String s) {
        records.forEach(record -> logger.info(s + record.value()));
    }

    private Iterable<ConsumerRecord<String, String>> getRecords(String incomingTopic, Consumer<String, String> consumer) {
        return consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);
    }

    private void sendMessage(String outgoingTopic, KafkaTemplate<String, String> producer, String messageToProduce) {
        producer.send(outgoingTopic, 0, null, messageToProduce);
    }

    private String getCorrectFetchedMessage(String messageToProduce) {
        String correctFetchedMessage = messageToProduce + "pong";
        return correctFetchedMessage;
    }

    private String getMessageToProduce() {
        String messageToProduce = "ping" + (new Random().nextInt(10));
        return messageToProduce;
    }

    private KafkaTemplate<String, String> getProducer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private CheckReportBuilder getCheckReportBuilder() {
        CheckReportBuilder reportBuilder = new CheckReportBuilder();
        reportBuilder.setNameOfCheck("kafka check").createTimestamp();
        return reportBuilder;
    }

    private Consumer<String, String> getConsumer(String incomingTopic, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);
        return consumer;
    }

    private Logger getLogger() {
        return LogManager.getLogger(KafkaPingPongCheck.class);
    }

    private Iterable<ConsumerRecord<String, String>> fetchConsumerRecords(String incomingTopic, Consumer<String, String> consumer) {
        Iterable<ConsumerRecord<String, String>> records;

        records = consumer.poll(Duration.ofSeconds(3)).records(incomingTopic);
        return records;
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