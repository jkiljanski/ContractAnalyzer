package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class KafkaMessagesManyRunsCheckWithVavr implements KafkaCheck {

    private static final int NUMBER_OF_CONCURRENT_RUNS = 10;
    private final String name = "KafkaMessagesManyRunsCheckWithVavr";
    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;
    private ReportBuilder reportBuilder = new ReportBuilder();


    public KafkaMessagesManyRunsCheckWithVavr(KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {

        //given
        Map<String, List<String>> messagesToSend = createMessagesForXRuns(NUMBER_OF_CONCURRENT_RUNS); //Multimap
        Consumer<String, String> consumer = createAndSetUpConsumer(incomingTopic, host, port);


        //when
        sendMessagesWithDelay(messagesToSend, 5L, host, port, outgoingTopic);
        sendComputeCommands(messagesToSend.keySet(), host, port, outgoingTopic);

        Try.run(() -> Thread.sleep(30_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);

        //then
        Map<String, String> expectedResults = computeExpectedResults(messagesToSend);

        Map<String, String> actualResults = waitForResults(Duration.ofSeconds(10), incomingTopic, consumer);


        Try<Void> finalCheckResult = assertResultsMatch(actualResults, expectedResults);

        setUpReportBuilder(reportBuilder);

//        if (finalCheckResult.isEmpty()) {
//            return getPassedCheckReport(reportBuilder);
//        }

        return finalCheckResult
                .map(v -> getPassedCheckReport(reportBuilder))
                .recover(AssertionError.class, e -> createFailedCheckReport(e.getMessage(), reportBuilder))
                .recover(Exception.class, e -> createFailedCheckReport("Unexpected Exception: " + e.getMessage(), reportBuilder))
                .get();

//
//        return getFailedCheckReport(expectedResults, actualResults, reportBuilder);

    }

    private Try<Void> assertResultsMatch(Map<String, String> results, Map<String, String> expectedResults) {

        return Try.runRunnable(() -> assertThat(results).containsAll(expectedResults));

    }

    private Map<String, String> waitForResults(Duration ofSeconds, String topic, Consumer<String, String> consumer) {

        Logger logger = getLogger();

        logger.info("Setting up consumer and blocking thread");

        Try.run(() -> Thread.sleep(10_000)).onFailure(InterruptedException.class, Throwable::printStackTrace);

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(ofSeconds).records(topic);

        logConsumerOffset(consumer);

        records.iterator().forEachRemaining(s -> System.out.println("XDXDXD: " + s));

        consumer.close();

        return List.ofAll(records).toMap(ConsumerRecord::key, ConsumerRecord::value);

    }

    private void logConsumerOffset(Consumer<String, String> consumer) {

        Logger logger = getLogger();

        TopicPartition partition = new TopicPartition("output-topic", 0);

        long position = consumer.position(partition);
        logger.info("Consumer offset is: " + position);
    }

    private Map<String, String> computeExpectedResults(Map<String, List<String>> messagesToSend) {


        return HashMap.ofAll(messagesToSend.
                flatMap(s -> s._2().map(string -> s._1() + string))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                .map((k, v) -> new Tuple2<>(k, "" + v));
    }


    private void sendComputeCommands(Set<String> keySet, String host, String port, String topic) {

        KafkaTemplate<String, String> producer = createProducer(host, port);

        keySet
                .forEach(key -> producer.send(topic, key, "compute"));

    }

    private void sendMessagesWithDelay(Map<String, List<String>> messagesToSend, Long ofMillis, String host, String port, String topic) {

        KafkaTemplate<String, String> producer = createProducer(host, port);

        messagesToSend
                .flatMap(this::assignEachMessageWithUUID)
                .forEach(entry -> sendWithDelay(ofMillis, topic, producer, entry._1, entry._2));
    }

    private List<Tuple2<String, String>> assignEachMessageWithUUID(Tuple2<String, List<String>> tuple) {
        return tuple._2
                .map(number -> new Tuple2<>(tuple._1, number));
    }

    private void sendWithDelay(Long ofMillis, String topic, KafkaTemplate<String, String> producer, String key, String value) {

        Try.run(() -> Thread.sleep(ofMillis)).onFailure(InterruptedException.class, Throwable::printStackTrace);

        producer.send(topic, key, value);
    }

    private io.vavr.collection.Map<String, List<String>> createMessagesForXRuns(int numberOfConcurrentRuns) {

        HashMap<String, List<String>> results = HashMap.empty();



        for (int i = 0; i < numberOfConcurrentRuns; i++) {

            String checkUniqueIdentifier = getCheckUniqueIdentifier();

            List<Integer> integersListToSendToOutsideProcessor = get10IntegersListToSendToOutsideProcessor();

            List<String> randomChars = integersListToSendToOutsideProcessor
                    .map(number -> "" + ('z' - number))
                    .collect(List.collector());

            results.put(checkUniqueIdentifier, randomChars);
        }
        return results;
    }

    private void setUpReportBuilder(ReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Consumer<String, String> createAndSetUpConsumer(String incomingTopic, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);
        setUpConsumer(consumer);
        return consumer;
    }

    private KafkaTemplate<String, String> createProducer(String host, String port) {
        return kafkaProducFactory.createProducer(host, port);
    }

    private Logger getLogger() {
        return LogManager.getLogger(KafkaMessagesManyRunsCheckWithVavr.class);
    }

    private Report createFailedCheckReport(String message, ReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.FAILED)
                .setReportBody("Sorry, please have another shot. Assertion details: \n" + message)
                .build();
    }

    private Report getPassedCheckReport(ReportBuilder reportBuilder) {
        return reportBuilder
                .setResult(ReportResults.PASSED)
                .setReportBody("Your system produced a good answer. You deserve a hug.")
                .build();
    }


    private void setUpConsumer(Consumer<String, String> consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private String getCheckUniqueIdentifier() {
        return java.util.UUID.randomUUID() + "--test--";
    }

    private List<Integer> get10IntegersListToSendToOutsideProcessor() {

        return Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(List.collector());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
