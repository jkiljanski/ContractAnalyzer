package com.example.kafkapingponger.kafka.complexCounter;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.function.Function;

@Configuration
public class ComplexStreamProcessor {

    @Bean
    public Function<KStream<String, String>, KStream<String, String>[]> count() {

        Predicate<String, String> inputTopic1 = (k, v) -> k.endsWith("1");
        Predicate<String, String> inputTopic2 = (k, v) -> k.endsWith("2");

        return input -> input
                .filter((k, v) -> Objects.nonNull(k) && k.contains("test"))
                .branch(inputTopic1, inputTopic2);
    }
}