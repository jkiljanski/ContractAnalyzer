package com.sciamus.contractanalyzer.domain.checks.queues;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaCheck;
import com.sciamus.contractanalyzer.domain.checks.exception.CheckNotFoundException;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;

import java.util.List;
import java.util.stream.Collectors;

public class KafkaCheckService {

    private final List<KafkaCheck> kafkaChecks;

    public KafkaCheckService(List<KafkaCheck> kafkaChecks) {
        this.kafkaChecks = kafkaChecks;
    }

    public Report runKafkaCheck (String incomingTopic, String outgoingTopic, String host, String port, String name) {

        KafkaCheck kafkaCheck = kafkaChecks.stream()
                .filter(s->s.getName().equals(name))
                .findFirst().orElseThrow(()-> new CheckNotFoundException(name));

        return kafkaCheck.run(incomingTopic, outgoingTopic, host, port);

    }


    public List<String> getAllChecks() {
        return kafkaChecks.stream().map(KafkaCheck::getName).collect(Collectors.toList());
    }




}
