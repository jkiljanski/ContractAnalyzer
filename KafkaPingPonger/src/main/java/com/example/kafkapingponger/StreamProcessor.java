package com.example.kafkapingponger;


import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Function;

@Configuration
public class StreamProcessor {


    @Bean
    public Function<KStream<String, String>, KStream<String, String>> process() {


        System.out.println("im here motherfucker");

        return input -> input
                .filter((k, v) -> Objects.nonNull(k) && k.contains("test"))
                .groupBy((k, v) -> k + v)
                .windowedBy(TimeWindows.of(Duration.ofMillis(30000)))
                .count()
                .toStream()
                .peek((k, v) -> System.out.println("key " + k + " value " + v))
                .map((k, v) -> new KeyValue<>(k.key(), String.valueOf(v)));
    }

}
