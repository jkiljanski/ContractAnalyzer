package com.example.kafkapingponger;


import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class StreamProcessor {


    @Bean
    public Function<KStream<String, String>, KStream<String, String>> process() {


        System.out.println("im here motherfucker");

        return input -> input
                .groupBy((k, v) -> v)
                .count()
                .toStream()
                .peek((k, v) -> System.out.println("key " + k + " value " + v))
                .mapValues(v -> String.valueOf(v));
    }

}
