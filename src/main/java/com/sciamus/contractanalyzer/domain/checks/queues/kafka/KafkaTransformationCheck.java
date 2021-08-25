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
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KafkaTransformationCheck implements KafkaCheck {
    final String name = "KafkaTransformationCheck";

    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private ReportBuilder reportBuilder = new ReportBuilder();
    private String checkUniqueKey;

    final Logger logger = getLogger();

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
        String seqOfNumbersToSendToOutsideProcessor = convertListOfIntegersToStringSeq(integersListToSendToOutsideProcessor);

        //when:
        sendMessagesToOutsideSystemAndGoToSleep(outgoingTopic, producer, seqOfNumbersToSendToOutsideProcessor);
        Integer expectedAnswer = getExpectedAnswer(integersListToSendToOutsideProcessor);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordFromOutsideSystem(incomingTopic, consumer);
        Integer actualAnswer = checkIfReceivedRecordsHaveUniqueIdentifier(recordsFromOutsideSystem);

        //then:
        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer))
            return getPassedCheckReport(reportBuilder);
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private String convertListOfIntegersToStringSeq(List<Integer> integersList) {
        StringBuilder seqOfNumbers = new StringBuilder();
        for (int i=0; i< integersList.size(); i++) {
            seqOfNumbers.append(integersList.get(i).intValue());
        }
        String a = seqOfNumbers.toString();
        return seqOfNumbers.toString();
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

    private Iterable<ConsumerRecord<String, String>> waitForRecordFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(10)).records(incomingTopic);
        consumer.close();
        return records;
    }

    private List<Integer> getIntegersListToSendToOutsideProcessor() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    private void sendMessagesToOutsideSystemAndGoToSleep(String outgoingTopic, KafkaTemplate<String, String> producer, String seqOfNumbersToSendToOutsideProcessor) {
        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, seqOfNumbersToSendToOutsideProcessor);
    }

    private void sendMessagesToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, String toSendToTopic) {
        producer.send(outgoingTopic, checkUniqueKey, toSendToTopic);
    }

    private Integer checkIfReceivedRecordsHaveUniqueIdentifier(Iterable<ConsumerRecord<String, String>> records) {

        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after second poll: key" + record.key() + " value: " + record.value()
                    + " offset: " + record.offset()
                    + " timestamp " + record.timestamp());
                Integer intTry = Try.of(
                        () -> Integer.valueOf(record.value()))
                        .getOrElseThrow(() ->
                                new RuntimeException("Sorry, I cannot convert the value you provided to Integer type"));
            return intTry;
        }
        return null;
    }

    private Logger getLogger() {
        return LogManager.getLogger(KafkaMessagesSimpleCountCheck.class);
    }

    private Integer getExpectedAnswer(List<Integer> toSend) {
        int sum = 0;

        for (int i=0; i<toSend.size(); i+=2) {
            sum += toSend.get(i) * 10 + toSend.get(i+1);
        }
        return sum;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Report getFailedCheckReport(Integer expectedAnswer, Integer answerToCheck, ReportBuilder reportBuilder) {
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
