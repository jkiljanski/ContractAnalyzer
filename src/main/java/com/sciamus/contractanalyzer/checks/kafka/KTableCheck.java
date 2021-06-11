package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaProducFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import io.vavr.control.Try;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class KTableCheck implements KafkaContractCheck {

    private final String name = "KTableCheck";
    private final KafkaStreamFactory kafkaStreamFactory;
    private final KafkaProducFactory kafkaProducFactory;
    private final KafkaConsumFactory kafkaConsumFactory;


    public KTableCheck(KafkaStreamFactory kafkaStreamFactory, KafkaProducFactory kafkaProducFactory, KafkaConsumFactory kafkaConsumFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
        this.kafkaProducFactory = kafkaProducFactory;
        this.kafkaConsumFactory = kafkaConsumFactory;
    }

    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {


        Logger logger = LogManager.getLogger(KTableCheck.class);

        KafkaTemplate<String, String> producer = kafkaProducFactory.createProducer(host, port);


        Consumer consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);

        String checkUniqueKey = new Random().nextInt(10) + "--test--";

        List<Integer> toSend = Stream
                .generate(() -> new Random().nextInt(10))
                .limit(10)
                .collect(Collectors.toList());


        Map<String, Long> expectedAnswer = toSend
                .stream()
                .map(i -> checkUniqueKey+i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry entry : expectedAnswer.entrySet()) {

            logger.info(entry);
        }

        for (int i=0; i<5; i++) {
            producer.send(outgoingTopic,"to be ignored");
        }


        for (Integer element : toSend) {
            producer.send(outgoingTopic, checkUniqueKey, String.valueOf(element));
        }

        for (int i=0; i<5; i++) {
            producer.send(outgoingTopic,"to be ignored");
        }

        consumer.poll(Duration.ofSeconds(5));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);
        Map<String, Long> answerToCheck = new HashMap<>();

        consumer.close();



        for (ConsumerRecord<String, String> record : records) {
            logger.info("logging records after second poll " + record.key() + "---" + record.value());
            if (record.key().contains(checkUniqueKey)){

            Try<Long> longTry = Try.of(()->Long.valueOf(record.value()));

            answerToCheck.put(record.key(), longTry.getOrElseThrow(()->new RuntimeException("Sorry, I cannot convert the value you provided to type Long")));}
        }

        for (Map.Entry entry : answerToCheck.entrySet()) {
            logger.info(entry);
        }

        Boolean result = expectedAnswer.equals(answerToCheck);

        logger.info("And the answer is " + expectedAnswer.equals(answerToCheck));

        CheckReportBuilder reportBuilder = new CheckReportBuilder();

        reportBuilder.createTimestamp().setNameOfCheck(getName());

        if (result) {
            return reportBuilder
                    .setResult(ReportResults.PASSED)
                    .setReportBody("Your system produced a good answer. You deserve a hug.")
                    .build();
        }




        return reportBuilder
                .setResult(ReportResults.FAILED)
                .setReportBody("Sorry, please have another shot. " +
                        "Correct answer is: " + expectedAnswer + "\n" +
                        " your system produced this: " + answerToCheck)
                .build();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
