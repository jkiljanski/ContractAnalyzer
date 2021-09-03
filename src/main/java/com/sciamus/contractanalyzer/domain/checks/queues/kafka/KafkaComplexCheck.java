package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class KafkaComplexCheck implements KafkaCheck {

    final String name = "KafkaComplexCheck";

    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private final ReportBuilder reportBuilder = new ReportBuilder();

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

        List<Integer> randomIntegers1 = generateRandomIntegers();
        List<Integer> randomIntegers2 = generateRandomIntegers();

        String outsideProcessorMessage1 = convertRandomIntegersToMessage(randomIntegers1);
        String outsideProcessorMessage2 = convertRandomIntegersToMessage(randomIntegers2);

        //when:
        sendMessageToOutsideSystem(outgoingTopic, producer, outsideProcessorMessage1, firstMessageUniqueIdentifier);
        sendMessageToOutsideSystem(outgoingTopic, producer, outsideProcessorMessage2, secondMessageUniqueIdentifier);
        List<Integer> expectedAnswer = getExpectedAnswer(randomIntegers1, randomIntegers2);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordFromOutsideSystem(incomingTopic, consumer);
        Option<List<Integer>> actualAnswer = getActualAnswer(recordsFromOutsideSystem, messageUniqueIdentifier);

        //then:
        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer.getOrNull()))
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

    private List<Integer> generateRandomIntegers() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    private String convertRandomIntegersToMessage(List<Integer> integersList) {
        return integersList
                .stream()
                .map(number -> Integer.toString(number))
                .collect(Collectors.joining());
    }

    private Iterable<ConsumerRecord<String, String>> waitForRecordFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(10)).records(incomingTopic);
        consumer.close();
        return records;
    }

    private Option<List<Integer>> getActualAnswer(Iterable<ConsumerRecord<String, String>> records, String messageUniqueIdentifier) {

        final List[] receivedNumbers = new List[]{new ArrayList<>()};

        records.forEach(record -> {
                if (record.key().equals(messageUniqueIdentifier)) {
                    receivedNumbers[0] = Stream.of(record.value()
                    .split("/"))
                    .map(pair -> Try.of(
                    () -> Integer.valueOf(pair))
                    .getOrElseThrow(() ->
                            new RuntimeException("Sorry, I cannot convert " + record.value() + " to Integer type")))
                    .collect(Collectors.toList());
                }
        });
        return Option.of(receivedNumbers[0]);
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

    private Report getFailedCheckReport(List<Integer> expectedAnswer, Option<List<Integer>> answerToCheck, ReportBuilder reportBuilder) {
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
