//package com.example.kafkapingponger;
//
//import com.example.kafkapingponger.kafka.config.KafkaStreamFactory;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class StreamsService {
//
//    private KafkaStreamFactory streamFactory;
//
//
//    public StreamsService(KafkaStreamFactory streamFactory) {
//        this.streamFactory = streamFactory;
//    }
//
//    public void redirect() {
//
//        StreamsBuilder builder = new StreamsBuilder();
//
//
//        KStream<String, String> kStream = builder.stream("input-topic");
//
//        KTable<String, Long> kTable = kStream
//                .groupBy((k, v) -> v)
//                .count();
//
//
//        kTable.toStream()
//                .mapValues(v -> String.valueOf(v))
//                .peek((k, v) -> {
//                    System.out.println("hello!! key " + k + " value " + v);
//                })
//                .to("output-topic");
//
//        KafkaStreams application = streamFactory.createStream(builder.build());
//
//
//        application.cleanUp();
//        application.start();
//
//    }
//
//
//}
