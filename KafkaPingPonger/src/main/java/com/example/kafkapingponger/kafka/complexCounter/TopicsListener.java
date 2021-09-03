package com.example.kafkapingponger.kafka.complexCounter;

import com.example.kafkapingponger.kafka.config.KafkaStreamFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicsListener {
    private KafkaStreamFactory factory;

    public TopicsListener(KafkaStreamFactory factory) {
        this.factory = factory;
    }

    @KafkaListener(topics = "complex-input-topic", groupId = "#{T(java.util.UUID).randomUUID().toString()}")
    private void listenToInputTopics() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> inputStream1 = builder.stream("complex-input-topic1");
        KStream<String, String> inputStream2 = builder.stream("complex-input-topic2");

        KafkaStreams application = createJoinedInputKafkaStream(inputStream1, inputStream2, builder);

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        application.close();
    }

    private String computeJoinedInputStreamValue(String firstValue, String secondValue) {
        int firstPair;
        int secondPair;
        List<Integer> sumsOfPairs = new ArrayList<>();

        for (int i=0; i<firstValue.length(); i+=2) {
            firstPair = Integer.parseInt(firstValue.substring(i, i+2));
            secondPair = Integer.parseInt(secondValue.substring(i, i+2));
            sumsOfPairs.add(firstPair + secondPair);
        }

        sumsOfPairs.sort(Collections.reverseOrder());

        List<String> stringSumsOfPairs = convertSumsOfPairsToString(sumsOfPairs);

        String stringWithSplitters = String.join("/", stringSumsOfPairs);

        return stringWithSplitters;
    }

    private List<String> convertSumsOfPairsToString(List<Integer> sumsOfPairs) {
        return sumsOfPairs
                .stream()
                .map(pair -> String.valueOf(pair))
                .collect(Collectors.toList());
    }

    private KStream<String, String> getMessagesWithoutGroupNumber(KStream<String, String> inputStream) {
        return inputStream.map((k, v) -> new KeyValue<>(k.substring(0, k.length() - 1), v));
    }

    private KafkaStreams createJoinedInputKafkaStream(KStream<String, String> inputStream1, KStream<String, String> inputStream2, StreamsBuilder builder) {
        KStream<String, String> inputStream1WithOriginalKey = getMessagesWithoutGroupNumber(inputStream1);
        KStream<String, String> inputStream2WithOriginalKey = getMessagesWithoutGroupNumber(inputStream2);

        KStream<String, String> joinedInputStream = inputStream1WithOriginalKey
                .join(inputStream2WithOriginalKey,
                        (firstValue, secondValue) -> computeJoinedInputStreamValue(firstValue, secondValue),
                        JoinWindows.of(Duration.ofMillis(150)),
                        StreamJoined.with(
                                Serdes.String(),
                                Serdes.String(),
                                Serdes.String()
                        ));
        joinedInputStream.to("complex-output-topic");
        KafkaStreams application = factory.createStream(builder.build());
        application.start();
        return application;
    }
}
