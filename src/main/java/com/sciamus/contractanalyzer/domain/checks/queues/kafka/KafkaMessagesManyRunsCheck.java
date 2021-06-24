package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaProperties;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;


@Component
public class KafkaMessagesManyRunsCheck implements KafkaContractCheck {

    private static final int NUMBER_OF_CONCURRENT_RUNS = 10;
    private final String name = "KafkaMessagesManyRunsCheck";
    private final KafkaStreamFactory kafkaStreamFactory;
    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;


    public KafkaMessagesManyRunsCheck(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }


    public static void main(String[] args) {


        KafkaProperties props = new KafkaProperties();

        props.setConsum(new KafkaProperties.Consum());
        props.getConsum().setKeyDeserializer("org.apache.kafka.common.serialization.StringDeserializer");
        props.getConsum().setValueDeserializer("org.apache.kafka.common.serialization.StringDeserializer");


        KafkaMessagesManyRunsCheck kafkaMessagesCountCheck = new KafkaMessagesManyRunsCheck(new KafkaStreamFactory(props), new KafkaProducFactory(props), new KafkaConsumFactory(props));

        Consumer<String, String> consumer = kafkaMessagesCountCheck.createConsumer("output-topic", "localhost", "29092");


        Iterable<ConsumerRecord<String, String>> recordsFromOutsideProcessor = kafkaMessagesCountCheck.getRecordsFromOutsideProcessor("output-topic", consumer);

        System.out.println(StreamSupport.stream(recordsFromOutsideProcessor.spliterator(), false).collect(Collectors.toList()).size());

    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {





        //given
        Map<String, List<String>> messagesToSend = createMessagesForXRuns(NUMBER_OF_CONCURRENT_RUNS); //Multimap
        Consumer<String, String> consumer = createConsumer(incomingTopic, host, port);


        //when
        sendMessagesWithDelay(messagesToSend, 5L, host, port, outgoingTopic);
        sendComputeMessages(messagesToSend.keySet(), host, port, outgoingTopic);


        try {
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //then
        Map<String, String> expectedResults = computeExpectedResults(messagesToSend);


        Map<String, String> results = waitForResults(Duration.ofSeconds(10), incomingTopic,consumer);
        Optional<String> result = assertResultsMatch(results, expectedResults);//results.size > expectedResults.size -> results.contains(expectedResults);


        CheckReportBuilder reportBuilder = new CheckReportBuilder();

        setUpReportBuilder(reportBuilder);

        if (result.isEmpty()) {
            return getPassedCheckReport(reportBuilder);
        }

        return getFailedCheckReport(expectedResults, results, reportBuilder);

        ///






       /* Logger logger = getLogger();

        KafkaTemplate<String, String> producer = getProducer(host, port);

        Consumer consumer = createConsumer(incomingTopic, host, port);

        String checkUniqueIdentifier = getCheckUniqueIdentifier();

        List<Integer> integersListToSendToOutsideProcessor = get10IntegersListToSendToOutsideProcessor();

        Map<String, Long> expectedAnswerToGetFromOutsideProcessor = getExpectedAnswer(checkUniqueIdentifier, integersListToSendToOutsideProcessor);

        logExpectedAnswer(logger, expectedAnswerToGetFromOutsideProcessor);

        sendMessagesToOutsideProcessor(outgoingTopic, producer, checkUniqueIdentifier, integersListToSendToOutsideProcessor);
*/

        // do kontenera:

        /*
        setUpConsumer(consumer);

        try {
            //Changed:
            Thread.sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterable<ConsumerRecord<String, String>> records = getRecordsFromOutsideProcessor(incomingTopic, consumer);

        Map<String, Long> answerToCheck = new HashMap<>();

//        String consumData = consumer.groupMetadata().toString();

//        consumer.commitSync();

        consumer.close(Duration.ofSeconds(3));

        processReceivedRecords(logger, checkUniqueIdentifier, records, answerToCheck);

        Boolean resultOfCheck = expectedAnswerToGetFromOutsideProcessor.equals(answerToCheck);

        logCheckResult(logger, expectedAnswerToGetFromOutsideProcessor, answerToCheck);

        CheckReportBuilder reportBuilder = new CheckReportBuilder();

        setUpReportBuilder(reportBuilder);

        if (resultOfCheck) {
            return getPassedCheckReport(reportBuilder);
        }

        return getFailedCheckReport(expectedAnswerToGetFromOutsideProcessor, answerToCheck, reportBuilder);

         */
    }

    private Optional<String> assertResultsMatch(Map<String, String> results, Map<String, String> expectedResults) {


        try {
            assertThat(results).containsAllEntriesOf(expectedResults);
        } catch (AssertionError error) {

            return Optional.of(error.getMessage());
        }

        return Optional.empty();


    }

    private Map<String, String> waitForResults(Duration ofSeconds, String topic, Consumer consumer) {
        Logger logger = getLogger();


        logger.info("Setting up consumer and blocking thread");

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Iterable<ConsumerRecord<String, String>> records = consumer.poll(ofSeconds).records(topic);


        logConsumerOffset(consumer);

        records.iterator().forEachRemaining(s-> System.out.println("XDXDXD: "+s));

        consumer.close();

        Map<String, String> collect = StreamSupport.stream(records.spliterator(), false)
                .peek(record -> logger.info("KROWA logging after poll: " + record))
                .collect(Collectors.toMap(record -> record.key(), record -> record.value()));

        return collect;

    }

    private void logConsumerOffset(Consumer consumer) {

        Logger logger = getLogger();

        TopicPartition partition = new TopicPartition("output-topic",0);

        long position = consumer.position(partition);
        logger.info("Consumer offset is: " + position);
    }

    private Map<String, String> computeExpectedResults(Map<String, List<String>> messagesToSend) {

        Logger logger = getLogger();


        return messagesToSend.entrySet().stream()
                .peek(message -> logger.info("in expected results: " + message))
                .flatMap(stringListEntry -> stringListEntry.getValue().stream().map(string -> stringListEntry.getKey() + string))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> "" + entry.getValue()));
    }


    private void sendComputeMessages(Set<String> keySet, String host, String port, String topic) {

        KafkaTemplate<String, String> producer = createProducer(host, port);

        keySet.stream()
                .forEach(key -> producer.send(topic, key, "compute"));

    }

    private void sendMessagesWithDelay(Map<String, List<String>> messagesToSend, Long ofMillis, String host, String port, String topic) {

        KafkaTemplate<String, String> producer = createProducer(host, port);


        messagesToSend.entrySet().stream()
                .flatMap(stringListEntry -> stringListEntry.getValue().stream()
                        .map(character -> new Tuple2<>(stringListEntry.getKey(), character)))
                .forEach(entry -> sendWithDelay(ofMillis, topic, producer, entry._1, entry._2));

    }

    private void sendWithDelay(Long ofMillis, String topic, KafkaTemplate<String, String> producer, String element1, String element2) {
        try {
            Thread.sleep(ofMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.send(topic, element1, element2);
    }

    private Map<String, List<String>> createMessagesForXRuns(int numberOfConcurrentRuns) {

        Map<String, List<String>> results = new HashMap<>();


        for (int i = 0; i < numberOfConcurrentRuns; i++) {

            String checkUniqueIdentifier = getCheckUniqueIdentifier();

            List<Integer> integersListToSendToOutsideProcessor = get10IntegersListToSendToOutsideProcessor();

            List<String> randomChars = integersListToSendToOutsideProcessor
                    .stream()
                    .map(number -> "" + ('z' - number))
                    .collect(Collectors.toList());

            results.put(checkUniqueIdentifier, randomChars);
        }
        return results;


    }


    private void logCheckResult(Logger logger, Map<String, Long> expectedAnswerToGetFromOutsideProcessor, Map<String, Long> answerToCheck) {
        logger.info("And the answer is " + expectedAnswerToGetFromOutsideProcessor.equals(answerToCheck));
    }

    private void setUpReportBuilder(CheckReportBuilder reportBuilder) {
        reportBuilder.createTimestamp().setNameOfCheck(getName());
    }

    private Consumer<String, String> createConsumer(String incomingTopic, String host, String port) {
        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);
        setUpConsumer(consumer);
        return consumer;
    }

    private KafkaTemplate<String, String> createProducer(String host, String port) {
        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);
        return producer;
    }

    private Logger getLogger() {
        Logger logger = LogManager.getLogger(KafkaMessagesManyRunsCheck.class);
        return logger;
    }

    private CheckReport getFailedCheckReport(Map<String, String> expectedAnswer, Map<String, String> answerToCheck, CheckReportBuilder reportBuilder) {
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
            logger.info("logging records after second poll: key" + record.key() + " value: " + record.value()
                    + " offset: " + record.offset()
                    + " timestamp " + record.timestamp());
            if (record.key().startsWith(checkUniqueKey)) {

                Try<Long> longTry = Try.of(() -> Long.valueOf(record.value()));

                answerToCheck.put(record.key(), longTry.getOrElseThrow(() -> new RuntimeException("Sorry, I cannot convert the value you provided to Long type")));
            }
        }
    }


    private Iterable<ConsumerRecord<String, String>> getRecordsFromOutsideProcessor(String incomingTopic, Consumer consumer) {
        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(20)).records(incomingTopic);
        return records;
    }


    private void setUpConsumer(Consumer consumer) {
        consumer.poll(Duration.ofSeconds(5));
    }

    private void sendMessagesToOutsideProcessor(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<Integer> integersListToSendToTopic) {
        sendMessagesToIgnore(outgoingTopic, producer);

        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, integersListToSendToTopic);

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        sendMessagesToIgnore(outgoingTopic, producer);
        sendMessagesToBeChecked(outgoingTopic, producer, checkUniqueKey, Collections.singletonList("compute"));

    }

    private void sendMessagesToBeChecked(String outgoingTopic, KafkaTemplate<String, String> producer, String checkUniqueKey, List<? super Integer> toSendToTopic) {
        for (Object element : toSendToTopic) {


            producer.send(outgoingTopic, checkUniqueKey, String.valueOf(element));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendMessagesToIgnore(String outgoingTopic, KafkaTemplate<String, String> producer) {
        for (int i = 0; i < 5; i++) {
            producer.send(outgoingTopic, "to be ignored");
        }
    }

    private void logExpectedAnswer(Logger logger, Map<String, Long> expectedAnswer) {
        for (Map.Entry entry : expectedAnswer.entrySet()) {

            logger.info("expected answer " + entry);
        }
    }

    private String getCheckUniqueIdentifier() {
        String checkUniqueKey = UUID.randomUUID() + "--test--";
        return checkUniqueKey;
    }

    private Map<String, Long> getExpectedAnswer(String checkUniqueKey, List<Integer> toSend) {
        Map<String, Long> expectedAnswer = toSend
                .stream()
                .map(i -> checkUniqueKey + i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return expectedAnswer;
    }

    private List<Integer> get10IntegersListToSendToOutsideProcessor() {
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
