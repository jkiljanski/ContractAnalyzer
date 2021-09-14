package com.example.kafkapingponger.kafka.simpleCounter;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.function.Function;

@Configuration
public class TransformationStreamProcessor {

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> transformNumberSeqToSum() {

        return input ->
                input
                    .filter((k, v) -> Objects.nonNull(k) && k.contains("test"))
                    .map((k, v) -> new KeyValue<>(k, countSumOfPairs(v)));
    }

    private String countSumOfPairs(String seqOfNumbers) {
        int sum = 0;
        for (int i=0; i<seqOfNumbers.length(); i+=2) {
            sum += Integer.parseInt(seqOfNumbers.substring(i, i+2));
        }

        return String.valueOf(sum);
    }
}
