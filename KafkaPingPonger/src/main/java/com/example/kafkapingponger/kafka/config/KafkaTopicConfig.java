package com.example.kafkapingponger.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createPing() {
        return new NewTopic("ping", 1, (short) 1);
    }

    @Bean
    public NewTopic createPong() {
        return new NewTopic("pong", 1, (short) 1);
    }

    @Bean
    public NewTopic createStreamIn() {
        return new NewTopic("input-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic createStreamOut() {
        return new NewTopic("output-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic createCommand() {
        return new NewTopic("command-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic createCount() {
        return new NewTopic("count-topic", 1, (short) 1);
    }


}