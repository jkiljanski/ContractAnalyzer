package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaConsumFactory;
import com.sciamus.contractanalyzer.checks.kafka.clients.config.KafkaProducerFactory;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.ReportService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;
import java.util.Set;
import java.util.stream.StreamSupport;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {


    private final KafkaConsumFactory kafkaConsumFactory;

    private final KafkaPingProducer kafkaPingProducer;

    private final KafkaProducerFactory kafkaProducerFactory;

    private final ReportService reportService;

    //2 strategie: albo assign() albo subscribe()


    public KafkaPingPongCheck(KafkaConsumFactory kafkaConsumFactory, KafkaPingProducer kafkaPingProducer, KafkaProducerFactory kafkaProducerFactory, ReportService reportService) {
        this.kafkaConsumFactory = kafkaConsumFactory;
        this.kafkaPingProducer = kafkaPingProducer;
        this.kafkaProducerFactory = kafkaProducerFactory;
        this.reportService = reportService;
    }


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        Consumer<String, String> consumer = kafkaConsumFactory.createConsumer(incomingTopic, host, port);




//        Set<TopicPartition> topicPartitions = consumer.assignment();

//
//        System.out.println(getPosition(consumer, topicPartitions));

        consumer.poll(Duration.ZERO);


//        System.out.println(getPosition(consumer,topicPartitions));

//        System.out.println(consumer.position(topicPartitions.iterator().next()));


        CheckReportBuilder reportBuilder = new CheckReportBuilder();
        reportBuilder.setNameOfCheck("kafka check").createTimestamp();


        KafkaTemplate<String, String> producer = kafkaProducerFactory.createProducer(host, port);


        String message = "ping" + (new Random().nextInt(10));

        String correctMessage = message + "pong";


        producer.send(outgoingTopic, 0, null, message);

        Iterable<ConsumerRecord<String, String>> records = consumer.poll(Duration.ofSeconds(5)).records(incomingTopic);


        for (ConsumerRecord<String, String> record: records) {
            System.out.println("tu jestem");
            System.out.println(record.value());
        }

        String additionalReportInfo = "incoming topic: " + incomingTopic + " outgoing topic: " + outgoingTopic +" ";

        reportBuilder.setReportBody(additionalReportInfo);


        if(StreamSupport.stream(records.spliterator(),false)
                .map(r ->r.value())
                .peek(System.out::println)
                .anyMatch(p->p.equals(correctMessage))) {

            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.PASSED)
                    .addTextToBody("message should be: " +
                            "" + correctMessage +
                            " and indeed was")
                    .createTestReport());
        }

        else {
            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.FAILED)
                    .addTextToBody("Sorry, we couldn't find the correct message: " + correctMessage
                    )
                    .createTestReport());
        }
    }

    private long getPosition(Consumer<String, String> consumer, Set<TopicPartition> topicPartitions) {
        return consumer.position(topicPartitions.iterator().next());
    }

}