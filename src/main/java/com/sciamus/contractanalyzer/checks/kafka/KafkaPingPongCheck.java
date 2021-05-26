package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPongConsumer;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
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


        kafkaPingProducer.addTopicName(outgoingTopic);
        kafkaPingProducer.sendMessage("ping");

        Map<String, Object> consumerConfig = Map.of(
                BOOTSTRAP_SERVERS_CONFIG, host + ":" + port,
                GROUP_ID_CONFIG, "1"
        );

        DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        consumerConfig,
                        new StringDeserializer(),
                        new StringDeserializer());


        ContainerProperties containerProperties = new ContainerProperties(incomingTopic);

        containerProperties.setMessageListener((MessageListener<String, String>) record -> consumedMessages.add(record.value()));

        ConcurrentMessageListenerContainer container =
                new ConcurrentMessageListenerContainer<>(
                        kafkaConsumerFactory,
                        containerProperties);


        container.start();

        try {
            consumedMessages.poll(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed but active");
            return new CheckReport("12", ReportResults.FAILED, "xxx", new Date(), "kafka");
        }

        System.out.println("passed and active");
        System.out.println(consumedMessages);
        return new CheckReport("13", ReportResults.FAILED, "sss", new Date(), "kafka");

    }

}
