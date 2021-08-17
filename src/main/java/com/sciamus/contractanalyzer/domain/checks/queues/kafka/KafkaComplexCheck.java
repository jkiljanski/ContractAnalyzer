package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KafkaComplexCheck implements KafkaCheck {

    final String name = "KafkaComplexCheck";

    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private ReportBuilder reportBuilder = new ReportBuilder();
    private String checkUniqueKey;

    public KafkaComplexCheck(KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {

        //given:
        List<String> incomingTopics = new ArrayList<>();
        incomingTopics.add(incomingTopic + "1");
        incomingTopics.add(incomingTopic + "2");

        KafkaTemplate<String, String> producer = Producer(host, port);
        Consumer<String, String> consumer = Consumer(incomingTopics, host, port);
        setCheckUniqueIdentifier();
        List<Integer> integersListToSendToOutsideProcessor1 = getIntegersListToSendToOutsideProcessor();
        List<Integer> integersListToSendToOutsideProcessor2 = getIntegersListToSendToOutsideProcessor();

        //when:
        sendMessagesToOutsideSystemAndGoToSleep(outgoingTopic, producer, integersListToSendToOutsideProcessor1);
        sendMessagesToOutsideSystemAndGoToSleep(outgoingTopic, producer, integersListToSendToOutsideProcessor2);
        List<Long> expectedAnswer = getExpectedAnswer(integersListToSendToOutsideProcessor1, integersListToSendToOutsideProcessor2);
//
//        Iterable<ConsumerRecord<String, String>> recordsFromOutsideSystem = waitForRecordsFromOutsideSystem(incomingTopic, consumer);
        List<Long> actualAnswer = new ArrayList<>();
//
//        //then:
        setUpReportBuilder(reportBuilder);
//
        if (expectedAnswer.equals(actualAnswer))
            return getPassedCheckReport(reportBuilder);
        return getFailedCheckReport(expectedAnswer, actualAnswer, reportBuilder);
    }

    private KafkaTemplate<String, String> Producer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private Consumer<String, String> Consumer(List<String> incomingTopics, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createMultiTopicConsumer(incomingTopics, host, port);
        setUpConsumer(consumer);
        return consumer;
    }

    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private void setCheckUniqueIdentifier() {
        this.checkUniqueKey = UUID.randomUUID() + "--test--";
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

    private List<Long> getExpectedAnswer(List<Integer> toSend1, List<Integer> toSend2) {
        List<Long> result = new ArrayList<>();
        int number;
        for(int i=0; i<10; i+=2) {
            number = 10 * (toSend1.get(i) + toSend2.get(i)) + toSend1.get(i+1) + toSend2.get(i+1);
            result.add(Long.valueOf(number));
        }
        result.sort(Collections.reverseOrder());
        return result;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Report getFailedCheckReport(List<Long> expectedAnswer, List<Long> answerToCheck, ReportBuilder reportBuilder) {
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
