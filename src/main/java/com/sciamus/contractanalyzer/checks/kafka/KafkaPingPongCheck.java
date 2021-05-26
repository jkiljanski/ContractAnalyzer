package com.sciamus.contractanalyzer.checks.kafka;

import ch.qos.logback.core.BasicStatusManager;
import ch.qos.logback.core.helpers.CyclicBuffer;
import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPingProducer;
import com.sciamus.contractanalyzer.checks.kafka.clients.KafkaPongConsumer;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;


@Component
public class KafkaPingPongCheck implements KafkaContractCheck {


    private final KafkaPingProducer kafkaPingProducer;


    private final KafkaPongConsumer kafkaPongConsumer;
    private BlockingQueue<String> consumedMessages =new ArrayBlockingQueue<>(100);


    public KafkaPingPongCheck(KafkaPingProducer kafkaPingProducer, KafkaPongConsumer kafkaPongConsumer) {
        this.kafkaPingProducer = kafkaPingProducer;
        this.kafkaPongConsumer = kafkaPongConsumer;
    }


    @Override
    public CheckReport run(String outgoingTopic, String incomingTopic, String host, String port) {

        //adres brokera

        Map<String, Object> consumerConfig = Collections.unmodifiableMap(Map.of(
                BOOTSTRAP_SERVERS_CONFIG, host + port,
                GROUP_ID_CONFIG, "1"
        ));

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
            consumedMessages.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed but active");
            return new CheckReport("12", ReportResults.FAILED,"xxx",new Date(), "kafka");
        }

        System.out.println("passed and active");
        return new CheckReport("13",ReportResults.FAILED,"sss",new Date(), "kafka");

    }

}
