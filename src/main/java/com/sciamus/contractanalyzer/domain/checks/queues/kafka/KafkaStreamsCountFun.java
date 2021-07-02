package com.sciamus.contractanalyzer.domain.checks.queues.kafka;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.config.KafkaStreamFactory;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaStreamsCountFun implements KafkaCheck {

    private final String name = "KafkaStreamsCount";

    private final KafkaStreamFactory kafkaStreamFactory;

    public KafkaStreamsCountFun(KafkaStreamFactory kafkaStreamFactory) {
        this.kafkaStreamFactory = kafkaStreamFactory;
    }

    @SneakyThrows
    @Override
    public Report run(String incomingTopic, String outgoingTopic, String host, String port) {

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = getKStream(outgoingTopic, streamsBuilder);

        stream.mapValues(value ->
                String.valueOf(value.length())
        )
                .peek((k, v) -> System.out.println("value=" + v))
                .to(incomingTopic);

        Topology topology = streamsBuilder.build();

        KafkaStreams application = kafkaStreamFactory.createStream(host, port, topology);

        application.cleanUp();

        application.start();


        Try.run(()-> Thread.sleep(30_000)).onFailure(InterruptedException.class, exception -> exception.printStackTrace());


        application.close();


        final Logger logger = LogManager.getLogger(KafkaStreamsCountFun.class);

        logger.info("I'm in the count stream class");

        return null;

    }

    private KStream<String, String> getKStream(String outgoingTopic, StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream(outgoingTopic);
        return stream;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
