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

    public KafkaTransformationCheck(KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {
        //given:
        KafkaTemplate<String, String> producer = Producer(host, port);
        Consumer<String, String> consumer = Consumer(incomingTopic, host, port);
        String checkUniqueIdentifier = getCheckUniqueIdentifier();
        List<Integer> randomIntegers = generateRandomIntegers();
        String outsideProcessorMessage = convertRandomIntegersToMessage(randomIntegers);

        //when:
        sendMessageToOutsideSystem(outgoingTopic, checkUniqueIdentifier, producer, outsideProcessorMessage);
        Integer expectedAnswer = getExpectedAnswer(randomIntegers);

        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordFromOutsideSystem(incomingTopic, consumer);
        Option<Integer> actualAnswer = checkIfReceivedRecordsHaveUniqueIdentifier(recordsFromOutsideSystem);

        //then:
        setUpReportBuilder(reportBuilder);

        if (expectedAnswer.equals(actualAnswer.getOrNull()))
            return getPassedCheckReport(reportBuilder);
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private String convertRandomIntegersToMessage(List<Integer> integersList) {

        return integersList
                .stream()
                .map(number -> Integer.toString(number))
                .collect(Collectors.joining());
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

    private String getCheckUniqueIdentifier() {
        return UUID.randomUUID() + "--test--";
    }

    private Iterable<ConsumerRecord<String, String>> waitForRecordFromOutsideSystem(String incomingTopic, Consumer<String, String> consumer) {

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(10)).records(incomingTopic);
        consumer.close();
        return records;
    }

    private List<Integer> generateRandomIntegers() {
        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());
    }

    private void sendMessageToOutsideSystem(String outgoingTopic, String checkUniqueIdentifier, KafkaTemplate<String, String> producer, String outsideProcessorMessage) {
        producer.send(outgoingTopic, checkUniqueIdentifier, outsideProcessorMessage);
    }

    private Option<Integer> checkIfReceivedRecordsHaveUniqueIdentifier(Iterable<ConsumerRecord<String, String>> records) {
        final Integer[] intTry = {null};

        records.forEach(record -> {
            intTry[0] = Try.of(
                    () -> Integer.valueOf(record.value()))
                    .getOrElseThrow(() ->
                            new RuntimeException("Sorry, I cannot convert " + record.value() + " to Integer type"));
        });

        return Option.of(intTry[0]);
    }

    private Integer getExpectedAnswer(List<Integer> toSend) {
        int sum = 0;

        for (int i=0; i<toSend.size(); i+=2) {
            sum += toSend.get(i) * 10 + toSend.get(i+1);
        }
        return sum;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder
                .createTimestamp()
                .setNameOfCheck(getName());
    }

    private Report getFailedCheckReport(Integer expectedAnswer, Option<Integer> answerToCheck, ReportBuilder reportBuilder) {
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
