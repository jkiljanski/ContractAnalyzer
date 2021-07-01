package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReport;
import com.sciamus.contractanalyzer.domain.checks.reports.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
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

public class KafkaMessagesSimpleCountCheck implements KafkaContractCheck {

    private final String name = "KafkaMessagesSimpleCountCheck";
    private final KafkaStreamFactory kafkaStreamFactory;
    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;



    @Autowired
    public KafkaMessagesSimpleCountCheck(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        //given:

        KafkaTemplate<String, String> producer = Producer(host, port);
        Consumer<String, String> consumer = Consumer(incomingTopic, host, port);
        String checkUniqueIdentifier = getCheckUniqueIdentifier();
        List<Integer> integersListToSendToOutsideProcessor = getIntegersListToSendToOutsideProcessor();
        CheckReportBuilder reportBuilder = new CheckReportBuilder();


        //when:

        sendMessagesToOutsideSystemAndGoToSleep(outgoingTopic, producer, checkUniqueIdentifier, integersListToSendToOutsideProcessor);
        Map<String, Long> expectedAnswer = getExpectedAnswer(checkUniqueIdentifier, integersListToSendToOutsideProcessor);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordsFromOutsideSystem(incomingTopic, consumer);
        Map<String, Long> actualAnswer = checkIfReceivedRecordsHaveUniqueIdentifier(checkUniqueIdentifier, recordsFromOutsideSystem);


        //then:

        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer)) {
            return getPassedCheckReport(reportBuilder);
        }
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private void setUpReportBuilder(CheckReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
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

    private Logger getLogger() {
        Logger logger = LogManager.getLogger(KafkaMessagesSimpleCountCheck.class);
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

    private Map<String, Long> checkIfReceivedRecordsHaveUniqueIdentifier(String checkUniqueKey, Iterable<ConsumerRecord<String, String>> records) {

        Map<String, Long> answerToCheck = new HashMap<>();

        Logger logger = getLogger();

        for (ConsumerRecord<String, String> record : records) {

            logger.info("logging records after second poll: key" + record.key() + " value: " + record.value()
                    + " offset: " + record.offset()
                    + " timestamp " + record.timestamp());
            if (record.key().startsWith(checkUniqueKey)) {

                Try<Long> longTry = Try.of(() -> Long.valueOf(record.value()));

                answerToCheck.put(record.key(), longTry.getOrElseThrow(() -> new RuntimeException("Sorry, I cannot convert the value you provided to Long type")));
            }

        }
        return answerToCheck;
    }


    private Iterable<ConsumerRecord<String, String>> waitForRecordsFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(20)).records(incomingTopic);
        consumer.close();
        return records;
    }


    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private void sendMessagesToOutsideSystemAndGoToSleep(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<Integer> integersListToSendToTopic) {
        sendMessagesToIgnore(outgoingTopic, producer);

        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, integersListToSendToTopic);
        sendMessagesToIgnore(outgoingTopic, producer);
        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, Collections.singletonList("compute"));

        Try.run(()-> Thread.sleep(30_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);


    }

    private void sendMessagesToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<? super Integer> toSendToTopic) {
        for (Object element : toSendToTopic) {


            producer.send(outgoingTopic, checkUniqueKey, String.valueOf(element));

            Try.run(()-> Thread.sleep(30_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);

        }
    }

    private void sendMessagesToIgnore(String outgoingTopic, KafkaTemplate<String, String> producer) {
        for (int i = 0; i < 5; i++) {
            producer.send(outgoingTopic, "to be ignored");
        }
    }

    private String getCheckUniqueIdentifier() {
        return UUID.randomUUID() + "--test--";
    }

    private Map<String, Long> getExpectedAnswer(String checkUniqueKey, List<Integer> toSend) {

        return toSend
                .stream()
                .map(i -> checkUniqueKey + i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<Integer> getIntegersListToSendToOutsideProcessor() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
