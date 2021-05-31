package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.ReportService;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {


    private final KafkaPingProducer kafkaPingProducer;

    private final ReportService reportService;


    private BlockingQueue<String> consumedMessages = new ArrayBlockingQueue<>(100);


    public KafkaPingPongCheck(KafkaPingProducer kafkaPingProducer, ReportService reportService) {
        this.kafkaPingProducer = kafkaPingProducer;
        this.reportService = reportService;
    }


    @Override
    public CheckReport run(String incomingTopic, String outgoingTopic, String host, String port) {

        //adres brokera


        CheckReportBuilder reportBuilder = new CheckReportBuilder();
        reportBuilder.setNameOfCheck("kafka check").createTimestamp();

        System.out.println("kafka host is + " + host + port);
        Map<String, Object> kafkaConfig = Map.of(
                BOOTSTRAP_SERVERS_CONFIG, host + ":" + port,
                GROUP_ID_CONFIG, "1"
//                AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        kafkaConfig,
                        new StringDeserializer(),
                        new StringDeserializer());

        ContainerProperties containerProperties = new ContainerProperties(incomingTopic);

        containerProperties.setMessageListener((MessageListener<String, String>) record ->
                {
                    consumedMessages.add(record.value());
                }

        );

        //ominąć kontener?? // healthcheck w checku // warunek na configsach contenera: "do 3 razy sztuka" itp
        // błąd - w raporcie informacja o powodach faila


        KafkaMessageListenerContainer<String, String> container =
                new KafkaMessageListenerContainer(kafkaConsumerFactory, containerProperties);


        container.start();

//        synchronized (Thread.currentThread()) {
//        try {
//            Thread.currentThread().wait(10_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }}

        kafkaPingProducer.addTopicName(outgoingTopic);

        String message = "ping" + (new Random().nextInt(10));

        kafkaPingProducer.sendMessage(message);

        String consumedMessage = null;

        try {
            consumedMessage = consumedMessages.poll(3, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String correctMessage = message + "pong";


        String additionalInfo = "incoming topic: " + incomingTopic + " outgoing topic: " + outgoingTopic +" ";

        reportBuilder.setReportBody(additionalInfo);

        if ((correctMessage).equals(consumedMessage)) {
            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.PASSED)
                    .addTextToBody("message should be: " +
                            "" + correctMessage +
                            " and is: " +
                            "" + consumedMessage)
                    .createTestReport());
        } else {
            return reportService.addReportToRepository(reportBuilder.setResult(ReportResults.FAILED)
                    .addTextToBody("message should be: " + correctMessage + " but is: " + consumedMessage)
                    .createTestReport());

        }
    }

}
