package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {


    private final KafkaPingProducer kafkaPingProducer;


    private BlockingQueue<String> consumedMessages = new ArrayBlockingQueue<>(100);


    public KafkaPingPongCheck(KafkaPingProducer kafkaPingProducer) {
        this.kafkaPingProducer = kafkaPingProducer;
    }




    @Override
    public CheckReport run(String outgoingTopic, String incomingTopic, String host, String port) {

        //adres brokera


        System.out.println("kafka host is + " +host +port);
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
                    System.out.println("jestem tu");
                    consumedMessages.add(record.value());
                }

        );
        KafkaMessageListenerContainer<String,String>  container =
                new KafkaMessageListenerContainer(kafkaConsumerFactory,containerProperties);


        container.start();

        synchronized (Thread.currentThread()) {
        try {
            Thread.currentThread().wait(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}


        kafkaPingProducer.addTopicName(outgoingTopic);
        kafkaPingProducer.sendMessage("ping");


        String message;

        try {
            System.out.println();
            message = consumedMessages.poll(3, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed but active");
            return new CheckReport("12", ReportResults.FAILED, "xxx", new Date(), "kafka");
        }

        System.out.println("passed and active");
        System.out.println(message);
        return new CheckReport("13", ReportResults.PASSED, "sss", new Date(), "kafka");

    }

}
