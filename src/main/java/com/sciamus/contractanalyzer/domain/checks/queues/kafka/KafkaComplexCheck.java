package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.logging.log4j.LogManager.getLogger;

public class KafkaComplexCheck implements KafkaCheck {

    final String name = "KafkaComplexCheck";

    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private ReportBuilder reportBuilder = new ReportBuilder();

    final Logger logger = getLogger();

    public KafkaComplexCheck(KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {

        //given:
        KafkaTemplate<String, String> producer = Producer(host, port);
        Consumer<String, String> consumer = Consumer(incomingTopic, host, port);

        String messageUniqueIdentifier = setMessageUniqueIdentifier();
        String firstMessageUniqueIdentifier = messageUniqueIdentifier + "1";
        String secondMessageUniqueIdentifier = messageUniqueIdentifier + "2";

        List<Integer> integersListToSendToOutsideProcessor1 = getIntegersListToSendToOutsideProcessor();
        List<Integer> integersListToSendToOutsideProcessor2 = getIntegersListToSendToOutsideProcessor();

        String seqOfNumbersToSendToOutsideProcessor1 = convertListOfIntegersToStringSeq(integersListToSendToOutsideProcessor1);
        String seqOfNumbersToSendToOutsideProcessor2 = convertListOfIntegersToStringSeq(integersListToSendToOutsideProcessor2);

        //when:
        sendMessageToOutsideSystem(outgoingTopic, producer, seqOfNumbersToSendToOutsideProcessor1, firstMessageUniqueIdentifier);
        sendMessageToOutsideSystem(outgoingTopic, producer, seqOfNumbersToSendToOutsideProcessor2, secondMessageUniqueIdentifier);
        List<Integer> expectedAnswer = getExpectedAnswer(integersListToSendToOutsideProcessor1, integersListToSendToOutsideProcessor2);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordFromOutsideSystem(incomingTopic, consumer);
        List<Integer> actualAnswer = checkIfReceivedRecordsHaveUniqueIdentifier(recordsFromOutsideSystem);

        //then:
        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer))
            return getPassedCheckReport(reportBuilder);
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private KafkaTemplate<String, String> Producer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private Consumer<String, String> Consumer(String incomingTopic, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);
        setUpConsumer(consumer);
        return consumer;
    }

    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private String setMessageUniqueIdentifier() {
        return UUID.randomUUID() + "--test--";
    }

    private List<Integer> getIntegersListToSendToOutsideProcessor() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    private String convertListOfIntegersToStringSeq(List<Integer> integersList) {
        StringBuilder seqOfNumbers = new StringBuilder();
        for (Integer integer : integersList) {
            seqOfNumbers.append(integer.intValue());
        }
        return seqOfNumbers.toString();
    }

    private Iterable<ConsumerRecord<String, String>> waitForRecordFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(10)).records(incomingTopic);
        consumer.close();
        return records;
    }

    private List<Integer> checkIfReceivedRecordsHaveUniqueIdentifier(Iterable<ConsumerRecord<String, String>> records) {

        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after second poll: key" + record.key() + " value: " + record.value()
                    + " offset: " + record.offset()
                    + " timestamp " + record.timestamp());

            List<Integer> receivedNumbers = Stream.of(record.value()
                    .split("/"))
                    .map(pair -> Try.of(
                            () -> Integer.valueOf(pair))
                            .getOrElseThrow(() ->
                                    new RuntimeException("Sorry, I cannot convert the value you provided to Integer type")))
                    .collect(Collectors.toList());
            return receivedNumbers;
        }
        return null;
    }

    private void sendMessageToOutsideSystem(String outgoingTopic, KafkaTemplate<String, String> producer,
                                            String seqOfNumbersToSendToTopic, String messageUniqueIdentifier) {
        sendMessageToBeChecked(outgoingTopic, producer, messageUniqueIdentifier, seqOfNumbersToSendToTopic);
    }

    private void sendMessageToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String messageUniqueIdentifier, String toSendToTopic) {
        producer.send(outgoingTopic, messageUniqueIdentifier, toSendToTopic);
    }

    private List<Integer> getExpectedAnswer(List<Integer> toSend1, List<Integer> toSend2) {
        List<Integer> result = new ArrayList<>();
        int number;
        for (int i = 0; i < 10; i += 2) {
            number = 10 * (toSend1.get(i) + toSend2.get(i)) + toSend1.get(i + 1) + toSend2.get(i + 1);
            result.add(number);
        }
        result.sort(Collections.reverseOrder());
        return result;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Report getFailedCheckReport(List<Integer> expectedAnswer, List<Integer> answerToCheck, ReportBuilder reportBuilder) {
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
