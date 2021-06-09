package com.example.kafkapingponger;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.function.Function;


@SpringBootApplication
public class KafkaPingPongerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaPingPongerApplication.class, args);
    }







}
