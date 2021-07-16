package com.sciamus.contractanalyzer.domain.checks.queues;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaContractCheck;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public class KafkaContractCheckService {

    private final List<KafkaContractCheck> kafkaChecks;

    @Autowired
    public KafkaContractCheckService(java.util.List<KafkaContractCheck> kafkaChecks) {
        this.kafkaChecks = List.ofAll(kafkaChecks);
    }

    public CheckReport runKafkaCheck (String incomingTopic, String outgoingTopic, String host, String port, String name) {

        KafkaContractCheck kafkaContractCheck = kafkaChecks.toStream()
                .filter(s->s.getName().equals(name))
                .headOption().getOrElseThrow(()-> new CheckNotFoundException(name));

        return kafkaContractCheck.run(incomingTopic, outgoingTopic, host, port);

    }


    public List<String> getAllChecks() {
        return List.ofAll(kafkaChecks.toStream()
                .map(KafkaContractCheck::getName)
                .collect(Collectors.toList()));
    }




}
