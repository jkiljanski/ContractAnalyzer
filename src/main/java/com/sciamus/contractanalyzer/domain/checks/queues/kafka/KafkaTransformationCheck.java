package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KafkaTransformationCheck implements KafkaCheck {
    final String name = "KafkaTransformationCheck";

    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private ReportBuilder reportBuilder = new ReportBuilder();
    private String checkUniqueKey;

    public KafkaTransformationCheck(KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {
        //given:
        KafkaTemplate<String, String> producer = Producer(host, port);
        Consumer<String, String> consumer = Consumer(incomingTopic, host, port);
        setCheckUniqueIdentifier();
        List<Integer> integersListToSendToOutsideProcessor = getIntegersListToSendToOutsideProcessor();

        //when:
        sendMessagesToOutsideSystemAndGoToSleep(outgoingTopic, producer, integersListToSendToOutsideProcessor);
        Long expectedAnswer = getExpectedAnswer(integersListToSendToOutsideProcessor);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordsFromOutsideSystem(incomingTopic, consumer);
        Long actualAnswer = checkIfReceivedRecordsHaveUniqueIdentifier(recordsFromOutsideSystem);

        //then:
        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer))
            return getPassedCheckReport(reportBuilder);
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private Consumer<String, String> Consumer(String incomingTopic, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);
        setUpConsumer(consumer);
        return consumer;
    }

    private KafkaTemplate<String, String> Producer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private void setCheckUniqueIdentifier() {
        this.checkUniqueKey = UUID.randomUUID() + "--test--";
    }

    private Iterable<ConsumerRecord<String, String>> waitForRecordsFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(20)).records(incomingTopic);
        consumer.close();
        return records;
    }

    private List<Integer> getIntegersListToSendToOutsideProcessor() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    private void sendMessagesToOutsideSystemAndGoToSleep(String outgoingTopic, KafkaTemplate<String, String> producer, List<Integer> integersListToSendToTopic) {
        sendMessagesToIgnore(outgoingTopic, producer);

        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, integersListToSendToTopic);
        sendMessagesToIgnore(outgoingTopic, producer);
        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, Collections.singletonList("compute"));

        Try.run(() -> Thread.sleep(30_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);
    }

    private void sendMessagesToIgnore(String outgoingTopic, KafkaTemplate<String, String> producer) {
        for (int i = 0; i < 5; i++) {
            producer.send(outgoingTopic, "to be ignored");
        }
    }

    private void sendMessagesToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<? super Integer> toSendToTopic) {

        toSendToTopic.forEach(element -> {

            producer.send(outgoingTopic, checkUniqueKey, String.valueOf(element));

            Try.run(() -> Thread.sleep(30_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);
        });
    }

    private Long checkIfReceivedRecordsHaveUniqueIdentifier(Iterable<ConsumerRecord<String, String>> records) {

        Long answerToCheck = 0L;

        Logger logger = getLogger();

        int numberCounter = 0;

        for (ConsumerRecord<String, String> record : records) {

            logger.info("logging records after second poll: key" + record.key() + " value: " + record.value()
                    + " offset: " + record.offset()
                    + " timestamp " + record.timestamp());
            if (record.key().contains(checkUniqueKey)) {
                Long longTry = Try.of(
                        () -> Long.valueOf(Character.getNumericValue(record.key().charAt(record.key().length() - 1))))
                        .getOrElseThrow(
                                () -> new RuntimeException("Sorry, I cannot convert the value you provided to Long type"));
                logger.info("Num: " + longTry);

                if (numberCounter % 2 == 0)
                    answerToCheck += 10 * longTry;
                else
                    answerToCheck += longTry;
            }
            ++numberCounter;
        }
        return answerToCheck;
    }

    private Logger getLogger() {
        Logger logger = LogManager.getLogger(KafkaMessagesSimpleCountCheck.class);
        return logger;
    }

    private Long getExpectedAnswer(List<Integer> toSend) {
        Long sum = 0L;
        for(int i=0; i<10; i+=2) {
            sum += toSend.get(i)*10+toSend.get(i+1);
        }
        return sum;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Report getFailedCheckReport(Long expectedAnswer, Long answerToCheck, ReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.FAILED)
                .setReportBody("Sorry, please have another shot. " +
                        "Correct answer is: " + expectedAnswer + "\n" +
                        " your system produced this: " + answerToCheck)
                .build();
    }

    private Report getPassedCheckReport(ReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.PASSED)
                .setReportBody("Your system produced a good answer. You deserve a hug.")
                .build();
    }

    @Override
    public String getName() {
        return this.name;
    }

}
