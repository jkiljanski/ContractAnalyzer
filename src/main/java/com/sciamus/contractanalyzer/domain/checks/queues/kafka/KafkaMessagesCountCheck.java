package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KafkaMessagesCountCheck implements KafkaContractCheck {

    private final String name = "KafkaMessagesCountCheck";

    private final KafkaStreamFactory kafkaStreamFactory;

    private final KafkaProducFactory kafkaProducFactory;

    private final KafkaConsumFactory kafkaConsumFactory;

    @Autowired
    public KafkaMessagesCountCheck(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        Logger logger = getLogger();

        KafkaTemplate<String, String> producer = getProducer(host, port);

        Consumer consumer = getConsumer(incomingTopic, host, port);

        String checkUniqueIdentifier = getCheckUniqueIdentifier();

        List<Integer> integersListToSendToOutsideProcessor = getIntegersListToSendToOutsideProcessor();

        Map<String, Long> expectedAnswerToGetFromOutsideProcessor = getExpectedAnswer(checkUniqueIdentifier, integersListToSendToOutsideProcessor);

        logAnswer(logger, expectedAnswerToGetFromOutsideProcessor);

        sendMessagesToOutsideProcessor(outgoingTopic, producer, checkUniqueIdentifier, integersListToSendToOutsideProcessor);

        setUpConsumer(consumer);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterable<ConsumerRecord<String, String>> records = getRecordsFromOutsideProcessor(incomingTopic, consumer);

        Map<String, Long> answerToCheck = new HashMap<>();

        consumer.close();

        processReceivedRecords(logger, checkUniqueIdentifier, records, answerToCheck);

        logAnswer(logger, answerToCheck);

        Boolean resultOfCheck = expectedAnswerToGetFromOutsideProcessor.equals(answerToCheck);

        logCheckResult(logger, expectedAnswerToGetFromOutsideProcessor, answerToCheck);

        CheckReportBuilder reportBuilder = new CheckReportBuilder();

        setUpReportBuilder(reportBuilder);

        if (resultOfCheck) {
            return getPassedCheckReport(reportBuilder);
        }

        return getFailedCheckReport(expectedAnswerToGetFromOutsideProcessor, answerToCheck, reportBuilder);
    }

    private void logCheckResult(Logger logger, Map<String, Long> expectedAnswerToGetFromOutsideProcessor, Map<String, Long> answerToCheck) {
        logger.info("And the answer is " + expectedAnswerToGetFromOutsideProcessor.equals(answerToCheck));
    }

    private void setUpReportBuilder(CheckReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Consumer getConsumer(String incomingTopic, String host, String port) {
        return kafkaConsumFactory.createConsumer(incomingTopic, host, port);
    }

    private KafkaTemplate<String, String> getProducer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private Logger getLogger() {
        Logger logger = LogManager.getLogger(KafkaMessagesCountCheck.class);
        return logger;
    }

    private CheckReport getFailedCheckReport(Map<String, Long> expectedAnswer, Map<String, Long> answerToCheck, CheckReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.FAILED)
                .setReportBody("Sorry, please have another shot. " +
                        "Correct answer is: " + expectedAnswer + "\n" +
                        " your system produced this: " + answerToCheck)
                .build();
    }

    private CheckReport getPassedCheckReport(CheckReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.PASSED)
                .setReportBody("Your system produced a good answer. You deserve a hug.")
                .build();
    }

    private void processReceivedRecords(Logger logger, String checkUniqueKey, Iterable<ConsumerRecord<String, String>> records, Map<String, Long> answerToCheck) {
        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after second poll " + record.key() + "---" + record.value());
            if (record.key().contains(checkUniqueKey)) {

                Try<Long> longTry = Try.of(() -> Long.valueOf(record.value()));

                answerToCheck.put(record.key(), longTry.getOrElseThrow(() -> new RuntimeException("Sorry, I cannot convert the value you provided to Long type")));
            }
        }
    }

    private Iterable<ConsumerRecord<String, String>> getRecordsFromOutsideProcessor(String incomingTopic, Consumer consumer) {
        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);
        return records;
    }

    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private void sendMessagesToOutsideProcessor(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<Integer> integersListToSendToTopic) {
        sendMessagesToIgnore(outgoingTopic, producer);

        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, integersListToSendToTopic);

        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendMessagesToIgnore(outgoingTopic, producer);
        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, Collections.singletonList("compute"));

    }

    private void sendMessagesToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<? super Integer> toSendToTopic) {
        for (Object element : toSendToTopic) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.send(outgoingTopic, checkUniqueKey, String.valueOf(element));
        }
    }

    private void sendMessagesToIgnore(String outgoingTopic, KafkaTemplate<String, String> producer) {
        for (int i = 0; i < 5; i++) {
            producer.send(outgoingTopic, "to be ignored");
        }
    }

    private void logAnswer(Logger logger, Map<String, Long> expectedAnswer) {
        for (Map.Entry entry : expectedAnswer.entrySet()) {

            logger.info(entry);
        }
    }

    private String getCheckUniqueIdentifier() {
        String checkUniqueKey = new Random().nextInt(10) + "--test--";
        return checkUniqueKey;
    }

    private Map<String, Long> getExpectedAnswer(String checkUniqueKey, List<Integer> toSend) {
        Map<String, Long> expectedAnswer = toSend
                .stream()
                .map(i -> checkUniqueKey + i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return expectedAnswer;
    }

    private List<Integer> getIntegersListToSendToOutsideProcessor() {
        List<Integer> toSend = Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
        return toSend;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
